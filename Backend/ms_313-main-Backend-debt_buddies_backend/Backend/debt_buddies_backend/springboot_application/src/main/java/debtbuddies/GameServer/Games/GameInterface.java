package debtbuddies.GameServer.Games;

import debtbuddies.GameServer.Communication.ServerEvent;
import debtbuddies.GameServer.PlayerClasses.Group;
import debtbuddies.GameServer.PlayerClasses.User;

import java.util.List;

public interface GameInterface <T, K>{

    void getResponse(User user, ServerEvent serverEvent);

    K getNewGame(Group lobby, int gameId);

    T getNewUser(User user);

    int getQueueSize();

}
