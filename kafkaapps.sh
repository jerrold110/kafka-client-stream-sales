#!/bin/bash

# Start producer and consumer clients
echo 'Starting kafka clients'
cd /home/a/folder_1/kafka-client-stream-sales
mvn clean package
mvn exec:java -Dexec.mainClass=myapps.Producer1 &
mvn exec:java -Dexec.mainClass=myapps.Producer2 &
mvn exec:java -Dexec.mainClass=myapps.Consumer &
mvn exec:java -Dexec.mainClass=myapps.Stream &