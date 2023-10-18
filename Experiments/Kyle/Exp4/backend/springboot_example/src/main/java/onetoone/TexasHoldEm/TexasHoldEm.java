package onetoone.TexasHoldEm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import onetoone.DeckLibrary.Card;
import onetoone.DeckLibrary.Deck;
import onetoone.DeckLibrary.Suit;
import onetoone.GameServer.*;
import onetoone.PlayerClasses.Player;
import onetoone.PlayerClasses.TexasHoldEmPlayer;

public class TexasHoldEm {

    private final int BASE_ANTE = 10;
    private List<TexasHoldEmPlayer> players;
    private Deck deck;
    private List<Card> pit;
    private int pot;
    private int gameId;
    private int ante;
    private int stage;
    private int num_players;

    private int running = 0;
    private int p_index = 0;
    private TexasHoldEmPlayer target_player;

    private TexasHoldEmPlayer final_player;

    private Gson gson = new Gson();

    public TexasHoldEm(List<TexasHoldEmPlayer> players, int gameId){
        this.players = new ArrayList<>(players);
        this.gameId = gameId;
    }

    public TexasHoldEm(int gameId){
        this.gameId = gameId;
    }

    public void addPlayer(Player player){
        players.add((TexasHoldEmPlayer) player);
    }

    public Response getResponse(TexasHoldEmPlayer player, Action action){
        Response response = new Response();
        if(running == 0 && Objects.equals(action.getAction(), "start")){
            initializeGame();
            response.addMessage(new Message(castToPlayers(), player.toString() + " has started the game"));
            response.addResponse(sendHands());
            return response;
        }else if(running == 1 && target_player == player){
            int previous_ante = ante;
            switch(action.getAction()) {
                case "fold":
                    fold(player);
                    response.addMessage(new Message(castToPlayers(), player.toString() + " has folded"));
                    break;
                case "call":
                    call(player);
                    response.addMessage(new Message(castToPlayers(), player.toString() + " called\nThe pot is now at " + Integer.toString(pot)));
                    break;
                case "raise":
                    raise(player, action.getValue());
                    response.addMessage(new Message(castToPlayers(), player.toString() + " has raised the ante by " + action.getValue() + "\nThe pot is now at " + pot + " and the ante is " + ante));
                    break;
                default:
                    response.addMessage(new Message(player, "invalid move"));
                    return response;
            }
            if(getActivePlayers() == 1){
                TexasHoldEmPlayer winner = end_game();
                response.addMessage(new Message(castToPlayers(), winner.toString() + " won the game."));
                return response;
            }
            if(Objects.equals(action.getAction(), "call") || Objects.equals(action.getAction(), "fold")){
                if(Objects.equals(action.getAction(),"call") && player.getBet() == previous_ante && nextTargetPlayer() == final_player) {
                    stage++;
                    if (stage == 1) {
                        flop();
                        response.addMessage(new Message(castToPlayers(), getCommunityString()));
                    } else if (stage == 2) {
                        turn();
                        response.addMessage(new Message(castToPlayers(), getCommunityString()));
                    } else if (stage == 3) {
                        river();
                        response.addMessage(new Message(castToPlayers(), getCommunityString()));
                    } else {
                        TexasHoldEmPlayer winner = end_game();
                        response.addMessage(new Message(castToPlayers(), winner.toString() + " won the game."));
                    }
                }
            }
            if(running == 1) {
                target_player = nextTargetPlayer();
                response.addMessage(new Message(castToPlayers(), "It is now " + target_player.toString() + "'s turn"));
            }
        }
        return response;
    }

    public List<Player> castToPlayers(){
        List<Player> temp = new ArrayList<>();
        for (TexasHoldEmPlayer player : players) {
            temp.add((Player) player);
        }
        return temp;
    }

    public Response sendHands(){

        Response response = new Response();

        Event event;

        for(TexasHoldEmPlayer player : players){

            event = new Event("hole", new Action("{\"card1\":\""+player.getHand().get(0).toString()+"\",\"card2\":\""+player.getHand().get(1).toString()+"\"}"));

            response.addMessage(new Message(player, gson.toJson(event)));
        }

        event = new Event("message", new Action("{\"value\":\"It is now " + target_player + "'s turn\"}"));

        response.addMessage(new Message(getAllPlayers(), gson.toJson(event)));

        return response;
    }

    private TexasHoldEmPlayer end_game(){
        running = 0;
        if(getActivePlayers() == 1){
            for(TexasHoldEmPlayer player : players){
                if(player.foldStatus()){
                    return player;
                }
            }
        }
        return decideWinner();
    }

    private String getCommunityString(){
        StringBuilder sb = new StringBuilder();
        for(Card card : pit){
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }

    private void fold(TexasHoldEmPlayer player){
        player.foldHand();
    }

    private void call(TexasHoldEmPlayer player){
        int increase = ante - player.getBet();
        pot += player.placeBet(increase);
    }

    private void raise(TexasHoldEmPlayer player, int ante_increase){
        ante += ante_increase;
        int increase = ante - player.getBet();
        player.placeBet(increase);
        final_player = player;
    }

    private TexasHoldEmPlayer nextTargetPlayer(){
        return players.get((players.indexOf(target_player) + 1) % players.size());
    }

    private void initializeGame(){
        num_players = players.size();
        pit = new ArrayList<>();
        deck = new Deck();
        ante = BASE_ANTE;
        running = 1;
        stage = 0;
        pot = 0;
        target_player = players.get(p_index++ % num_players);
        final_player = target_player;
        for(TexasHoldEmPlayer player : players){
            player.clearInventory();
        }
        deal_hole();
    }

    private int getActivePlayers(){
        int active = 0;
        for(TexasHoldEmPlayer player : players){
            if(!player.foldStatus()){ active++; }
        }
        return active;
    }

    private List<Player> getAllPlayers(){
        List<Player> all_players = new ArrayList<>();
        for(TexasHoldEmPlayer t_player : players){
            all_players.add((Player) t_player);
        }
        return all_players;
    }

    public void deal_hole(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < num_players; j++){
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

    public int getGameId(){
        return gameId;
    }
    public TexasHoldEmPlayer decideWinner(){
        int high_index = 0;
        PokerHands high_hand = PokerHands.LOW;

        for(int i = 0; i < num_players; i++){
            if(players.get(i).foldStatus()){ continue; }
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_index = i;
                high_hand = player_high;
            }else if(player_high.getValue() == high_hand.getValue()){
                TexasHoldEmPlayer player1 = players.get(high_index);
                TexasHoldEmPlayer player2 = players.get(i);
                //int winner = tiebreaker(player1, player2);
                int winner = 1;
                if(winner == 1){
                    high_index = i;
                }
            }
        }
        return players.get(high_index);
    }

    private int tiebreaker(TexasHoldEmPlayer player1, TexasHoldEmPlayer player2){
        List<Card> p1_total = getTotal(player1.getHand(), pit);
        List<Card> p2_total = getTotal(player2.getHand(), pit);
        Deck.CardManager.sortCards(p1_total);
        Deck.CardManager.sortCards(p2_total);
        for(int i = p1_total.size() - 1; i >= 0; i--){
            if(p1_total.get(i).getRank() > p2_total.get(i).getRank()){
                return 0;
            }else if(p2_total.get(i).getRank() > p1_total.get(i).getRank()){
                return 1;
            }
        }
        return 2;
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

        return convertHigh(handAndPit);
    }

    private List<Integer> countNumbers(List<Card> cards){
        List<Integer> values = new ArrayList<>();

        int count = 1;

        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i).getRank() == cards.get(i - 1).getRank()){
                count++;
            }else{
                values.add(count);
                count = 1;
            }
        }
        return values;
    }

    private PokerHands convertHigh(List<Card> cards){
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

    private boolean royalFlush(List<Card> cards){
        List<Card> temp = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() >= 10) {
                temp.add(card);
            }
        }
        if(temp.size() < 5){
            return false;
        }else{
            return straightFlush(temp);
        }
    }

    private boolean straightFlush(List<Card> cards){
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

    private boolean fourOfAKind(List<Card> cards){

        List<Integer> values = countNumbers(cards);

        for(Integer value : values){
            if(values.get(value) == 4){ return true; }
        }

        return false;
    }

    private boolean fullHouse(List<Card> cards){

        List<Integer> values = countNumbers(cards);

        int twoCount = 0, threeCount = 0;

        for(Integer value : values){
            if(values.get(value) == 3){
                threeCount++;
            }else if(values.get(value) == 2){
                twoCount++;
            }
        }

        return threeCount == 2 || (threeCount == 1 && twoCount >= 1);
    }

    private boolean flush(List<Card> cards){
        for(Suit suit : Suit.values()){
            int count = 0;
            for(Card card : cards){
                if(card.getSuit() == suit){ count++; }
            }
            if(count >= 5){ return true; }
        }
        return false;
    }

    private boolean straight(List<Card> cards){
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

    private boolean threeOfAKind(List<Card> cards){
        List<Integer> values = countNumbers(cards);

        for(Integer i : values){
            if(values.get(i) == 3){ return true; }
        }

        return false;
    }

    private boolean twoPair(List<Card> cards){
        List<Integer> values = countNumbers(cards);
        int count = 0;

        for(Integer value : values){
            if(values.get(value) == 2){ count++; }
        }

        return count >= 2;
    }

    private boolean pair(List<Card> cards){
        List<Integer> values = countNumbers(cards);

        for(Integer i : values){
            if(values.get(i) == 2){ return true; }
        }

        return false;
    }

    private PokerHands getHighEnum(List<Card> cards){
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

}
