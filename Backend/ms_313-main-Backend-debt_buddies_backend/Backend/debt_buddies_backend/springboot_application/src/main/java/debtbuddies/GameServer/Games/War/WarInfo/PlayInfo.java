package debtbuddies.GameServer.Games.War.WarInfo;

public class PlayInfo {

    private String next;

    private String player;

    private String suit;

    private int rank;

    public PlayInfo(String next, String player, String suit, int rank){
        this.next = next;
        this.player = player;
        this.suit = suit;
        this.rank = rank;
    }

}
