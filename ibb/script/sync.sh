cd /data/tools/nginx/html/
export PATH=$PATH:$PWD

packages=$1
conf=/data/tools/nginx/html/crawler.conf

rm -rf $conf

echo 'email=alkamaina@gmail.com' >> $conf
echo 'password=9mE8ZDwQ7q' >> $conf
echo 'securitytoken=xxxxxxxx' >> $conf
echo 'localization=en-EN' >> $conf
echo 'sleeptime=5000' >> $conf
echo 'packages='${packages} >> $conf 
#echo $packages >> $conf

echo begin download from googleplay
echo ''

ps -ef | grep "googleplay.jar"  |grep -v grep|awk '{print $2}' |  sed -e "s/^/kill -9 /g" | sh -

java -jar googleplay.jar

#echo ''
#echo s3cmd running
#echo ''

#s3cmd/s3cmd sync apk/ s3://googleplayapk
