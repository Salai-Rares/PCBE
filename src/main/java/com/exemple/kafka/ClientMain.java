package com.exemple.kafka;

import test.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws Exception{

        Scanner scanner = new Scanner(System.in);
       // Server server = Server.getInstanceOfServer();


            System.out.print("Insert your name: ");
            String name = scanner.nextLine();

            System.out.print("Insert topics you are interested in: ");

            List<String> listOfTopics = new ArrayList<>();

            //read topics until enter is pressed


            while (true) {
                String topic = scanner.nextLine();
                if (topic.equalsIgnoreCase("")) break;
                listOfTopics.add(topic);
            }
            Client client = new Client(name,listOfTopics);
            //Connected to server
            Thread thread = new Thread(client);
            thread.start();
            System.out.println("1. Clienti online");
            System.out.println("2. Trimitere mesaj");
            System.out.println("3. Trimitere mesaj in topic");
            System.out.println("4. Creare topic");
            System.out.println("5. Subscrie in topic");
            System.out.println("6. Dezabonare din topic");
            System.out.println("7. Vizualizare topicuri");
            System.out.println("8. Exit");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("----------------");
            System.out.println(" ");
            System.out.println(" ");

            while(true){
                int varianta = Integer.parseInt(scanner.nextLine());
                switch (varianta){
                    case 1:
                        System.out.println("1");
                        client.sendMessageFromClient("ClientRequests",client.getIdClient(),"1");

                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                    case 4:
                        System.out.println("4");
                        break;
                    case 5:
                        System.out.println("5");
                        break;
                    case 6:
                        System.out.println("6");
                        break;
                    case 7:
                        System.out.println("7");
                        break;
                    case 8:
                        System.out.println("8");
                        break;
                    default:
                        System.out.println("Please choose your command");
                        break;
                }
            }


        }
    }

