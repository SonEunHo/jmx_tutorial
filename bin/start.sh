#!/bin/bash

JMX_PORT=8268

JVM_OPTS="-Xms128m -Xmx512m"
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=$JMX_PORT"
JVM_OPTS="$JVM_OPTS -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
JVM_OPTS="$JVM_OPTS -Djava.rmi.server.hostname=$(hostname -f)"

SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
PROJECT_DIR="$SCRIPTPATH/.."
CLASS_PATH="$PROJECT_DIR/build/classes/java/main"
MAIN_CLASS="me.nanostudio.jmx.Main"

nohup java -cp ${CLASS_PATH} ${MAIN_CLASS} > ${PROJECT_DIR}/my_log.log 2>&1 </dev/null &
echo "started"