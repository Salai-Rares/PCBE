package com.exemple.kafka.User;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaProperties;

import static utils.Utils.sleep;

public class PollingThread implements Runnable{

    private User user;
    //The User is a producer of user messages
    private KafkaProducer<User,Long> producerUserMessages;

    public PollingThread(User user){
        producerUserMessages = new KafkaProducer<User, Long>(KafkaProperties.getPropertiesSendUserMessages());
        this.user = user;
    }
    @Override
    public void run() {
        pollingServer();
    }




    private void sendObjectMessage(String topic,User key,Long user){
        ProducerRecord<User, Long> record = new ProducerRecord<>(topic, key, user);
        producerUserMessages.send(record);
        producerUserMessages.flush();
    }
    private void pollingServer(){
        try{

            while (true){

                try{
                sleep(500);
                sendObjectMessage("ClientServer3",this.user,System.currentTimeMillis());
               // user.sendStringMessage("ClientServer",this.user.getIdClient(),Long.toString(System.currentTimeMillis()));
                }
                catch (Exception e){
                    System.out.println("Exceptie send Object");
                }

            }
        }
        finally {
            producerUserMessages.close();
        }
    }
}
