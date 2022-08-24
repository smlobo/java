#!/bin/sh

PORT=8082
JAR=SpringBootMySqlBookList-1.0.jar

#MYSQL_SERVER="192.168.214.1"
#export MYSQL_SERVER

JMX="-Dserver.tomcat.mbeanregistry.enabled=true -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1617 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

#DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

java ${DEBUG} -Dserver.port=${PORT} ${AGENT} ${JMX} -jar target/${JAR}

