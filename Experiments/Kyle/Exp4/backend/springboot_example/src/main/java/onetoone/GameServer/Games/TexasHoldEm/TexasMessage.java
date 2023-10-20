package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.DeckLibrary.Card;
import onetoone.GameServer.PlayerClasses.User;

public class TexasMessage {

    public class UserInfo{
        private int bank;
        private int bet;
        private int ante;
        private Card card1;
        private Card card2;

        public UserInfo(User user){

        }
    }

    public class GameInfo{

        private TexasHoldEmUser user;

        private String turn;
        private int pot;

        private int ante;

        public GameInfo(TexasHoldEm game){
            user = game.getFinalUser();
            pot = game.getPot();
            ante = game.getAnte();

        }
    }

    public GameInfo sendGameInfo(TexasHoldEm game){
        return new GameInfo(game);
    }

    public UserInfo sendUserInfo(TexasHoldEmUser user){
        return new UserInfo(user);
    }

}
