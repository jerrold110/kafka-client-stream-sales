package myapps;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {

        String bootstrapServers = "localhost:9092";
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, "Consumer-1");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup-1");
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());

        KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(properties);
        // Subscribes the consumer to 1 or more topics
        consumer.subscribe(Arrays.asList("topic-output"));

        while (true) {
            // This is a method provided by the KafkaConsumer class to retrieve records (messages) from Kafka topics
            // the maximum time to wait for records if none are available in the consumer's internal buffer
            ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Long> record : records) {
                System.out.printf("Received message: offset = %d, key = %s, value = %s%n",
                    record.offset(), record.key(), record.value());
            }
        }
    }
}

