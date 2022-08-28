#!/bin/sh

PORT=8087
JAR=webclient-consuming-rest-0.0.1-SNAPSHOT.jar

#JMX="-Dserver.tomcat.mbeanregistry.enabled=true -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1617 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

#DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

java ${DEBUG} -Dserver.port=${PORT} ${AGENT} ${JMX} -jar target/${JAR}

