package onetoone.GameServer.Games;

import onetoone.GameServer.Communication.Events.ServerEvent;
import onetoone.GameServer.Communication.Responses.Response;
import onetoone.GameServer.PlayerClasses.Player;

import java.util.*;

public interface GameInterface <T>{

    public Response getResponse(T player, ServerEvent serverEvent);

}
