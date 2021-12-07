package com.exemple.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static utils.KafkaProperties.bootstrapServer;

public class KafkaMessageSender {
    Properties properties = new Properties();
    final KafkaProducer<String,String> producer;
    public KafkaMessageSender(){
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer .class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
    }


    //Send Data - Asynchronous
    public void sendMessage(String topic,String nume,String mesaj){
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,nume,mesaj);

        producer.send(record);
//        , new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                if(e==null){
//
//               System.out.println("Topic"+ recordMetadata.topic() + ", Partition" + recordMetadata.partition()+", "+
//                       "Offset"+recordMetadata.offset() + "@ Timestamp" + recordMetadata.timestamp()+"\n");
//                }
//
//            }
//
//
//        });
        producer.flush();
        //producer.close();

    }


}
