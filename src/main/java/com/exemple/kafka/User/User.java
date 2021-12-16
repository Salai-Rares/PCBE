package com.exemple.kafka.User;

import com.exemple.kafka.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaProperties;

import java.time.Duration;
import java.util.*;

import static utils.Utils.sleep;

public class User implements Runnable{

    //Client model

    private String idClient ;

    private String name;

    private List<String> topic;

    //Admin for dynamic creation of topics
    private AdminKafka adminKafka;

    //The User is a producer of string messages
    private KafkaProducer<String,String> producerStringMessages;

    private KafkaConsumer<String,String> consumerStringMessages;

    private Map<String,String> listOfFriends;






    public User(){

    }

    public String getIdClient() {
        return idClient;
    }

    public String getName() {
        return name;
    }



    public User(String name, List<String> listOfTopics){
        //Generate unique ID
        idClient = UUID.randomUUID().toString();
        adminKafka = new AdminKafka();
        adminKafka.createTopic(idClient);
        this.name = name;
        this.topic = listOfTopics;
        Properties properties =  KafkaProperties.getPropertiesReceiveStringMessages();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, idClient);
        producerStringMessages = new KafkaProducer<String, String>(KafkaProperties.getPropertiesSendStringMessages());
        consumerStringMessages = new KafkaConsumer<String, String>(properties);

        consumerStringMessages.subscribe(listOfTopics);
        listOfFriends = new HashMap<>();

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
            try {

                ConsumerRecords<String, String> records = consumerStringMessages.poll(Duration.ofMillis(1000));

                for (ConsumerRecord consumerRecord : records) {
                    if (consumerRecord.key().equals("OnlineClients"))
                        System.out.println("Server: " + consumerRecord.value());
                    else if(consumerRecord.key().equals("IdClient")){
                        System.out.println("You received the queue from server: "+consumerRecord.value());
                        String[] splitedIdName = consumerRecord.value().toString().split("/");
                        if(splitedIdName.length==2){
                            listOfFriends.put(splitedIdName[0],splitedIdName[1]);
                        }

                    }
                    else if(consumerRecord.key().equals("topic")){
                        //System.out.println("You received the queue from server: "+consumerRecord.value());
                       // System.out.println("received messages in topic");
                      //  System.out.println(consumerRecord.value().toString());
                        String[] splitedTopicNameMessage = consumerRecord.value().toString().split("/");
                        if(splitedTopicNameMessage.length==3){
                            System.out.println("TOPIC:"+splitedTopicNameMessage[0]);
                            System.out.println(splitedTopicNameMessage[1]+" : "+splitedTopicNameMessage[2]);
                        }

                    }
                    else {
                        System.out.println(consumerRecord.value().toString());
                    }
                }


            }catch (Exception e){
                System.out.println("Catched exception in User");
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

    public void viewListOfFriends(){
        for (Map.Entry<String,String> entry : listOfFriends.entrySet())
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
    }

    public void sendMessageToFriend(String friendName,String message) throws IllegalArgumentException{
        if(!listOfFriends.containsKey(friendName))
            throw new IllegalArgumentException(friendName+ " it's not present in your list of friends!");
            StringBuilder messsageFormatted = new StringBuilder(this.getName());
            messsageFormatted.append(" : ").append(message);
            sendStringMessage(listOfFriends.get(friendName),this.getIdClient(),messsageFormatted.toString());

    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof User)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        User c = (User) o;

        // Compare the data members and return accordingly
        return Objects.equals(c.idClient,this.idClient);
    }

    @Override
    public int hashCode() {
        return (int) idClient.hashCode() * name.hashCode() ;
    }

}
