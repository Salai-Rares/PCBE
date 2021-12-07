package com.exemple.kafka.vesion2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaProperties;

import static utils.Utils.sleep;

public class PollingThread implements Runnable{

    private User user;
    //The User is a producer of user messages
    private KafkaProducer<String,User> producerUserMessages;

    public PollingThread(){
        producerUserMessages = new KafkaProducer<String, User>(KafkaProperties.getPropertiesSendUserMessages());

    }
    @Override
    public void run() {
        pollingServer();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    private void sendObjectMessage(String topic,String key,User user){
        ProducerRecord<String, User> record = new ProducerRecord<>(topic, key, user);
        producerUserMessages.send(record);
        producerUserMessages.flush();
    }
    private void pollingServer(){
        try{

            while (true){
                sleep(500);
                sendObjectMessage("ClientServer",this.user.getIdClient(),this.user);

            }
        }finally {
            producerUserMessages.close();
        }
    }
}
