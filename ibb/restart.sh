ps aux|grep haproxy |grep -v 'grep' |awk '{print $2}' |xargs kill
bin/haproxy -f conf/haproxy.cfg
