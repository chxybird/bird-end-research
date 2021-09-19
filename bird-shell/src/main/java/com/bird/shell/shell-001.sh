#!/usr/bin/env bash
#在指定的目录下重新启动java jar包
echo "publishing....."
cd /home/bird/volume/jenkins/workspace/spring-cloud/target
PID_ARRAY=$(ps -ef | grep bird-cicd-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $2}')
for pid in ${PID_ARRAY[*]}
do
 kill -9 ${pid}
done
nohup java -jar -Xmx128m -Xms128m -Xss256k -Xmn32m bird-cicd-1.0-SNAPSHOT.jar > /home/bird/log/console.txt 2>&1 &

