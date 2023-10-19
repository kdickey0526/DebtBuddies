package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Response;

public interface GameInterface <T>{

    public void getResponse(T player, ServerEvent serverEvent);

}
