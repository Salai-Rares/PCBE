package test;

import com.exemple.kafka.*;

import java.util.Collections;
import java.util.Scanner;

import static utils.Utils.sleep;

public class Receiver {
    public static void main(String[] args) {

        OnlineUsers onlineUsers = new OnlineUsers();
        addUsersInQueueThread addUsers = new addUsersInQueueThread(onlineUsers);
        removeUsersFromQueueThread removeUsersFromQueueThread = new removeUsersFromQueueThread(onlineUsers);
        Server server = new Server(onlineUsers);
        Thread serverThread = new Thread(server);
        serverThread.start();
        Thread addUsersThread = new Thread(addUsers);
        addUsersThread.start();
        Thread removeUsersThread = new Thread(removeUsersFromQueueThread);
        removeUsersThread.start();


    }
}
