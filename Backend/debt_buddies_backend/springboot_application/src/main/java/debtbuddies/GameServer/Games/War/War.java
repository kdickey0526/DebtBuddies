package debtbuddies.GameServer.Games.War;

import debtbuddies.GameServer.Communication.Response;
import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.DeckLibrary.Card;
import debtbuddies.GameServer.Games.Game;
import debtbuddies.GameServer.Games.GameInterface;
import debtbuddies.GameServer.Games.War.WarInfo.EndInfo;
import debtbuddies.GameServer.Games.War.WarInfo.PlayInfo;
import debtbuddies.GameServer.Games.War.WarInfo.StartInfo;
import debtbuddies.GameServer.PlayerClasses.CardUser;
import debtbuddies.GameServer.PlayerClasses.Group;
import debtbuddies.GameServer.PlayerClasses.User;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class War extends Game<CardUser> implements GameInterface<CardUser, War> {

    private int QUEUE_SIZE = 2;

    private CardUser target_player;

    private List<Card> p1stack, p2stack;

    private CardUser p1, p2;

    private int p_index = 0;

    public War(List<User> users, int gameId){super(users, gameId);}

    public War(){
        super();
        queue_size = QUEUE_SIZE;
    }

    private void sendPlayInfo(CardUser player){
        String last_suit;
        int last_rank;
        Card last_card;
        if(player == p1){
            last_card = p1stack.get(p1stack.size()-1);
        }else{
            last_card = p2stack.get(p2stack.size()-1);
        }
        last_suit = last_card.getSuit().toString();
        last_rank = last_card.getRank();
        PlayInfo playInfo = new PlayInfo(player.toString(), last_suit, last_rank);
        Response.addMessage(users, "playInfo", playInfo);
    }
    private void sendStartInfo(CardUser player){
        StartInfo startInfo = new StartInfo();
        Response.addMessage(playerUserMap.get(player), "startInfo", startInfo);
    }
    private void sendEndInfo(){
        EndInfo endInfo = new EndInfo();
        Response.addMessage(users, "endInfo", endInfo);
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
    }

    @Override
    public void getResponse(User user, ServerEvent serverEvent) {

        CardUser player = userPlayerMap.get(user);

        if(running == 0 && Objects.equals(serverEvent.getAction(), "start")){

            initializeGame();

            for(CardUser o_player : players){
                sendStartInfo(o_player);
            }

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
                    sendPlayInfo(player);
                    target_player = nextTargetPlayer();
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
            reward(p1);
        }else if(last2.getRank() > last1.getRank()){
            reward(p2);
        }else{

        }
    }

    public void reward(CardUser player){
        for(Card card : p1stack){
            player.giveCard(card);
        }
        for(Card card : p2stack){
            player.giveCard(card);
        }
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
