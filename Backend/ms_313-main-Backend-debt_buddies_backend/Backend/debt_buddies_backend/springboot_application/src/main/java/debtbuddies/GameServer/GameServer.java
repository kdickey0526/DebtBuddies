package debtbuddies.GameServer;

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

import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.Communication.MessageBearer;
import debtbuddies.GameServer.PlayerClasses.User;
import debtbuddies.GameServer.Communication.Response;
import debtbuddies.Users.UserRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static UserRepository Repo;

    @Autowired
    public void setUserRepository(UserRepository Repo){
        this.Repo = Repo;
    }
    private static Gson gson = new Gson();

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key

    private static Map < Session , User > sessionUserMap = new Hashtable<>();
    private static Map < User , Session > userSessionMap = new Hashtable<>();
    private static Map < User , String > userGameMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(GameServer.class);

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("game") String game, @PathParam("username") String username/*, @PathParam("id") int id*/) throws IOException {

        // server side log
        logger.info("[onOpen] " + username);
        //logger.info("[onOpen] " + Repo.toString());
        int coins = 25;
        int f_id = 0;
        /*
        debtbuddies.Users.User cl = Repo.findById(id);
        if(cl != null) {
            username = cl.getUserName();
            logger.info("[onOpen] " + username);
            f_id = cl.getId();
            coins = cl.getCoins();
        }
         */

        if(sessionUserMap.containsKey(session)){
            session.close();
        }else{
            User user = new User(username, f_id, coins);
            logger.info("[onOpen] created: " + user.toString());
            sessionUserMap.put(session, user);
            userSessionMap.put(user, session);
            userGameMap.put(user, game);
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

        User user = sessionUserMap.get(session);

        ServerEvent serverEvent = gson.fromJson(message, ServerEvent.class);

        logger.info(user.toString() + " sent " + message);

        //Repo.findBy(user.toString());

        Manager.getResponse(userGameMap.get(user), user, serverEvent);

        for (MessageBearer messageBearer : Response.getMessages()) {
            logger.info("[Message]: " + message);
            for (User recipient : messageBearer.getRecipients()) {
                sendMessageToParticularUser(recipient, messageBearer.getMessageString());
            }
        }

        Response.clearMessages();
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        User user = sessionUserMap.get(session);

        // server side log
        logger.info("[onClose] " + user.toString());

        // remove user from memory mappings
        sessionUserMap.remove(session);
        userSessionMap.remove(user);
        userGameMap.remove(user);

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
        String username = sessionUserMap.get(session).toString();

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param user The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToParticularUser(User user, String message) {
        try {
            userSessionMap.get(user).getBasicRemote().sendText(message);
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
        sessionUserMap.forEach((session, user) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

}
