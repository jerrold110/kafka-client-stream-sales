#!/bin/bash

# Start Kafka services
echo 'Starting kafka services'
cd /home/a/folder_1/kafka_2.13-3.7.0
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
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
