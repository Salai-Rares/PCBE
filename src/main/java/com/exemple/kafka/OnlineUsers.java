package com.exemple.kafka;

import com.exemple.kafka.User.User;

import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineUsers {
    private AbstractMap<User, Long> onlineClients;
    public OnlineUsers()
    {
        onlineClients = new ConcurrentHashMap<>();
    }

    public  void addUser(User key, Long value){
        onlineClients.put(key,value);}

        public void deleteUser(User user){
        onlineClients.remove(user);
        }


    public AbstractMap<User, Long> getOnlineClients() {
        return onlineClients;
    }
}
