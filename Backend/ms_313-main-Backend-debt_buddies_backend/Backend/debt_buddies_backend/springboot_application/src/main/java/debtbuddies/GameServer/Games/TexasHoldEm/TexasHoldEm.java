package debtbuddies.GameServer.Games.TexasHoldEm;

import java.util.*;

import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.DeckLibrary.*;
import debtbuddies.GameServer.Games.Game;
import debtbuddies.GameServer.Communication.Response;
import debtbuddies.GameServer.Games.GameInterface;
import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmInfo.*;
import debtbuddies.GameServer.PlayerClasses.Group;
import debtbuddies.GameServer.PlayerClasses.User;
import debtbuddies.GameServer.DBManager;
import debtbuddies.person.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

public class TexasHoldEm extends Game<TexasHoldEmUser> implements GameInterface<TexasHoldEmUser, TexasHoldEm> {

    private int QUEUE_SIZE = 3;
    private final int BASE_ANTE = 10;
    private Deck deck;
    private List<Card> pit;
    private int pot;
    private int ante;
    private int stage;
    private int p_index = 0;

    private int update = 0;
    private TexasHoldEmUser target_player;
    private TexasHoldEmUser final_player;

    private String last_move;

    public TexasHoldEm(List<User> users, int gameId){
        super(users, gameId);
    }

    public TexasHoldEm(){
        super();
        queue_size = QUEUE_SIZE;
    }

    @Override
    public TexasHoldEm getNewGame(Group lobby, int gameId){
        return new TexasHoldEm(lobby.getUsers(), gameId);
    }

    @Override
    public void initializeGame(){
        num_users = users.size();
        pit = new ArrayList<>();
        deck = new Deck();
        ante = BASE_ANTE;
        running = 1;
        stage = 0;
        pot = 0;
        convertUsers();
        deal_hole();
        smallAndBigBlind();
    }

    public boolean isRunning(){
        return running == 1;
    }

    @Override
    public TexasHoldEmUser getNewUser(User user){
        return new TexasHoldEmUser(user);
    }


    public void getResponse(User user, ServerEvent serverEvent, PersonRepository personRepository){

        TexasHoldEmUser player = userPlayerMap.get(user);

        if(running == 0 && Objects.equals(serverEvent.getAction(), "start")){

            initializeGame();

            for(TexasHoldEmUser o_player : players){
                sendStartInfo(o_player);
                sendPlayerInfo(o_player);
            }

        }else if(running == 1 && player == target_player){
            switch(serverEvent.getAction()) {
                case "fold":
                    fold(player);
                    update = 1;
                    break;
                case "call":
                    call(player);
                    break;
                case "raise":
                    if(serverEvent.getValue() == 0){ return; }
                    raise(player, serverEvent.getValue());
                    break;
                default:
                    return;
            }

            if(getActivePlayers() == 1){
                TexasHoldEmUser winner = end_game(personRepository);
                sendEndInfo(winner);
                return;
            }else{
                sendPlayerInfo(player);
            }

            target_player = nextTargetPlayer();

            int pre_stage = stage;

            if((Objects.equals(serverEvent.getAction(), "call") || Objects.equals(serverEvent.getAction(), "fold")) && target_player == final_player) {
                switch(++stage){
                    case 1:
                        flop();
                        break;
                    case 2:
                        turn();
                        break;
                    case 3:
                        river();
                        sendStageInfo();
                    default:
                        sendEndInfo(end_game(personRepository));
                        return;
                }
            }

            if(player == final_player && update == 1){
                update = 0;
                final_player = nextTargetPlayer();
            }

            if(stage != pre_stage){
                sendStageInfo();
                newRound();
            }

            sendTurnInfo();
        }
    }

    private void sendPlayerInfo(TexasHoldEmUser player){
        PlayerInfo playerInfo = new PlayerInfo(player.getBalance(), player.getBet(), player.getAnte());
        Response.addMessage(playerUserMap.get(player), "playerInfo", playerInfo);
    }

    private void sendStartInfo(TexasHoldEmUser player){
        StartInfo startInfo = new StartInfo(player.getHand(), target_player.toString(), pot, ante);
        Response.addMessage(playerUserMap.get(player), "startInfo", startInfo);
    }

    private void sendEndInfo(TexasHoldEmUser winner){
        EndInfo endInfo = new EndInfo(winner.toString(), winner.getHigh_hand().toString(), winner.getHand(), pot);
        Response.addMessage(users, "endInfo", endInfo);
    }

    private void sendTurnInfo(){
        TurnInfo turnInfo = new TurnInfo(last_move, target_player.toString(), pot, ante);
        Response.addMessage(users, "turnInfo", turnInfo);
    }

    private void sendStageInfo(){
        StageInfo stageInfo = new StageInfo(pit);
        Response.addMessage(users, "stageInfo", stageInfo);
    }

    private void newRound(){
        for(TexasHoldEmUser player : players){
            player.setAnte(0);
        }
        ante = 0;
    }

    private void smallAndBigBlind(){

        players.get(p_index).placeBet(5);

        p_index = (p_index + 1) % players.size();

        players.get(p_index).placeBet(10);

        pot = 15;
        ante = BASE_ANTE;

        target_player = players.get((p_index + 1) % num_users);
        final_player = target_player;
    }

    public TexasHoldEmUser getTargetPlayer(){
        return target_player;
    }

    public TexasHoldEmUser end_game(PersonRepository personRepository){
        running = 0;
        /*
        if(getActivePlayers() == 1){
            for(TexasHoldEmUser player : players){
                if(!player.foldStatus()){
                    return player;
                }
            }
        }
         */

        DBManager dbManager = new DBManager(personRepository);

        TexasHoldEmUser winner = decideWinner();

        dbManager.updatePerson(winner.toString(), winner.getBalance() + pot);

        List<Long> ids = new ArrayList<>();
        List<Integer> coins = new ArrayList<>();

        ids.add(winner.getID());
        coins.add(winner.getBalance() + pot);

        for(TexasHoldEmUser player : players){
            if(player != winner){
                ids.add(player.getID());
                coins.add(player.getBalance());
                dbManager.updatePerson(player.toString(), player.getBalance());
            }
        }

        //dbManager.updatePersons(ids, coins);

        return winner;
    }

    private void fold(TexasHoldEmUser player){
        player.foldHand();
    }

    private void call(TexasHoldEmUser player){
        int increase = ante - player.getAnte();
        pot += player.placeBet(increase);
        last_move = "call";
    }

    private void raise(TexasHoldEmUser player, int ante_increase){
        ante += ante_increase;
        int increase = ante - player.getAnte();
        pot += player.placeBet(increase);
        final_player = player;
        last_move = "raise";
    }

    private TexasHoldEmUser nextTargetPlayer(){
        TexasHoldEmUser temp = players.get((players.indexOf(target_player) + 1) % players.size());
        while(temp.foldStatus()){
            temp = players.get((players.indexOf(temp) + 1) % players.size());
        }
        return temp;
    }

    private TexasHoldEmUser prevTargetPlayer(){
        TexasHoldEmUser temp = players.get((((players.indexOf(target_player) - 1) + num_users) % players.size()));

        while(temp.foldStatus()){
            temp = players.get((((players.indexOf(target_player) - 1) + players.size()) % players.size()));
        }

        return temp;
    }

    private int getActivePlayers(){
        int active = 0;
        for(TexasHoldEmUser player : players){
            if(!player.foldStatus()){ active++; }
        }
        return active;
    }

    private List<TexasHoldEmUser> getActiveUsers(){
        List<TexasHoldEmUser> active = new ArrayList<>();
        for(TexasHoldEmUser user : players){
            if(!user.foldStatus()){
                active.add(user);
            }
        }
        return active;
    }

    public void deal_hole(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < num_users; j++){
                players.get(j).draw(deck);
            }
        }
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

/*
    public List<TexasHoldEmUser> decideWinners(){
        List<Integer> high_indexes = new ArrayList<>();
        high_indexes.add(0);
        PokerHands high_hand = PokerHands.LOW;

        for(int i = 0; i < num_users; i++){
            if(players.get(i).foldStatus()){ continue; }
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_hand = player_high;
                if(i == 0){ continue; }
                high_indexes = new ArrayList<>();
                high_indexes.add(i);
            }else if(player_high.getValue() == high_hand.getValue()){
                high_indexes.add(i);
            }
        }
        List<TexasHoldEmUser> winners = new ArrayList<>();
        for(Integer value : high_indexes){
            winners.add(players.get(value));
        }
        return winners;
    }

 */
    public TexasHoldEmUser decideWinner(){
        int high_index = 0;
        PokerHands high_hand = PokerHands.LOW;

        for(int i = 0; i < num_users; i++){
            if(players.get(i).foldStatus()){ continue; }
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_index = i;
                high_hand = player_high;
            }
        }
        return players.get(high_index);
    }
    private int tiebreaker(TexasHoldEmUser player1, TexasHoldEmUser player2){
        List<Card> p1_total = getTotal(player1.getHand(), pit);
        List<Card> p2_total = getTotal(player2.getHand(), pit);
        Deck.CardManager.sortCards(p1_total);
        Deck.CardManager.sortCards(p2_total);
        removeDuplicates(p1_total, p2_total);
        for(int i = p1_total.size() - 1; i >= 0; i--){
            if(p1_total.get(i).getRank() > p2_total.get(i).getRank()){
                return 0;
            }else if(p2_total.get(i).getRank() > p1_total.get(i).getRank()){
                return 1;
            }
        }
        return 2;
    }

    private void removeDuplicates(List<Card> l0, List<Card> l1){
        List<Card> overlap = new ArrayList<>();
        for(Card card : l0){
            for(int i = 0; i < l1.size(); i++){
                if(card.getRank() == l1.get(i).getRank()){
                    overlap.add(l1.get(i));
                    l1.remove(l1.get(i));
                    break;
                }
            }
        }
        if(overlap.isEmpty()){ return;}
        for(Card card : overlap){
            for(int i = 0; i < l0.size(); i++){
                if(card.getRank() == l0.get(i).getRank()){
                    l0.remove(l0.get(i));
                    break;
                }
            }
        }
    }

    private List<Card> getTotal(List<Card> deck1, List<Card> deck2){
        List<Card> total = new ArrayList<>();
        total.addAll(deck1);
        total.addAll(deck2);
        return total;
    }

    private PokerHands getHigh(int index){

        List<Card> handAndPit = getTotal(players.get(index).getHand(), pit);

        Deck.CardManager.sortCards(handAndPit);

        PokerHands hh = convertHigh(handAndPit);

        players.get(index).setHigh_hand(hh);

        return hh;
    }

    public Map<Integer, Integer> countNumbers(List<Card> cards){
        //List<Integer> values = new ArrayList<>();
        Map<Integer, Integer> values = new Hashtable<>();

        int count = 1;

        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i).getRank() == cards.get(i - 1).getRank()){
                count++;
            }else{
                values.put(cards.get(i-1).getRank(), count);
                count = 1;
            }
        }
        values.put(cards.get(cards.size()-1).getRank(), count);

        return values;
    }

    public PokerHands convertHigh(List<Card> cards){
        if(royalFlush(cards)){
            return PokerHands.ROYAL_FLUSH;
        }else if(straightFlush(cards)){
            return PokerHands.STRAIGHT_FLUSH;
        }else if(fourOfAKind(cards)){
            return PokerHands.FOUR_OF_A_KIND;
        }else if(fullHouse(cards)){
            return PokerHands.FULL_HOUSE;
        }else if(flush(cards)){
            return PokerHands.FLUSH;
        }else if(straight(cards)){
            return PokerHands.STRAIGHT;
        }else if(threeOfAKind(cards)) {
            return PokerHands.THREE_OF_A_KIND;
        }else if(twoPair(cards)){
            return PokerHands.TWO_PAIR;
        }else if(pair(cards)){
            return PokerHands.PAIR;
        }else{
            return getHighEnum(cards);
        }
    }

    public boolean royalFlush(List<Card> cards){
        List<Card> temp = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() >= 10) {
                temp.add(card);
            }
        }
        if(temp.size() < 5){
            return false;
        }
        return straightFlush(temp);
    }
    public boolean straightFlush(List<Card> cards){
        for(int i = 0; i < cards.size() - 4; i++){
            int count = 1;
            for(int j = 1; j < 5; j++){
                if(Deck.CardManager.contains(cards, cards.get(i).getSuit(), cards.get(i).getRank() + j)){
                    if(++count == 5){ return true; }
                }
            }
        }
        return false;
    }
    public boolean fourOfAKind(List<Card> cards){

        Map<Integer, Integer> values = countNumbers(cards);

        for(Integer value : values.keySet()){
            if(values.get(value) == 4){ return true; }
        }

        return false;
    }
    public boolean fullHouse(List<Card> cards){

        Map<Integer, Integer> values = countNumbers(cards);

        int twoCount = 0, threeCount = 0;

        for(Integer value : values.keySet()){
            if(values.get(value) == 3){
                threeCount++;
            }else if(values.get(value) == 2){
                twoCount++;
            }
        }

        return threeCount == 2 || (threeCount == 1 && twoCount >= 1);
    }
    public boolean flush(List<Card> cards){
        for(Suit suit : Suit.values()){
            int count = 0;
            for(Card card : cards){
                if(card.getSuit() == suit){ count++; }
            }
            if(count >= 5){ return true; }
        }
        return false;
    }
    public boolean straight(List<Card> cards){
        for(int i = 0; i < cards.size() - 4; i++){
            int count = 1;
            for(int j = 1; j < 5; j++){
                if(Deck.CardManager.contains(cards, cards.get(i).getRank() + j)){
                    if(++count == 5){ return true; }
                }
            }
        }
        return false;
    }

    public boolean threeOfAKind(List<Card> cards){
        Map<Integer, Integer> values = countNumbers(cards);

        for(Integer i : values.keySet()){
            if(values.get(i) == 3){ return true; }
        }

        return false;
    }
    public boolean twoPair(List<Card> cards){
        Map<Integer, Integer> values = countNumbers(cards);
        int count = 0;

        for(Integer value : values.keySet()){
            if(values.get(value) == 2){ count++; }
        }

        return count >= 2;
    }

    public boolean pair(List<Card> cards){
        Map<Integer, Integer> values = countNumbers(cards);

        for(Integer i : values.keySet()){
            if(values.get(i) == 2){ return true; }
        }

        return false;
    }

    public PokerHands getHighEnum(List<Card> cards){
        switch(cards.get(cards.size() - 1).getRank()){
            case 2:
                return PokerHands.HIGH_TWO;
            case 3:
                return PokerHands.HIGH_THREE;
            case 4:
                return PokerHands.HIGH_FOUR;
            case 5:
                return PokerHands.HIGH_FIVE;
            case 6:
                return PokerHands.HIGH_SIX;
            case 7:
                return PokerHands.HIGH_SEVEN;
            case 8:
                return PokerHands.HIGH_EIGHT;
            case 9:
                return PokerHands.HIGH_NINE;
            case 10:
                return PokerHands.HIGH_TEN;
            case 11:
                return PokerHands.HIGH_JACK;
            case 12:
                return PokerHands.HIGH_QUEEN;
            case 13:
                return PokerHands.HIGH_KING;
            default:
                return PokerHands.HIGH_ACE;
        }
    }

    @Override
    public String toString(){
        return Integer.toString(gameId);
    }

}
