package test;

import com.exemple.kafka.*;

import java.util.Collections;
import java.util.Scanner;

import static utils.Utils.sleep;

public class Receiver {
    public static void main(String[] args) {
//        KafkaObjectReceiver kafkaObjectReceiver = new KafkaObjectReceiver();
//        //kafkaObjectReceiver.receivedClient("TestObject");
//        KafkaMessageReceiver kafkaMessageReceiver= new KafkaMessageReceiver();
//        Thread thread = new Thread(kafkaMessageReceiver);
//        thread.start();
//        Thread thread2 = new Thread(kafkaObjectReceiver);
//        thread2.start();

        Server server = Server.getInstanceOfServer();
        Thread thread = new Thread(server.getKafkaObjectReceiver());
        thread.start();
        Thread thread1 = new Thread(server);
        thread1.start();
       // Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String s = scanner.nextLine();
//            if (s.equals("s")) {
//                System.out.println("---------online list --------");
//                for(Client c : server.getKafkaObjectReceiver().getOnlineClients()){
//                    System.out.println(c + " ");
//                }
//
//            }
//            s= null;
//            sleep(1000);
//        }

    }
}
