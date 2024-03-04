#!/bin/bash

# Start Kafka services
echo 'Starting kafka services'
cd /home/a/folder_1/kafka_2.13-3.7.0
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
bin/kafka-server-start.sh config/kraft/server.properties &

# Start topics
echo 'Starting kafka topics'
bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic topic_input &

bin/kafka-topics.sh --create \
    --bootstrap-server localhost:9092 \
    --replication-factor 1 \
    --partitions 1 \
    --topic topic_output &

# Start producer and consumer clients
echo 'Starting kafka clients'
cd /home/a/folder_1/kafka-client-stream-sales
mvn clean package
mvn exec:java -Dexec.mainClass=myapps.Producer1 &
mvn exec:java -Dexec.mainClass=myapps.Producer2 &
mvn exec:java -Dexec.mainClass=myapps.Consumer &
mvn exec:java -Dexec.mainClass=myapps.Stream &