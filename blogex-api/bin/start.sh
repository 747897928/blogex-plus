#!/bin/bash

. /etc/profile

export LANG=en_US.UTF-8

if [[ -z "$JAVA_HOME" ]]; then
  export JAVA_HOME=/usr/local/jdk-17.0.2
fi
export JAVA_HOME=/usr/local/jdk-17.0.2
SERVER_HOME=$(dirname $0)/..
cd ${SERVER_HOME}
SERVER_NAME="BlogExApiApplication"

pid=$(ps -ef | grep ${SERVER_NAME} | grep -v grep | awk '{print $2}')

if [[ "$pid" != '' ]]; then
  kill -9 "$pid"
  if [[ $? == 0 ]]; then
    echo "stop successfully"
  else
    echo "stop failed"
  fi
else
  echo "already stopped"
fi

JAVA_OPTS="-Xms256m -Xmx1024m"

nohup java ${JAVA_OPTS} -cp *.jar cn.edu.gxust.blogex.api.BlogExApiApplication &
echo "$SERVER_NAME started ok"