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
    private int num_players;
    private int p_index = 0;
    private TexasHoldEmPlayer target_player;

    public TexasHoldEm(List<TexasHoldEmPlayer> players, int gameId){
        this.players = players;
        this.gameId = gameId;
        initializeGame();
    }

    public Response getResponse(TexasHoldEmPlayer player, Move move){
        String message = "Error";
        List<Player> k = new ArrayList<>();
        k.add(player);
        Response response = new Response(k, "Error");
        if(running == 0 && Objects.equals(move.getPlay(), "start")){
            running = 1;
            message = player.toString() + " has started the game";
        }else if(running == 1 && target_player == player){
            int previous_ante = ante;
            switch(move.getPlay()) {
                case "fold":
                    fold(player);
                    message = player.toString() + " has folded";
                    break;
                case "call":
                    call(player);
                    message = player.toString() + " called\nThe pot is now at " + Integer.toString(pot);
                    break;
                case "raise":
                    raise(player, move.getValue());
                    message = player.toString() + " has raised the ante by " + move.getValue() + "\nThe pot is now at " + pot + " and the ante is " + ante;
                    break;
                default:
                    message = "invalid move";
            }
            if(getActivePlayers() > 1){
                target_player = nextTargetPlayer();
            }else{
                end_game();
            }
            if(Objects.equals(move.getPlay(), "call") || Objects.equals(move.getPlay(), "fold")){
                if(Objects.equals(move.getPlay(),"call") && player.getBet() == previous_ante && target_player.getBet() == previous_ante){
                    stage++;
                    if(stage == 1){
                        flop();
                    }else if(stage == 2){
                        turn();
                    }else if(stage == 3){
                        river();
                    }else{
                        end_game();
                    }
                    response.setPlayers(getAllPlayers());
                    message = getCommunityString();
                }else{

                }
            }
        }
        response.addMessage(message);
        return response;
    }

    public Response sendHands(){
        Response response = new Response();

        for(TexasHoldEmPlayer player : players){
            response.addPlayer(player);
            StringBuilder handstring = new StringBuilder();
            for(Card card : player.getHand()){
                handstring.append(card.toString()).append("\n");
            }
            response.addMessage(handstring.toString());
        }
        return response;
    }

    private void end_game(){

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
        num_players = players.size();
        pit = new ArrayList<>();
        deck = new Deck();
        ante = BASE_ANTE;
        running = 0;
        stage = 0;
        pot = 0;
        target_player = players.get(p_index++ % num_players);
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
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_index = i;
                high_hand = player_high;
            }else if(player_high.getValue() == high_hand.getValue()){
                TexasHoldEmPlayer player1 = players.get(high_index);
                TexasHoldEmPlayer player2 = players.get(i);
                int winner = tiebreaker(player1, player2);
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

        return straightFlush(temp);
    }

    private boolean straightFlush(List<Card> cards){
        for(int i = 0; i < 3; i++){
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
        for(int i = 0; i < 3; i++){
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
