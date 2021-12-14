package test;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import utils.KafkaProperties.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import static utils.KafkaProperties.*;

class Admin {



    public Admin(){

    }

    public static void main(String[] args) {
         Properties properties = new Properties();
         final String bootstrapServer = "localhost:9092";
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);

        org.apache.kafka.clients.admin.Admin admin = org.apache.kafka.clients.admin.Admin.create(properties);

        NewTopic newTopic = new NewTopic("ClientServer",numPartitions,replication_factor);
        CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
        KafkaFuture<Void> future = result.values().get("SomeTopic2");
        try {
            future.get();
        }catch (Exception e){
            System.out.println("User ul deja exista in bd" + "exception asfansdjkfnwiuef");
        }
    }
}
