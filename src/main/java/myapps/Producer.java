package myapps;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        String bootstrapServers = "localhost:9092";
        String filepath = "src/main/java/myapps/data/sales_data.csv";

        final String INPUT_TOPIC = "topic-input";

        // Kafka producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "Producer-1");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName()); // StringSerializer.class.getName() is a string "org.apache.kafka.common.serialization.StringSerializer"
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String key = fields[0];
                String value = line;
                ProducerRecord<String, String> record_new = new ProducerRecord<>(INPUT_TOPIC, key, value);
                System.out.println(key + " " + value);
                producer.send(record_new);
                Thread.sleep(100);
            }
        } 
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            producer.close();
        }
        //System.out.println("WD = " + System.getProperty("user.dir"));
    }
}