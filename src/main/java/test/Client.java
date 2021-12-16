package test;

import com.exemple.kafka.AdminKafka;
import com.exemple.kafka.KafkaMessageSender;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static utils.Utils.sleep;

public class Client implements Runnable{
    //Client model
   // @JsonProperty
    private String idClient ;
   // @JsonProperty
    private String name;
  //  @JsonProperty
    private List<String> topic;

    private AdminKafka adminKafka;
    private KafkaObjectSender kafkaObjectSender;
    private KafkaMessageSender kafkaMessageSender;
    //private Server server= Server.getInstanceOfServer();
    public Client(){

    }

    public Client(String name,List<String> listOfTopics){
        //Generate unique ID
        idClient = UUID.randomUUID().toString();
        adminKafka = new AdminKafka();
        adminKafka.createTopic(idClient);

        this.name = name;
        this.topic = listOfTopics;

        kafkaObjectSender = new KafkaObjectSender();
        kafkaMessageSender = new KafkaMessageSender();
        ClientThreadMessageReceive clientThreadMessageReceive = new ClientThreadMessageReceive();
        clientThreadMessageReceive.setIdClient(idClient);
        Thread thread = new Thread(clientThreadMessageReceive);
        thread.start();
    }

    public void sendMessageFromClient(String Topic,String key,String mesaj){
        kafkaMessageSender.sendMessage(Topic,key,mesaj);
    }



    public void pollingServer(){
        try{

            while (true){
                sleep(500);
                kafkaObjectSender.sendMessage("ClientServer",this.idClient,this);

            }
        }finally {
            kafkaObjectSender.getProducer().close();
        }

    }
    public String getName(){return name;}

    public String getIdClient(){
        return idClient;
    }

    @Override public String toString() {
        return idClient.substring(0,2);
    }

    @Override
    public void run() {
        pollingServer();
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Client)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Client c = (Client) o;

        // Compare the data members and return accordingly
        return Objects.equals(c.idClient,this.idClient);
    }

    @Override
    public int hashCode() {
          return (int) idClient.hashCode() * name.hashCode() ;
    }
}

