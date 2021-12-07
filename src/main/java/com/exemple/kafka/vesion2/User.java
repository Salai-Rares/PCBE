package com.exemple.kafka.vesion2;

import com.exemple.kafka.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static utils.Utils.sleep;

public class User implements Runnable{

    //Client model
    @JsonProperty
    private String idClient ;
    @JsonProperty
    private String name;
    @JsonProperty
    private List<String> topic;

    //Admin for dynamic creation of topics
    private AdminKafka adminKafka;

    //The User is a producer of string messages
    private KafkaProducer<String,String> producerStringMessages;

    private KafkaConsumer<String,String> consumerStringMessages;




    //Reference to Polling Thread
    PollingThread pollingThread;


    public User(){

    }

    public String getIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }

    public PollingThread getPollingThread() {
        return pollingThread;
    }

    public List<String> getTopic() {
        return topic;
    }

    public User(String name, ArrayList<String> listOfTopics){
        //Generate unique ID
        idClient = UUID.randomUUID().toString();
        adminKafka = new AdminKafka();
        adminKafka.createTopic(idClient);
        this.name = name;
        this.topic = listOfTopics;

        producerStringMessages = new KafkaProducer<String, String>(KafkaProperties.getPropertiesSendStringMessages());
        consumerStringMessages = new KafkaConsumer<String, String>(KafkaProperties.getPropertiesReceiveStringMessages());

        consumerStringMessages.subscribe(listOfTopics);


    }

    public void startThread(){
        pollingThread = new PollingThread();
        pollingThread.setUser(this);
        Thread polling = new Thread(pollingThread);
        polling.start();
    }

    public void sendStringMessage(String topic,String key,String message){
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,key,message);
        producerStringMessages.send(record);
        producerStringMessages.flush();
    }

    @Override
    public void run() {
        receiveMessage();
    }

    private void receiveMessage(){
        while(true){
            ConsumerRecords<String,String> records = consumerStringMessages.poll(Duration.ofMillis(1000));
            sleep(100);
            for(ConsumerRecord consumerRecord:records){
                if(consumerRecord.key().equals("Server"))
                    System.out.println("Server: "+ consumerRecord.value());
            }
        }
    }

    public void subscribeToTopics(List<String> newTopics){
        this.topic.addAll(newTopics);
        consumerStringMessages.subscribe(topic);
    }

    public void unsubscribeFromTopics(List<String> topicsToBeRemoved){
        this.topic.removeAll(topicsToBeRemoved);
        consumerStringMessages.subscribe(topic);
    }

    public void viewMyTopics(){
        for(String topic:topic){
            System.out.print(topic+ " ");
        }
        System.out.println(" ");
    }
}
