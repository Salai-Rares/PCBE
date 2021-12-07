package com.exemple.kafka;

import utils.Utils;

import java.util.Scanner;

import static utils.Utils.sleep;

public class ServerMain {
    public static void main(String[] args) {

        Server server = Server.getInstanceOfServer();
        while(true){
//            if(server.getSubscribedClients().isEmpty()){
//                System.out.println("Is empty");
//            }
//            else {
//                for (Client client : server.getSubscribedClients()) {
//                    System.out.println(client.getIdClient());
//                }
//            }
            sleep(1000);
        }
    }
}
