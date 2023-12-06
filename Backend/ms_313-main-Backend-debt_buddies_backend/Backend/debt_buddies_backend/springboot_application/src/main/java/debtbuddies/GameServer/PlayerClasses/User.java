package debtbuddies.GameServer.PlayerClasses;

public class User {

    private static int playerID_index = 0;

    protected final String username;

    protected long playerID;

    protected int balance;

    public User(){
        playerID = playerID_index++;
        balance = 100;
        username = Long.toString(playerID);
    }

    public User(String username){
        playerID = playerID_index++;
        balance = 100;
        this.username = username;
    }

    public User(String username, long id, int balance){
        this.username = username;
        playerID = id;
        this.balance = balance;
    }

    public User(User user){
        playerID = user.getID();
        balance = user.getBalance();
        username = user.toString();
    }
    
    public int getBalance(){ return balance; }

    public long getID(){
        return playerID;
    }

    @Override
    public String toString(){
        return username;
    }

}
