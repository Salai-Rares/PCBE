package com.exemple.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static utils.KafkaProperties.bootstrapServer;
import static utils.Utils.sleep;

public class KafkaObjectReceiver implements Runnable {

    private BlockingQueue<Client> onlineClients;
    BlockingQueue<Client> listOfOnlineClientsInLastSecond = new LinkedBlockingQueue<>(10);
    Properties props = new Properties();
    public KafkaObjectReceiver(){

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group3");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.exemple.kafka.deserializers.ClientDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        onlineClients = new LinkedBlockingQueue<>(10);



    }

    public BlockingQueue<Client> getOnlineClients() {
        return onlineClients;
    }

    public BlockingQueue<Client> getListOfOnlineClientsInLastSecond() {
        return listOfOnlineClientsInLastSecond;
    }

    public void receivedClient(String topic){
        final KafkaConsumer<String, Client> consumer = new KafkaConsumer<String, Client>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                ConsumerRecords<String, Client> records = consumer.poll(Duration.ofMillis(1000));
                sleep(1000);
                for (ConsumerRecord<String, Client> record : records) {
                    if(!onlineClients.contains(record.value())){
                        onlineClients.add(record.value());
                    }

                if(!listOfOnlineClientsInLastSecond.contains(record.value()))
                   listOfOnlineClientsInLastSecond.add(record.value());

               }
                for(Client client:onlineClients){
                    if(!listOfOnlineClientsInLastSecond.contains(client)){
                        onlineClients.remove(client);
                    }
                }
            listOfOnlineClientsInLastSecond.clear();
            }
        } finally {
            consumer.close();
        }
    }

    @Override
    public void run() {
        receivedClient("ClientServer");
    }
}
