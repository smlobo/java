#!/bin/sh

JAR=jetty-server-1.0-SNAPSHOT-jar-with-dependencies.jar

#DEBUG="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

#JPROFILER_AGENT="-agentpath:/home/slobo/jprofiler/jprofiler11.1.4/bin/linux-x64/libjprofilerti.so"

java ${DEBUG} ${AGENT} ${JPROFILER_AGENT} -jar target/${JAR}

