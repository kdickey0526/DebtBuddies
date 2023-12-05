package debtbuddies;

import debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEm;
import debtbuddies.GameServer.PlayerClasses.User;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    @Test
    public void TexasTest(){

        User user1 = new User("Kyle");
        User user2 = new User("Owen");
        User user3 = new User("Kevin");

        List<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        TexasHoldEm game = new TexasHoldEm(users, 1);

        game.initializeGame();

        assertTrue(game.isRunning());

    }

}
