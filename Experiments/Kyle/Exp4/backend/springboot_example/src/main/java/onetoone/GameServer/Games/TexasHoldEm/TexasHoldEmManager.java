package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.Games.GameManager;
import onetoone.GameServer.PlayerClasses.User;

import java.util.*;

public class TexasHoldEmManager extends GameManager<TexasHoldEmUser, TexasHoldEm > {


    public TexasHoldEmManager(){
        super();
    }

    @Override
    protected TexasHoldEmUser getNewUser(User user) {
        return new TexasHoldEmUser(user);
    }

    @Override
    protected TexasHoldEm getNewGame(List<TexasHoldEmUser> queue, Integer gameId) {
        return new TexasHoldEm(queue, gameId);
    }

}