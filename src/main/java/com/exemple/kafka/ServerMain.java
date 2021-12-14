package com.exemple.kafka;



public class ServerMain {
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
