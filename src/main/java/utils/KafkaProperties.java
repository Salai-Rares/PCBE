package utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProperties {

    public final static String bootstrapServer = "localhost:9092";
    public final static String offset_reset_config = "earliest";
    public final static String group_id= "java-group-consumer";
    public final static short replication_factor = 1;
    public final static short numPartitions = 1;

        public static Properties getPropertiesSendStringMessages(){
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
            return properties;
        }

        public static Properties getPropertiesSendUserMessages(){
            Properties properties = new Properties();
            properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"com.exemple.kafka.serializers.ClientSerializer");
            return properties;
        }

        public static Properties getPropertiesReceiveUserMessages(){
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group3");
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.exemple.kafka.deserializers.ClientDeserializer");
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            return properties;
        }

        public static Properties getPropertiesReceiveStringMessages(){
            Properties p = new Properties();
            p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
            p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
            p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset_reset_config);
            return p;
        }

}
