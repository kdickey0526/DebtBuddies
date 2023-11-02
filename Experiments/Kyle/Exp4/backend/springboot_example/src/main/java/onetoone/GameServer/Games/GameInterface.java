package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.ServerEvent;
import onetoone.GameServer.PlayerClasses.Group;
import onetoone.GameServer.PlayerClasses.User;

import java.util.List;

public interface GameInterface <T, K>{

    void getResponse(User user, ServerEvent serverEvent);

    K getNewGame(Group queue, int gameId);

    T getNewUser(User user);

    void convertUsers(List<User> users);

    int getQueueSize();

}
