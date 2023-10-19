package onetoone.GameServer.PlayerClasses;

public class User {

    private static int playerID_index = 0;

    protected final String username;

    protected int playerID;

    protected int balance;

    public User(){
        playerID = playerID_index++;
        balance = 100;
        username = Integer.toString(playerID);
    }

    public User(String username){
        playerID = playerID_index++;
        balance = 100;
        this.username = username;
    }

    public User(User user){
        playerID = user.getID();
        balance = user.getBalance();
        username = user.toString();
    }
    
    public int getBalance(){ return balance; }

    public int getID(){
        return playerID;
    }

    @Override
    public String toString(){
        return username;
    }

}
