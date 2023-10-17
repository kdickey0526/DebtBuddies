package onetoone.GameServer;

public class Action {
    private String game;
    private int gameid;
    private Move move;

    public Move getMove() {
        return move;
    }

    public int getGameid(){ return gameid; }

    public String getGame(){ return game; }
}
