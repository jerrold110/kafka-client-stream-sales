package myapps;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/* Count operator has a materialized parameter that specifies the running count should be stored in a state store 'counts-store' (ktable)
* We write the counts Ktable's changelog stream into another kafka topic stream-output
* Look at Stateful transformations chapter on the streams developer-guide
* The output of this is a KTable which is an abstraction of a changelog stream
*/ 
public class Stream {
    
    public static void main(String[] args) throws IOException {
        // Configure the stream
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        final String inputTopic = "topic-input";
        final String outputTopic = "topic-output";

        KStream<String, String> eventStream = builder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()));
        
        // FirstStream.filter((key, value) -> (Integer.parseInt(key)%2) == 0)
        // .mapValues(value -> value.split(",")[1])
        // .peek((key, value) -> System.out.println("Key "+key+" value " + value))
        // .groupByKey()
        // // Materialise the result into keyvaluestore named "counts-store", which is a ktable I
        // // The materialised store is always of type <Bytes, byte[]> as this is the format of the innermost store
        // .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
        // .toStream()
        // .to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));

        KTable<String, Long> eventCounts = eventStream.mapValues(value -> value.split(",")[1]).groupByKey().count();

        eventCounts.toStream().to("topic-output", Produced.with(Serdes.String(), Serdes.Long()));

        final Topology topology = builder.build();
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

    }
}
