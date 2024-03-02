#!/bin/bash

# Start Kafka services

# Start topics
#kafkatopics.sh --bootstrap-server localhost:9092 --topic topic_input --creat --partitions 1 --replication-factor 1


# Start producer and consumer clients
mvn clean package
mvn exec:java -Dexec.mainClass=myapps.Producer