package debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

public class TurnInfo {

    private String move;

    private String next;

    private int pot;

    private int ante;

    public TurnInfo(String move, String next, int pot, int ante){
        this.move = move;
        this.next = next;
        this.pot = pot;
        this.ante = ante;
    }

}
