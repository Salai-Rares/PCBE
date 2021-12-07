package com.exemple.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.Utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Utils.onlineClientsResponse;
import static utils.Utils.sleep;

public class Server extends KafkaMessageReceiver implements Runnable  {

    private static Server instance =null;
    KafkaObjectReceiver kafkaObjectReceiver;

    KafkaMessageSender kafkaMessageSender;

    private Server(){
      kafkaObjectReceiver = new KafkaObjectReceiver();
      kafkaMessageSender = new KafkaMessageSender();
    }

    @Override
    public void receivedMessage(List<String> topics) {
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(p);
        consumer.subscribe(topics);
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord consumerRecord:records){

                if(consumerRecord.value().equals("1")){
                   StringBuilder stringBuilder = new StringBuilder(onlineClientsResponse);
                    for(Client client : kafkaObjectReceiver.getOnlineClients()){
                        stringBuilder.append(client.getName()).append("\n");
                    }
                    kafkaMessageSender.sendMessage(consumerRecord.key().toString(),"Server",stringBuilder.toString());

                }

            }
        }
    }


    public KafkaObjectReceiver getKafkaObjectReceiver() {
        return kafkaObjectReceiver;
    }

    public static Server getInstanceOfServer(){
        if(instance == null){
            instance=new Server();
            System.out.println("Server was uninitialized");
        }
        return  instance;
    }


    public void decodeCommand(String command){

        String[] clientId_command = command.split("/");
        switch (command){
            //TO DO
        }
    }


    @Override
    public void run() {
       this.receivedMessage(Collections.singletonList("ClientRequests"));
    }
}
