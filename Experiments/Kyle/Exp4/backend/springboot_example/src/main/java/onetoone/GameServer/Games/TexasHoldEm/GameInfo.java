package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.DeckLibrary.Card;

import java.util.List;

public class GameInfo {

    private List<Card> pit;

    private List<String> players;

    private String current_turn;

    private String last_turn;

    private String last_play;

    private int pot;

    private int ante;

    public List<Card> getPit(){
        return pit;
    }

    public List<String> getPlayers(){
        return players;
    }

    public String getCurrentTurn(){
        return current_turn;
    }

    public String getLastTurn(){
        return last_turn;
    }

    public String getLastPlay(){
        return last_play;
    }

    public int getPot(){
        return pot;
    }

    public int getAnte(){
        return ante;
    }

    public void setPit(List<Card> pit){
        this.pit = pit;
    }

    public void setPlayers(List<String> players){
        this.players = players;
    }

    public void setCurrentTurn(String current_turn){
        this.current_turn = current_turn;
    }

    public void setLastTurn(String last_turn){
        this.last_turn = last_turn;
    }

    public void setLastPlay(String last_play){
        this.last_play = last_play;
    }

    public void setPot(int pot){
        this.pot = pot;
    }

    public void setAnte(int ante){
        this.ante = ante;
    }

}
