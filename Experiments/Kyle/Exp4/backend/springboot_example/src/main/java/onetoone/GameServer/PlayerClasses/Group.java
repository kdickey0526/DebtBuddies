package onetoone.GameServer.PlayerClasses;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<User> users;

    private final int groupId;

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

    public List<User> getUsers(){
        return users;
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
