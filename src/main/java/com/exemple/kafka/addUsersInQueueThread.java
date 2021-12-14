package com.exemple.kafka;

import com.exemple.kafka.User.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.time.Duration;
import java.util.*;

import static utils.KafkaProperties.bootstrapServer;

public class addUsersInQueueThread implements Runnable {


    //BlockingQueue<User> listOfOnlineClientsInLastSecond = new LinkedBlockingQueue<>(10);
    Properties props = new Properties();
    private OnlineUsers onlineUsers;

    public addUsersInQueueThread(OnlineUsers onlineUsers){
        this.onlineUsers = onlineUsers;
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group3");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  "com.exemple.kafka.deserializers.UserDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName() );
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    }



    public void receivedClient(String topic){
        final KafkaConsumer<User, Long> consumer = new KafkaConsumer<User, Long>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                ConsumerRecords<User, Long> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<User, Long> record : records) {
                    User user = record.key();
                    Long timestamp = record.value();
                    this.onlineUsers.addUser(user,timestamp);

                }
            }
        }
        finally {
            consumer.close();
        }
    }

    @Override
    public void run() {
        receivedClient("ClientServer3");
    }
}
