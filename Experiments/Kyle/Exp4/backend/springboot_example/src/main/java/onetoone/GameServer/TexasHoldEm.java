package onetoone.GameServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TexasHoldEm {

    private final int BASE_ANTE = 10;
    private List<TexasHoldEmPlayer> players;
    private Deck deck;
    private List<Card> pit;
    private int pot;
    private int gameId;
    private int ante;
    private int stage;
    private int running;
    private int p_index = 0;
    private TexasHoldEmPlayer target_player;

    public TexasHoldEm(List<TexasHoldEmPlayer> players, int gameId){
        this.players = players;
        this.gameId = gameId;
        target_player = players.get(2);
        initializeGame();
    }

    public Response getResponse(TexasHoldEmPlayer player, Move move){
        Response response = new Response("all","Error");
        String message = "Error";
        if(running == 0 && Objects.equals(move.getPlay(), "start")){
            running = 1;
            message = player.toString() + " has started the game";
        }else if(running == 1 && target_player == player){
            switch(move.getPlay()) {
                case "fold":
                    fold(player);
                    message = player.toString() + " has folded";
                case "call":
                    call(player);
                    message = player.toString() + " called\nThe pot is now at " + Integer.toString(pot);
                case "raise":
                    raise(player, move.getValue());
                    message = player.toString() + " has raised the ante by " + move.getValue() + "\nThe pot is now at " + pot + " and the ante is " + ante;
                default:
                    message = "invalid move";
            }
            
            target_player = nextTargetPlayer();
        }
        response.setMessage(message);
        return response;
    }

    private void fold(TexasHoldEmPlayer player){
        player.foldHand();
    }

    private void call(TexasHoldEmPlayer player){
        int increase = ante - player.getBet();
        player.placeBet(increase);
    }

    private void raise(TexasHoldEmPlayer player, int ante_increase){
        ante += ante_increase;
        int increase = ante - player.getBet();
        player.placeBet(increase);
    }

    private TexasHoldEmPlayer nextTargetPlayer(){
        do{
            int ind = players.indexOf(target_player);
            if(ind + 1 == players.size()){
                target_player = players.get(0);
            }else{
                target_player = players.get(ind + 1);
            }
        }while(target_player.foldStatus());
        return target_player;
    }

    private void initializeGame(){
        deck = new Deck();
        pit = new ArrayList<>();
        ante = BASE_ANTE;
        pot = 0;
        stage = 0;
        running = 0;
        target_player = players.get(p_index++ % players.size());
        //deal_hole();
    }

    private int getActivePlayers(){
        int active = 0;
        for(TexasHoldEmPlayer player : players){
            if(!player.foldStatus()){ active++; }
        }
        return active;
    }

    private void flop(){
        for(int i = 0; i < 3; i++){
            pit.add(deck.draw());
        }
    }

    private void turn(){
        pit.add(deck.draw());
    }
    private void river(){
        pit.add(deck.draw());
    }

    public int getGameId(){
        return gameId;
    }

}
