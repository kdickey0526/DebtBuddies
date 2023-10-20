package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEm;
import onetoone.GameServer.Games.TexasHoldEm.TexasHoldEmUser;
import onetoone.GameServer.PlayerClasses.User;

import java.util.List;

public interface GameInterface <T, K>{

    void getResponse(T player, ServerEvent serverEvent);

    K getNewGame(List<T> queue, int gameId);

    T getNewUser(User user);

    int getQueueSize();

}
