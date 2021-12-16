package com.exemple.kafka;

import com.exemple.kafka.User.User;

import java.util.Map;

import static utils.Utils.sleep;

public class removeUsersFromQueueThread implements Runnable{
    private OnlineUsers onlineUsers;
    public void proceesingUsers(User user){

    }

    public removeUsersFromQueueThread(OnlineUsers onlineUsers){
        this.onlineUsers = onlineUsers;
    }

    @Override
    public void run() {
        removeUsersFromQueue();
    }

    private void removeUsersFromQueue(){
        while(true){
            sleep(1000);
            long currentTime = System.currentTimeMillis();
            for(Map.Entry<User,Long> userTimeStamp : onlineUsers.getOnlineClients().entrySet()){
               // System.out.println("Timp acum : " + currentTime + " " + "Timp din queue" + userTimeStamp.getValue());
                if(currentTime - userTimeStamp.getValue()  > 1000){
                   onlineUsers.deleteUser(userTimeStamp.getKey());
                   System.out.println("removed: "+ userTimeStamp.getKey().getName());
                }
            }
        }
    }
}
