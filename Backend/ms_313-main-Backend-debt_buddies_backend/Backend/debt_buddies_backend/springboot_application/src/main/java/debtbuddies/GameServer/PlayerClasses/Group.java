package debtbuddies.GameServer.PlayerClasses;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<User> users;

    private final int groupId;

    private int maxPlayers = 4;

    public Group(List<User> users, int groupId){
        this.users = new ArrayList<>(users);
        this.groupId = groupId;
    }

    public Group(User user, int groupId){
        users = new ArrayList<>();
        users.add(user);
        this.groupId = groupId;
    }

    public Group(int groupId){
        users = new ArrayList<>();
        this.groupId = groupId;
    }

    public int getGroupId(){
        return groupId;
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }

    public boolean full(){
        return (getNumUsers()==getMaxPlayers());
    }

    public boolean empty(){ return (getNumUsers()==0); }

    public void setMaxPlayers(int value){
        maxPlayers = value;
    }

    public List<User> getUsers(){
        return new ArrayList<>(users);
    }

    public List<String> getUsersString(){
        List<String> users_string = new ArrayList<>();
        for(User user: users){
            users_string.add(user.toString());
        }
        return users_string;
    }

    public int getNumUsers(){
        return users.size();
    }

    public boolean isQueue(){
        return groupId==0;
    }

    public void add(User user){
        users.add(user);
    }

    public void addUsers(List<User> users){
        this.users.addAll(users);
    }

    public void remove(User user){
        users.remove(user);
    }

    public void removeUsers(List<User> users){
        this.users.removeAll(users);
    }

    public void clear(){
        users.clear();
    }

}
