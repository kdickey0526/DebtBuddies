package debtbuddies.GameServer.Games.War;

import debtbuddies.GameServer.Communication.Response;
import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.DeckLibrary.Deck;
import debtbuddies.GameServer.DeckLibrary.Suit;
import debtbuddies.GameServer.Games.Game;
import debtbuddies.GameServer.Games.GameInterface;
import debtbuddies.GameServer.Games.War.WarInfo.EndInfo;
import debtbuddies.GameServer.Games.War.WarInfo.PlayInfo;
import debtbuddies.GameServer.Games.War.WarInfo.RoundInfo;
import debtbuddies.GameServer.Games.War.WarInfo.StartInfo;
import debtbuddies.GameServer.PlayerClasses.CardUser;
import debtbuddies.GameServer.PlayerClasses.Group;
import debtbuddies.GameServer.PlayerClasses.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class War extends Game<CardUser> implements GameInterface<CardUser, War> {

    private int QUEUE_SIZE = 2;

    private CardUser target_player;

    private List<Card> p1stack, p2stack;

    private CardUser p1, p2;

    private int p_index = 0;

    private Deck deck;

    private String ltm;

    private Card last_card1 = new Card(Suit.CLUBS, 2);
    private Card last_card2 = new Card(Suit.CLUBS, 2);

    public War(List<User> users, int gameId){super(users, gameId);}

    public War(){
        super();
        queue_size = QUEUE_SIZE;
    }

    private void sendPlayInfo(CardUser player){
        String last_suit1, last_suit2;
        int last_rank1 = 0;
        int last_rank2 = 0;
        if(player == p1){
            last_card1 = p1stack.get(p1stack.size()-1);
        }else{
            last_card2 = p2stack.get(p2stack.size()-1);
        }
        last_suit1 = last_card1.getSuit().toString();
        last_rank1 = last_card1.getRank();
        last_suit2 = last_card2.getSuit().toString();
        last_rank2 = last_card2.getRank();
        last_suit1 = convertSuit(last_suit1);
        last_suit2 = convertSuit(last_suit2);
        //PlayInfo playInfo = new PlayInfo(target_player.toString(), player.toString(), last_suit, last_rank);
        String playInfo = last_suit1 + last_rank1 + " " + last_suit2 + last_rank2;
        if(player == p2) {
            Response.addMessage(users, "playInfo", playInfo);
        }
    }

    private String convertSuit(String s){
        switch(s){
            case "CLUBS":
                return "club";
            case "HEARTS":
                return "heart";
            case "SPADES":
                return "spade";
            case "DIAMONDS":
                return "diamond";
            default:
                return "";
        }
    }
    private void sendStartInfo(CardUser player){
        StartInfo startInfo = new StartInfo(player.toString());
        Response.addMessage(playerUserMap.get(player), "startInfo", startInfo);
    }
    private void sendEndInfo(){
        EndInfo endInfo = new EndInfo();
        Response.addMessage(users, "endInfo", endInfo);
    }

    private void sendRoundInfo(String info){
        RoundInfo roundInfo = new RoundInfo(info);
        Response.addMessage(users, "roundInfo", roundInfo);
    }

    private CardUser nextTargetPlayer(){
        return players.get((players.indexOf(target_player) + 1) % players.size());
    }

    @Override
    protected void initializeGame() {
        running = 1;
        convertUsers();
        target_player = players.get(p_index++ % players.size());
        p1 = target_player;
        p2 = nextTargetPlayer();
        p1stack = new ArrayList<>();
        p2stack = new ArrayList<>();
        deck = new Deck();
        for(int i = 0; i < 26; i++){
            for(CardUser player : players){
                player.draw(deck);
            }
        }
    }

    @Override
    public void getResponse(User user, ServerEvent serverEvent) {

        CardUser player = userPlayerMap.get(user);

        if(running == 0 && Objects.equals(serverEvent.getAction(), "start")){

            initializeGame();

            sendStartInfo(target_player);

        }else if(running == 1 && player == target_player){
            switch(serverEvent.getAction()){
                case "deal":
                    if(player == p1){
                        p1stack.add(player.play());
                        if(p1stack.size() > 1){
                            p1stack.add(player.play());
                        }
                    }else if(player == p2){
                        p2stack.add(player.play());
                        if(p2stack.size() > 1){
                            p2stack.add(player.play());
                        }
                        compareCards();
                    }
                    target_player = nextTargetPlayer();
                    sendPlayInfo(player);
                    if(player == p2){
                        sendRoundInfo("winner: "+ltm);
                    }
                    break;
                default:
            }
        }

    }

    public void compareCards(){
        Card last1, last2;
        last1 = p1stack.get(p1stack.size()-1);
        last2 = p2stack.get(p2stack.size()-1);
        if(last1.getRank() > last2.getRank()){
            //reward(p1);
            ltm = p1.toString();
        }else if(last2.getRank() > last1.getRank()){
            //reward(p2);
            ltm = p2.toString();
        }else{
            ltm = "war";
        }
    }

    public void reward(CardUser player){
        for(Card card : p1stack){
            player.giveCard(card);
        }
        for(Card card : p2stack){
            player.giveCard(card);
        }
        p1stack.clear();
        p2stack.clear();
    }

    @Override
    public War getNewGame(Group lobby, int gameId) {
        return new War(lobby.getUsers(), gameId);
    }

    @Override
    public CardUser getNewUser(User user) {
        return new CardUser(user);
    }
}
