#!/bin/sh

JAR=jetty-client-1.0-SNAPSHOT-jar-with-dependencies.jar

#MYSQL_SERVER="192.168.214.1"
#export MYSQL_SERVER

#DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"
#DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"

#JPROFILER_AGENT="-agentpath:/home/slobo/jprofiler/jprofiler11.1.4/bin/linux-x64/libjprofilerti.so"

java ${DEBUG} ${AGENT} ${JPROFILER_AGENT} -jar target/${JAR}

