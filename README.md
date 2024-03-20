## Kafka clieent stream sales

Multiple producers stream sales data (id, quantiy, price) in realtime with a delay of 0.1 seconds per record, the data is aggregated to count in real-time how many of each product is sold. The data from 2 producers is sent into a kafka topic, then converted into a kstream, transformed, converted into a ktable, and sent into a topic. The sink is a Kafka consumer that reads from the output topic and prints the latest values in the Ktable (which is an abstraction of a changelog stream) every 1 second.

The architecture can be interpreted as a toplogy and is:
Multiple producers
-> Kafka input-topic
-> Stream processor (Kstream -> Ktable)
-> Kafka output-topic
-> Consumer (Prints the state of the ktable in realtime)

### Project management
Maven is used at the Java build tool. The Java kafka dependencies are managed in the `pom.xml` file under the <dependencies> tag. Kafka only seems to work with Java 1.8, so 1.8 is specified in the pom file.