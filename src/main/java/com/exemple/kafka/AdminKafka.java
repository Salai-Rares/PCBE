package com.exemple.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import utils.KafkaProperties;

import java.util.Collections;
import java.util.Properties;

import static utils.KafkaProperties.numPartitions;
import static utils.KafkaProperties.replication_factor;

public class AdminKafka {
    Properties properties = new Properties();
    final String bootstrapServer = KafkaProperties.bootstrapServer;
    org.apache.kafka.clients.admin.Admin admin;

    public AdminKafka(){
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        admin = org.apache.kafka.clients.admin.Admin.create(properties);
    }

    public boolean createTopic(String topicName){
        NewTopic newTopic = new NewTopic(topicName,numPartitions,replication_factor);
        CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
        KafkaFuture<Void> future = result.values().get(topicName);
        try {
            future.get();
        }catch (Exception e){
            System.out.println("The topic is already created");
            return false;
        }
        return  true;
    }



}
