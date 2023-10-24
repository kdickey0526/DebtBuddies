package onetoone.GameServer.Games.TexasHoldEm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.DeckLibrary.Deck;
import onetoone.GameServer.DeckLibrary.Suit;
import onetoone.GameServer.Communication.Events.GameEvent;
import onetoone.GameServer.Games.Game;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.Games.GameInterface;
import onetoone.GameServer.PlayerClasses.User;

public class TexasHoldEm extends Game<TexasHoldEmUser> implements GameInterface<TexasHoldEmUser, TexasHoldEm> {

    private int QUEUE_SIZE = 3;
    private final int BASE_ANTE = 10;
    private Deck deck;
    private List<Card> pit;
    private int pot;
    private int ante;
    private int stage;
    private int p_index = 0;

    private String last_turn;
    private TexasHoldEmUser target_user;
    private TexasHoldEmUser final_user;

    private GameInfo gameInfo = new GameInfo();

    private UserInfo userInfo = new UserInfo();

    public TexasHoldEm(List<TexasHoldEmUser> users, int gameId){
        super(users, gameId, 3);
    }

    public TexasHoldEm(){
        super();
        queue_size = QUEUE_SIZE;
    }

    @Override
    protected void initializeGame(){
        gameInfo.setPlayers(getUsernames());
        num_users = users.size();
        pit = new ArrayList<>();
        deck = new Deck();
        ante = BASE_ANTE;
        running = 1;
        stage = 0;
        pot = 0;
        target_user = users.get(p_index++ % num_users);
        final_user = target_user;
        last_turn = "hand";
        for(TexasHoldEmUser user : users){
            user.clearInventory();
        }
        deal_hole();
    }

    @Override
    public TexasHoldEm getNewGame(List<TexasHoldEmUser> queue, int gameId){
        return new TexasHoldEm(queue, gameId);
    }

    @Override
    public TexasHoldEmUser getNewUser(User user){
        return new TexasHoldEmUser(user);
    }

    public void getResponse(TexasHoldEmUser user, ServerEvent serverEvent){

        String message;

        if(running == 0 && Objects.equals(serverEvent.getAction(), "start")){

            initializeGame();
            Response.addMessage(getAllUsers(), "start", user.toString());
            sendHands();
            //blinds
            //send game info and player info

        }else if(running == 1 && target_user == user){

            int previous_ante = ante;

            switch(serverEvent.getAction()) {
                case "fold":
                    fold(user);
                    gameInfo.setLastTurn(user.toString());
                    gameInfo.setLastPlay("fold");
                    //Response.addMessage(getAllUsers(), "fold", user.toString());
                    break;
                case "call":
                    call(user);
                    gameInfo.setLastTurn(user.toString());
                    gameInfo.setLastPlay("call");
                    //message = "{\"user\":\""+ user +"\",\"pot\":"+ pot +"}";
                    //Response.addMessage(getAllUsers(), "call", message);
                    break;
                case "raise":
                    //TexasMessage.sendGameInfo(this)
                    raise(user, serverEvent.getValue());
                    gameInfo.setLastTurn(user.toString());
                    gameInfo.setLastPlay("raise");
                    //message = "{\"user\":\""+ user +"\",\"pot\":"+ pot +",\"ante\":"+ante+"}";
                    //Response.addMessage(getAllUsers(), "raise", message);
                    break;
                default:
                    Response.addMessage(user, "message", "invalid move");
                    return;
            }
            if(getActivePlayers() == 1){
                TexasHoldEmUser winner = end_game();
                gameInfo.setLastTurn(winner.toString());
                sendGameUpdate("end");
                //Response.addMessage(getAllUsers(), "winner", winner.toString());
                return;
            }else{
                sendUserUpdate(user);
            }
            if((Objects.equals(serverEvent.getAction(), "call") || Objects.equals(serverEvent.getAction(), "fold")) && nextTargetPlayer() == final_user) {
                stage++;
                if (stage == 1) {
                    flop();
                    gameInfo.setPit(pit);
                    sendGameUpdate("stage");
                    //Response.addMessage(getAllUsers(), "flop", getCommunityJson());
                } else if (stage == 2) {
                    turn();
                    gameInfo.setPit(pit);
                    sendGameUpdate("stage");
                    //Response.addMessage(getAllUsers(), "round", getCommunityJson());
                } else if (stage == 3) {
                    river();
                    gameInfo.setPit(pit);
                    sendGameUpdate("stage");
                    //Response.addMessage(getAllUsers(), "river", getCommunityJson());
                } else {
                    TexasHoldEmUser winner = end_game();
                    gameInfo.setLastTurn(winner.toString());
                    sendGameUpdate("end");
                    //Response.addMessage(getAllUsers(), "winner", winner.toString());
                    return;
                }
            }else{
                target_user = nextTargetPlayer();
                gameInfo.setCurrentTurn(target_user.toString());
                sendGameUpdate("turn");
                Response.addMessage(getAllUsers(), "turn", target_user.toString());
            }
        }
    }

    private void smallAndBigBlind(){
        
    }

    public void sendGameUpdate(String update_type){
        Response.addMessage(getAllUsers(), update_type, gson.toJson(gameInfo));
    }

    public void sendUserUpdate(TexasHoldEmUser user){
        userInfo.setInfo(user.getBalance(), user.getBet(), user.getAnte(), user.getHand().get(0), user.getHand().get(1));
        Response.addMessage(user, "update", gson.toJson(userInfo));
    }

    private List<String> getUsernames(){
        List<String> temp = new ArrayList<>();

        for(User user : users){
            temp.add(user.toString());
        }

        return temp;
    }

    public String getCommunityJson(){
        StringBuilder sb = new StringBuilder("{");
        for(int i = 0; i < pit.size(); i++){
            sb.append("\"card").append(i).append("\":\"").append(pit.get(i).toString()).append("\"");
            if(i != pit.size() - 1){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public int getPot(){
        return pot;
    }
    public void sendHands(){

        for(TexasHoldEmUser user : users){
            String message = "{\"card1\":\""+user.getHand().get(0).toString()+"\",\"card2\":\""+user.getHand().get(1).toString()+"\"}";
            Response.addMessage(user, "hole", message);
        }

        Response.addMessage(getAllUsers(), "turn", target_user.toString());
    }

    private TexasHoldEmUser end_game(){
        running = 0;
        if(getActivePlayers() == 1){
            for(TexasHoldEmUser user : users){
                if(!user.foldStatus()){
                    return user;
                }
            }
        }
        return decideWinner();
    }

    public TexasHoldEmUser getFinalUser() {
        return final_user;
    }

    public int getAnte(){
        return ante;
    }

    public String getLastTurn(){
        return last_turn;
    }

    private void fold(TexasHoldEmUser player){
        player.foldHand();
    }

    private void call(TexasHoldEmUser player){
        int increase = ante - player.getBet();
        pot += player.placeBet(increase);
        gameInfo.setPot(pot);
    }

    private void raise(TexasHoldEmUser player, int ante_increase){
        ante += ante_increase;
        int increase = ante - player.getBet();
        pot += player.placeBet(increase);
        final_user = player;
        gameInfo.setAnte(ante);
        gameInfo.setPot(pot);
    }

    private TexasHoldEmUser nextTargetPlayer(){
        return users.get((users.indexOf(target_user) + 1) % users.size());
    }

    private int getActivePlayers(){
        int active = 0;
        for(TexasHoldEmUser user : users){
            if(!user.foldStatus()){ active++; }
        }
        return active;
    }

    public void deal_hole(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < num_users; j++){
                users.get(j).draw(deck);
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

    public TexasHoldEmUser decideWinner(){
        int high_index = 0;
        PokerHands high_hand = PokerHands.LOW;

        for(int i = 0; i < num_users; i++){
            if(users.get(i).foldStatus()){ continue; }
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_index = i;
                high_hand = player_high;
            }else if(player_high.getValue() == high_hand.getValue()){
                TexasHoldEmUser player1 = users.get(high_index);
                TexasHoldEmUser player2 = users.get(i);
                //int winner = tiebreaker(player1, player2);
                int winner = 1;
                if(winner == 1){
                    high_index = i;
                }
            }
        }
        return users.get(high_index);
    }
    private int tiebreaker(TexasHoldEmUser player1, TexasHoldEmUser player2){
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

        List<Card> handAndPit = getTotal(users.get(index).getHand(), pit);

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

    @Override
    public String toString(){
        return Integer.toString(gameId);
    }

}
