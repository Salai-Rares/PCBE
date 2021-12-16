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
import static utils.KafkaProperties.getPropertiesReceiveUserMessages;

public class addUsersInQueueThread implements Runnable {


    //BlockingQueue<User> listOfOnlineClientsInLastSecond = new LinkedBlockingQueue<>(10);
    private Properties props ;
    private OnlineUsers onlineUsers;

    public addUsersInQueueThread(OnlineUsers onlineUsers){
        this.onlineUsers = onlineUsers;
        props = getPropertiesReceiveUserMessages();

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
