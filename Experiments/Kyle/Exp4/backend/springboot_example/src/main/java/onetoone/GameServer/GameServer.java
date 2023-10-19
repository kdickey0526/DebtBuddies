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

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.PlayerClasses.User;
import onetoone.GameServer.Communication.Responses.Message;
import onetoone.GameServer.Communication.Responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


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
@ServerEndpoint("/gameserver/{game}/{username}")
@Component
public class GameServer {

    private Gson gson = new Gson();

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key

    private static Map < String, User> usernamePlayerMap = new Hashtable < > ();
    private static Map < Session, User> sessionPlayerMap = new Hashtable < > ();
    private static Map <User, Session > playerSessionMap = new Hashtable < > ();

    private static Map < String, String > usernameGameMap = new Hashtable < > ();
    private static List<User> users = new ArrayList<>();
    private static int num_players = 0;

    private final Logger logger = LoggerFactory.getLogger(GameServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("game") String game, @PathParam("username") String username) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        // Handle the case of a duplicate username
        if (usernamePlayerMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            User user = new User(username);
            num_players++;
            users.add(user);

            usernameGameMap.put(username, game);
            // map current session with username
            sessionPlayerMap.put(session, user);

            // map current username with session
            playerSessionMap.put(user, session);

            usernamePlayerMap.put(username, user);

            // send to the user joining in
            sendMessageToParticularUser(username, "Welcome to the chat server, "+username);

            // send to everyone in the chat
            //broadcast("User: " + username + " has Joined the Chat");
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

        /**
         * {"event":*String event*,"action":*Action action*}
         */

        User user = sessionPlayerMap.get(session);

        ServerEvent serverEvent = gson.fromJson(message, ServerEvent.class);

        logger.info(user.toString() + " sent " + message);

        Response response = Manager.getResponse(usernameGameMap.get(user.toString()), user, serverEvent);

        List<Message> messages = response.getMessages();

        for (Message value : messages) {
            logger.info("[Message]: " + value.getMessage());
            List<User> m_users = value.getPlayers();
            for (User m_user : m_users) {
                sendMessageToParticularUser(m_user.toString(), value.getMessage());
            }
        }
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        User user = sessionPlayerMap.get(session);

        // server side log
        logger.info("[onClose] " + user.toString());

        // remove user from memory mappings
        usernamePlayerMap.remove(user.toString());
        sessionPlayerMap.remove(session);
        playerSessionMap.remove(user);
        users.remove(sessionPlayerMap.get(session));

        num_players--;

        // send the message to chat
        broadcast(user.toString() + " disconnected");
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
    private void sendMessageToParticularUser(String username, String message) {
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
}
