#!/bin/bash

. /etc/profile

export LANG=en_US.UTF-8

if [[ -z "$JAVA_HOME" ]]; then
  export JAVA_HOME=/usr/local/java
fi

SERVER_HOME=$(dirname $0)/..
cd ${SERVER_HOME}
SERVER_NAME="BlogExUploadApplication"

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
