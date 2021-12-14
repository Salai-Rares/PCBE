package com.exemple.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import test.Client;

import java.util.Properties;

import static utils.KafkaProperties.bootstrapServer;

public class KafkaObjectSender {
    Properties props = new Properties();
    KafkaProducer<String, Client> producer;




    public KafkaObjectSender(){
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "test.ClientSerializer");
        producer = new KafkaProducer<>(props);
    }


    public KafkaProducer<String, Client> getProducer() {
        return producer;
    }

    public void sendMessage(String topic, String nume, Client client) {

        ProducerRecord<String, Client> record
                = new ProducerRecord<String, Client>(topic, nume, client);
        producer.send(record);
        producer.flush();

    }
}
