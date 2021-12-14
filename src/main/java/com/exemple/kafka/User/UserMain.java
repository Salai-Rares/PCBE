package com.exemple.kafka.User;

import com.exemple.kafka.AdminKafka;

import java.util.*;

public class UserMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



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
        User client = new User(name,listOfTopics);
        PollingThread pollingThread;
        pollingThread = new PollingThread(client);
        Thread polling = new Thread(pollingThread);
        polling.start();
        client.subscribeToTopics(Arrays.asList(client.getIdClient()));
        //Connected to server
        Thread thread = new Thread(client);
        thread.start();
        System.out.println("1. Online clients");
        System.out.println("2. Add friend");
        System.out.println("3. View friends list");
        System.out.println("4. Send a message to a friend");
        System.out.println("5. Send a message within a topic");
        System.out.println("6. Create new topic");
        System.out.println("7. View topics");


        System.out.println(" ");
        System.out.println("----------------");
        System.out.println(" ");
        System.out.println(" ");

        while(true){
            int varianta = 0;
            try {
                 varianta = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException n){
                System.out.println("Please insert a number from 1 to 9");
            }
            switch (varianta){
                case 0:
                    System.out.println("Please choose an option");
                    break;
                case 1:

                    client.sendStringMessage("ClientRequest2",client.getIdClient(),"1");

                    break;
                case 2:

                    String sendMessageTo="";
                    System.out.print("Add friend: ");

                    if(scanner.hasNextLine()){
                       sendMessageTo  = scanner.nextLine();
                    }
                    System.out.println(" ");
                    client.sendStringMessage("ClientRequest2",client.getIdClient(),"2"+"-"+sendMessageTo);
                    break;
                case 3:

                    System.out.println("Your friends are: ");
                    client.viewListOfFriends();
                    break;
                case 4:

                    System.out.print("Write a message to: ");
                    String nameOfFriend="";
                    if(scanner.hasNextLine()){
                        nameOfFriend  = scanner.nextLine();
                    }
                    System.out.print("> ");
                    String message = "";
                    if(scanner.hasNextLine()){
                        message  = scanner.nextLine();
                    }
                    try {
                        client.sendMessageToFriend(nameOfFriend, message);
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:

                    System.out.print("Send a message within the topic: ");
                    String topic = "";
                    if(scanner.hasNextLine()){
                        topic  = scanner.nextLine();
                    }
                    System.out.print("> ");

                    String messageTopic = "";
                    if(scanner.hasNextLine()){
                        messageTopic  = scanner.nextLine();
                    }
                     String formattedStringMessage = topic+"/"+client.getName()+"/"+messageTopic;

                    client.sendStringMessage(topic,"topic",formattedStringMessage);
                    break;
                case 6:

                    System.out.print("Insert the name of the topic: ");
                    String topicName = "";
                    if(scanner.hasNextLine()){
                        topicName  = scanner.nextLine();
                    }
                    System.out.println("");
                    AdminKafka adminKafka = new AdminKafka();
                    if(adminKafka.createTopic(topicName))
                        System.out.println("Topic "+ topicName +" was created successfully");
                    break;
                case 7:
                    System.out.println("Your topics are: ");
                    client.viewMyTopics();
                    break;

                default:
                    System.out.println("Please choose your command");
                    break;
            }
        }


    }


}
