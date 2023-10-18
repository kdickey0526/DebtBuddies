package onetoone.PlayerClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private static int playerID_index = 0;

    final String username;

    int playerID;

    int balance;

    public Player(){
        playerID = playerID_index++;
        balance = 100;
        username = Integer.toString(playerID);
    }

    public Player(String username){
        playerID = playerID_index++;
        balance = 100;
        this.username = username;
    }

    public Player(Player player){
        playerID = player.getID();
        balance = player.getBalance();
        username = player.toString();
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
