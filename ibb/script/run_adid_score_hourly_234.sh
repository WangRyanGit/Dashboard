#!/bin/bash
####################################################
#run adid score hourly and download the data to mysql
#Author: WangQingHeng
#Created on 2015-07-02
##################################################
E_WRONG_ARGS=65


####ENV Variable###
BASE_HOME=/dev/shm
S3CMD_HOME=/usr
MYSQL_HOME=/usr/bin
MYSQL_HOST=52.74.66.234
USER=zhongyapte
PASSWORD="3Agpv2jHYD"
PORT=4300
DATABASE=overseaads
S3RESULT_BASE="s3://ngemobi/hive/tables"
S3BASE="s3://ngemobi/logs"
SERVER=hadoop3

##########Other Parameter############
sms_receiver="15810156886"

######################### Function #########################################
printUsage(){
        echo "[Usage]:"
        echo "`basename $0` <START_DATE_HOUR> <END_DATE_HOUR>"
        echo "Example: `basename $0` 2014-01-14_00 2014-01-14_01"
}

######Function download #######

update_o_ad(){
  START_DATE=$1
  HOUR=$2
  echo "update o_ad set oa.weight_new = c.score"
  ${MYSQL_HOME}/mysql -h ${MYSQL_HOST} -u${USER} -p${PASSWORD} -P${PORT} -ss ${DATABASE} -e "UPDATE o_ad a INNER JOIN (SELECT adid,score FROM o_optimizing_hourly b WHERE date = '${START_DATE}' AND HOUR = ${HOUR} ) c ON a.id = c.adid SET a.weight_new = c.score;commit;"
  echo "update o_ad set oa.weight = c.score"
  ${MYSQL_HOME}/mysql -h ${MYSQL_HOST} -u${USER} -p${PASSWORD} -P${PORT} -ss ${DATABASE} -e "UPDATE o_ad a INNER JOIN (SELECT adid,score FROM o_optimizing_hourly b WHERE date = '${START_DATE}' AND HOUR = ${HOUR} ) c ON a.id = c.adid SET a.weight = c.score;commit;"
  echo "update o_ad set weight=0 where  preweight=0"
  ${MYSQL_HOME}/mysql -h ${MYSQL_HOST} -u${USER} -p${PASSWORD} -P${PORT} -ss ${DATABASE} -e "update o_ad set weight=0,weight_new=0 where status=0 and type=3 and preweight=0;update o_ad set weight=0,weight_new=0 where provider in (168,200,183,211) and status=0;commit;"
  echo "Update done" 
  if [[ $? -eq 0 ]]; then
    echo "Wrote data into o_optimizing_hourly"
  fi
}



######Input Variable##########################################
if [ $# -eq 3 ]; then
    START_DATE_HOUR="${1:0:10} ${1:11:2}"
    END_DATE_HOUR="${2:0:10} ${2:11:2}"
    SERVER=$3
elif [ $# -eq 2 ]; then       
    START_DATE_HOUR="${1:0:10} ${1:11:2}"        
    END_DATE_HOUR="${2:0:10} ${2:11:2}"
elif [ $# -eq 0 ]; then        
    START_DATE_HOUR="`date +"%Y-%m-%d %H" -d"-1 hour"`"        
    END_DATE_HOUR="`date +"%Y-%m-%d %H" -d"-1 hour"`"
else      
    printUsage     
    exit $E_WRONG_ARGS
fi

echo "START_DATE_HOUR="${START_DATE_HOUR}
echo "END_DATE_HOUR="${END_DATE_HOUR}

####################################################

TASK_START_TIME="`date +"%Y-%m-%d %H:%M:%S" -d " +0 day"`"
echo "################TASK_START_TIME=${TASK_START_TIME}########################"

##### Notify ###############
notifyThesePersons () {
     echo "Sending Error messages....."
     echo "$1"
     echo "exit......"
    /bin/sh ${BASE_HOME}/sms.sh -r "${sms_receiver}" -m "$1"
}

######## Check depend hourly #############################
check_depend_hourly(){
    echo "Check depend hourly...."
    depend_ready=`${S3CMD_HOME}/bin/s3cmd ls $1 |wc -l`
    echo "depend_ready=${depend_ready}_($1)"
    i=0;
    while [ ${depend_ready} -eq 0 ];do
       echo "sleep 120...."
       sleep 120
       depend_ready=`${S3CMD_HOME}/bin/s3cmd ls $1 |wc -l`
       echo "s3cmd ls $1 | wc -l"
       echo "depend_ready=${depend_ready}_($1)"
       ((i++));
       echo "i=$i"
       if [ $i -gt 30 ]
         then
          #notifyThesePersons "clickstreamhourly_${PRODUCT} $1 is not ready."
          exit 2
      fi
    done;
}
#############################################################################################
while [ `date -d "${START_DATE_HOUR}" +%s` -le `date -d "${END_DATE_HOUR}" +%s` ]
  do
       echo "###########################################################################"
       START_DATE=${START_DATE_HOUR:0:10}
       YEAR=${START_DATE_HOUR:0:4}
       MONTH=${START_DATE_HOUR:5:2}
       YEAR_MONTH=${START_DATE_HOUR:0:7}
       DAY=${START_DATE_HOUR:8:2}
       HOUR=${START_DATE_HOUR:11:2}
       INNER_DATE=${START_DATE}    
    
       START_DATE_HOUR=${START_DATE_HOUR}
       YESTERDAY=`date +"%Y-%m-%d %H" -d "${START_DATE_HOUR} -23 hour"`
       Y_YEAR=${YESTERDAY:0:4}
       Y_MONTH=${YESTERDAY:5:2}
       Y_DAY=${YESTERDAY:8:2}
       Y_HOUR=${YESTERDAY:11:2}

       NEXT_DATE_HOUR=`date +"%Y-%m-%d %H" -d "${START_DATE_HOUR} +1 hour"`
       echo "YESTERDAY="${YESTERDAY}
       echo "Y_HOUR="${Y_HOUR}
       echo "START_DATE_HOUR="${START_DATE_HOUR}
       echo "END_DATE_HOUR="${END_DATE_HOUR}
       echo "NEXT_DATE_HOUR="${NEXT_DATE_HOUR}
       echo "START_DATE="${START_DATE}
       echo "YEAR_MONTH="${YEAR_MONTH}
       echo "MONTH="${MONTH}
       echo "YEAR"=${YEAR}
       echo "DAY="${DAY}
       echo "HOUR="${HOUR}
       echo "INNER_DATE="${INNER_DATE}
       echo "SERVER="${SERVER}
       echo "Run_aws s3 del the hour files first..."
 ################# Check the base logs ###################################
       echo "Check if the postlogs and o_ad_hourly are ready..."
       check_depend_hourly "${S3RESULT_BASE}/overseaads/o_ad_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/overseaads/o_provider_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3BASE}/user_ad_logs/hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3BASE}/postback_logs/hourly/dt=${START_DATE}/hour=${HOUR}/"    
  ##################Run jar and hive scripts on s3############################
       echo "download the hive scripts and run on hadoop..."
       ssh ${SERVER} "aws s3 cp s3://ngemobi/hive/bin/adid_score_hourly/adid_score_hourly.hql /mnt/adid_score_hourly/adid_score_hourly.hql"
       echo "Run Hive scripts on ${SERVER} "
       echo "hive -hiveconf START_DATE='${START_DATE}' -hiveconf HOUR=${HOUR} -hiveconf FROM_HOUR=${Y_HOUR} -f /mnt/adid_score_hourly/adid_score_hourly.hql"
       ssh ${SERVER} "hive -hiveconf START_DATE='${START_DATE}' -hiveconf HOUR=${HOUR} -hiveconf FROM_HOUR=${Y_HOUR} -f /mnt/adid_score_hourly/adid_score_hourly.hql"    
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_install_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_postback_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_provider_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_price_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_preweight_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_cr_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_cap_rank_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_all_dim_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
       check_depend_hourly "${S3RESULT_BASE}/oversealogs/s_adid_sum_score_hourly/dt=${START_DATE}/hour=${HOUR}/"
#########Run sql to insert the data to mysql ###############################################
       echo "load data and put the data to mysql"
       # download oversealogs s_adid_all_dim_score_hourly ${START_DATE} ${HOUR}
       # download oversealogs s_adid_sum_score_hourly ${START_DATE} ${HOUR} 
       # insert_dim oversealogs s_adid_all_dim_score_hourly o_ranking_hourly ${START_DATE} ${HOUR} "adid,dimension,ranking,score"
       # insert_sum oversealogs s_adid_sum_score_hourly o_optimizing_hourly ${START_DATE} ${HOUR} "adid,score"
       echo "/mnt/program/dataexp/dataexp.sh -f /home/ec2-user/hive/bin/adid_score_hourly/import_DB_234.sql -v DATE=${START_DATE} -v HOUR=${HOUR}"
       sh /mnt/program/dataexp/dataexp.sh -f /home/ec2-user/hive/bin/adid_score_hourly/import_DB_234.sql -v DATE=${START_DATE} -v HOUR=${HOUR} 
       echo "update_o_ad ${START_DATE}  ${HOUR} on ${MYSQL_HOST}"      
       update_o_ad  ${START_DATE}  ${HOUR}

      ##############DELETE HISTORY LOGS BEFORE 15 DAYS#####################
      FIFTEEN_DAYS_AGO=`date +%Y-%m-%d -d "${INNER_DATE} -3 day"`
      echo "FIFTEEN_DAYS_AGO="${FIFTEEN_DAYS_AGO}
      history_logs=`ls ${BASE_HOME}/oversealogs/s_adid_all_dim_score_hourly/${FIFTEEN_DAYS_AGO}* | wc -l`
      echo "ls ${BASE_HOME}/oversealogs/s_adid_all_dim_score_hourly/${FIFTEEN_DAYS_AGO}* | wc -l"
      if [ ${history_logs} -gt 0 ]
          then
             echo "Deleting the history logs on ${FIFTEEN_DAYS_AGO}...."
             rm -rf ${BASE_HOME}/oversealogs/s_adid_all_dim_score_hourly/${FIFTEEN_DAYS_AGO}*
             rm -rf ${BASE_HOME}/oversealogs/s_adid_sum_score_hourly/${FIFTEEN_DAYS_AGO}*
      fi
      ##############loop##################################################
      START_DATE_HOUR="${NEXT_DATE_HOUR}"
     done;

     TASK_END_TIME="`date +"%Y-%m-%d %H:%M:%S" -d " +0 day"`"
     echo "TASK_END_TIME="${TASK_END_TIME}
     TASK_END_TIME=`date -d "${TASK_END_TIME}" +%s`
     TASK_START_TIME=`date -d "${TASK_START_TIME}" +%s`
     TASK_SPEND_TIME=$((${TASK_END_TIME}-${TASK_START_TIME}))
     echo "TASK_SPEND_TIME="${TASK_SPEND_TIME}
echo "Done!"
