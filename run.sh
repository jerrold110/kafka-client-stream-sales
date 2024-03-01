#!/bin/bash

# Start Kafka services

# Start topics

# Start producer and consumer clients
mvn clean package
mvn exec:java -Dexec.mainClass=myapps.Producer