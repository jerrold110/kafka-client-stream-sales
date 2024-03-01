package myapps;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
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
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String key = fields[0];
                String value = line;
                System.out.println(line);
                ProducerRecord<String, String> record_new = new ProducerRecord<>(INPUT_TOPIC, key, value);
                producer.send(record_new);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("WD = " + System.getProperty("user.dir"));
    }
}
