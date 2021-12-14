package test;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer extends Thread{

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Producer.class);
    //Create properties object for Producer
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //key.serializer
        //value.serializer
        //bootstrap.servers
    //Create the Producer
    final KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);
    //Create the ProducerRecord
        ProducerRecord<String,String> record = new ProducerRecord<>("NewTopic","nume","mesaj");
    //Send Data - Asynchronous
        try {
            while (true) {
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e == null) {
                            logger.info("\nReceived record metadata\n" +
                                    "Topic" + recordMetadata.topic() + ", Partition" + recordMetadata.partition() + ", " +
                                    "Offset" + recordMetadata.offset() + "@ Timestamp" + recordMetadata.timestamp() + "\n");
//                System.out.println("Topic"+ recordMetadata.topic() + ", Partition" + recordMetadata.partition()+", "+
//                        "Offset"+recordMetadata.offset() + "@ Timestamp" + recordMetadata.timestamp()+"\n");
                        } else {
                            logger.error("Error occured", e);
                        }
                    }
                });
                //flush and close producer
                producer.flush();

                  //  Utils.sleep(500);


            }
        }finally {
            producer.close();
        }

    }
}
