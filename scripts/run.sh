#!/bin/sh

# 服务存放的文件夹
SERVICE_DIR=/deploy
# 服务名
SERVICE_NAME=$2
ENV=$3

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh run.sh [start|stop|restart|status] [your service name] [local|sit|uat|prod]"
  exit 1
}

# 进入服务存放的文件夹
cd $SERVICE_DIR

# 接收一个从linux控制台输入的参数 ./run.sh start/stop/restart

# 输入参数为start时启动项目
start() {
  echo "[start] - begin: $SERVICE_NAME"
  cd $SERVICE_DIR
  # nohup守护进程启动项目，即2使退出了终端项目仍在运行；/dev/null把日志输入到空即不输出日志到notout.out，因为项目里面已经有log4j日志了
  # nohup $JRE_HOME/bin/java -Xms256m -Xmx512m -jar $JAR_NAME >/dev/null 2>&1 &
  # nohup java -jar -Dspring.profiles.active=sit $JAR_NAME >/dev/null 2>&1 &
  nohup java -jar -Dlogging.file=/logs/$SERVICE_NAME.log -Dspring.profiles.active=$ENV $SERVICE_NAME.jar >/dev/null 2>&1 &
  # echo $! >$SERVICE_DIR/$PID
  echo "[start] - end: $SERVICE_NAME"
}

# 输入参数为stop时停止项目
stop() {
  echo "[stop] - begin: $SERVICE_NAME"
  # 杀死进程id即edu-service-user.pid
  # kill `cat $SERVICE_DIR/$PID`
  # P_ID=$(ps x | grep $SERVICE_NAME.jar | grep -v grep | awk '{print $1}')
  P_ID=$(ps x | grep $ENV\ $SERVICE_NAME.jar | grep -v grep | awk '{print $1}')
  echo "Deploy >>> stop -> $SERVICE_NAME进程为:$P_ID"

  # 等待5s
  sleep 5
  # 获取进程id -w是全匹配，不加-w就是模糊匹配，可能会造成误杀进程
  # P_ID=$(ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}')
  # 如果进程id为空就提示进程不存在或者已经停止
  if [ "$P_ID" == "" ]; then
    echo "[stop] $SERVICE_NAME process not exists or stop success"
  # 如果进程id不为空就强杀进程
  else
    echo "[stop] $SERVICE_NAME process pid is:$P_ID"
    echo "[stop] begin kill $SERVICE_NAME process, pid is:$P_ID"
    kill -9 $P_ID
    echo "Deploy >>> stop $SERVICE_NAME"
  fi
  echo "[stop] - end: $SERVICE_NAME"
}

# 输入参数为restart时重启项目
restart() {
  echo "[restart] - begin: $SERVICE_NAME"
  stop
  start
  echo "[restart] - end: $SERVICE_NAME"
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"start")
  start
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
*)
  usage
  ;;
esac
