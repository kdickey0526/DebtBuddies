package onetoone.GameServer;

import java.io.IOException;
import java.util.*;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.sql.Types.NULL;


/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */
@ServerEndpoint("/texasholdem/{username}")
@Component
public class TexasHoldEmServer {

    private Gson gson = new Gson();

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key

    private static Map < String, TexasHoldEmPlayer> usernamePlayerMap = new Hashtable < > ();
    private static Map < Session, TexasHoldEmPlayer > sessionPlayerMap = new Hashtable < > ();
    private static Map < TexasHoldEmPlayer , Session > playerSessionMap = new Hashtable < > ();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(TexasHoldEmServer.class);

    private static int num_players = 0;

    private static List<TexasHoldEmPlayer> players = new ArrayList<>();

    private static int running = 0;

    private static TexasHoldEmPlayer current_player;

    private static List<Card> pit;

    private static int pot;
    private static int ante;
    private static Deck deck;

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        // Handle the case of a duplicate username
        if (usernamePlayerMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            TexasHoldEmPlayer player = new TexasHoldEmPlayer(username);
            num_players++;
            players.add(player);
            // map current session with username
            sessionPlayerMap.put(session, player);

            // map current username with session
            playerSessionMap.put(player, session);

            usernamePlayerMap.put(username, player);

            // send to the user joining in
            sendMessageToPArticularUser(username, "Welcome to the chat server, "+username);

            // send to everyone in the chat
            broadcast("User: " + username + " has Joined the Chat");
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        Action action = gson.fromJson(message, Action.class);

        String actionValue = action.getAction();
        int value = action.getValue();

        // get the username by session
        TexasHoldEmPlayer player = sessionPlayerMap.get(session);

        StringBuilder sb = new StringBuilder(player.toString());
        sb.append(" has decided to ").append(actionValue);
        if(value != NULL){
            sb.append(" by ").append(value).append(" credits.\n");
        }
        broadcast(sb.toString());

        if(Objects.equals(actionValue, "start") && running == 0){
            if(num_players < 3){
                broadcast("\nNeed at least 3 players to start. Currently there are " + num_players + " players\n");
                return;
            }
            running = 1;
            pot = 0;
            ante = 10;
            pit = new ArrayList<>();
            deck = new Deck();
            deal_hole();
            send_hand();
            pot += players.get(0).placeBet(ante/2);
            pot += players.get(1).placeBet(ante);
            current_player = players.get(2);
            broadcast("\n" + players.get(0).toString() + " is small blind and bet 5 and " + players.get(1).toString() + " is big blind and bet 10 credits\nIt is now " + current_player.toString() + "'s turn");
        }else if(running == 1 && Objects.equals(player.toString(), current_player.toString())) {
            if (Objects.equals(actionValue, "fold")) {
                player.foldHand();
            } else if (Objects.equals(actionValue, "call")) {
                int before_bet = current_player.getBet();
                pot += current_player.placeBet(ante - current_player.getBet());
                broadcast(player.toString() + " is calling his bet from " + before_bet + " to " + ante + " credits\n");
                broadcast("The pot is now at " + pot + " credits\n");
                current_player.setBet(ante);
            } else if (Objects.equals(actionValue, "raise")) {
                ante += value;
                pot += current_player.placeBet(ante - current_player.getBet());
                broadcast(player.toString() + " is raising the ante from " + (ante-value) + " to " + ante + " credits\n");
                broadcast("The pot is now at " + pot + " credits\n");
            }else{
                broadcast(player.toString()+" used an invalid move, they lose their turn\n");
            }

            do{
                int ind = players.indexOf(current_player);
                if(ind + 1 == players.size()){
                    current_player = players.get(0);
                }else{
                    current_player = players.get(ind + 1);
                }
            }while(current_player.foldStatus());

            broadcast("It is now " + current_player.toString() + "'s turn\n");
        }else{
            logger.info("it did not work");
        }
    }

    public void send_hand(){
        for(TexasHoldEmPlayer player : sessionPlayerMap.values()){
            sendMessageToPArticularUser(player.toString(), player.handToString());
        }
    }

    public void send_community(){
        StringBuilder sb = new StringBuilder();
        sb.append("---------------\n");
        for (Card card : pit) {
            sb.append(card.toString());
        }
        sb.append("---------------\n");
        broadcast(sb.toString());
    }
/*
    public void game(){
        deal_hole();

        bettingRound();

        flop();

        bettingRound();

        river();

        bettingRound();

        decideWinner();
    }
 */

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        TexasHoldEmPlayer player = sessionPlayerMap.get(session);

        // server side log
        logger.info("[onClose] " + player.toString());

        // remove user from memory mappings
        usernamePlayerMap.remove(player.toString());
        sessionPlayerMap.remove(session);
        playerSessionMap.remove(player);
        players.remove(sessionPlayerMap.get(session));

        // send the message to chat
        broadcast(player.toString() + " disconnected");
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionPlayerMap.get(session).toString();

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param username The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToPArticularUser(String username, String message) {
        try {
            playerSessionMap.get(usernamePlayerMap.get(username)).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionPlayerMap.forEach((session, player) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

    public void deal_hole(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < num_players; j++){
                players.get(j).draw(deck);
            }
        }
    }

    public void flop(){
        for(int i = 0; i < 3; i++){
            pit.add(deck.draw());
        }
    }

    public void turn(){
        pit.add(deck.draw());
    }

    public void river(){
        pit.add(deck.draw());
    }

    public TexasHoldEmPlayer decideWinner(){
        int high_index = 0;
        PokerHands high_hand = PokerHands.LOW;

        for(int i = 0; i < num_players; i++){
            PokerHands player_high = getHigh(i);
            if(player_high.getValue() > high_hand.getValue()){
                high_index = i;
                high_hand = player_high;
            }
        }
        return players.get(high_index);
    }

    private PokerHands getHigh(int index){
        List<Card> handAndPit = new ArrayList<>();
        handAndPit.addAll(players.get(index).getHand());
        handAndPit.addAll(pit);

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
