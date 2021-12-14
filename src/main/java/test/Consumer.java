package test;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static utils.KafkaProperties.*;

public class Consumer {
    private String name;
    final private KafkaConsumer<String,String> consumer;
    private List<String> topics;
    public Consumer(String name,List<String> topics){

        Properties p = new Properties();
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.GROUP_ID_CONFIG,group_id);
        p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,offset_reset_config);
        this.name=name;
        this.topics = new ArrayList<>(topics);
        consumer=new KafkaConsumer<String, String>(p);
        consumer.subscribe(topics);
    }
    public void addSubscription(List<String> topicName){
        topics.addAll(topicName);
        consumer.subscribe(topics);
    }
    public void viewSubscribedTopics(){
        int contor = 0;
        for(String topic:topics){
            System.out.println(topic+" "+contor);
            contor++;
        }
    }

    public KafkaConsumer getKafkaConsumer(){
        return consumer;
    }

    public static void main(String[] args) {
        //Create logger for class
        final Logger logger = LoggerFactory.getLogger(Consumer.class);
        Consumer consumer1 = new Consumer("FirstConsumer",Arrays.asList("TestTopic"));
        KafkaConsumer<String,String> consumer = consumer1.getKafkaConsumer();
        //create consumer
        consumer1.addSubscription(Arrays.asList("NewTopic","Animale"));

        while(true){
            //Utils.sleep(1000);
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord consumerRecord:records){
                System.out.print( "value "+consumerRecord.value());

            }

            System.out.println("");
        }

    }
}
