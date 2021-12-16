package com.exemple.kafka;

import com.exemple.kafka.User.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.KafkaProperties;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static utils.Utils.onlineClientsResponse;

public class Server implements Runnable  {


    private  OnlineUsers onlineUsers;

    KafkaMessageSender kafkaMessageSender;
    KafkaConsumer<String,String> consumer ;
    public Server(OnlineUsers onlineUsers){
        Properties properties = KafkaProperties.getPropertiesReceiveStringMessages();
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"server_group_id");
      this.onlineUsers = onlineUsers;
        consumer =  new KafkaConsumer<String, String>(properties);
      kafkaMessageSender = new KafkaMessageSender();
    }


    public void receivedMessage(List<String> topics) {

        consumer.subscribe(topics);
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord consumerRecord:records){
                System.out.println(consumerRecord.value());
                if(consumerRecord.value().equals("1")){
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                   StringBuilder stringBuilder = new StringBuilder(onlineClientsResponse);
                    for(User client : onlineUsers.getOnlineClients().keySet()){
                        System.out.println(client.getName());
                        stringBuilder.append(client.getName()).append("\n");
                    }
                    kafkaMessageSender.sendMessage(consumerRecord.key().toString(),"OnlineClients",stringBuilder.toString());


                }

                else if(consumerRecord.value().toString().substring(0,1).equals("2")){
                    String[] splitedNameByRegex = consumerRecord.value().toString().split("-");
                    String nameToSend="";
                    if(splitedNameByRegex.length == 2){
                        nameToSend=splitedNameByRegex[1];
                    }
                    for(User client : onlineUsers.getOnlineClients().keySet()){
                        if(client.getName().equals(nameToSend)){
                            kafkaMessageSender.sendMessage(consumerRecord.key().toString(),"IdClient",client.getName()+"/"+client.getIdClient());
                            break;
                        }
                    System.out.println("2");
                    }
                }

            }
        }
    }




    @Override
    public void run() {

       this.receivedMessage(Collections.singletonList("ClientRequest2"));
    }
}
