package debtbuddies.GameServer.Communication;

import java.util.*;

public class LobbyInfo {

    private String type;

    private int lobbyId;

    private List<String> players;

    public LobbyInfo(String type){
        this.type = type;
    }

    public LobbyInfo(String type, int lobbyId){
        this.type = type;
        this.lobbyId = lobbyId;
    }

    public LobbyInfo(String type, int lobbyId, List<String> players){
        this.type = type;
        this.lobbyId = lobbyId;
        this.players = players;
    }
}
