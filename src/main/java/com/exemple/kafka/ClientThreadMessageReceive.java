package com.exemple.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static utils.Utils.sleep;

public class ClientThreadMessageReceive extends KafkaMessageReceiver implements Runnable{

    private String idClient;

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    @Override
    public void run() {
        receivedMessage(Arrays.asList(idClient));
    }

    @Override
    public void receivedMessage(List<String> topics) {
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(p);
        consumer.subscribe(topics);
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            sleep(500);
            for(ConsumerRecord consumerRecord:records){
                System.out.println(consumerRecord.value());
            }
        }
    }
}
