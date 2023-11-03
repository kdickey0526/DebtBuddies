package debtbuddies.GameServer.Games.TexasHoldEm.TexasHoldEmInfo;

public class PlayerInfo {

    private int balance;

    private int money_in_pot;

    private int money_in_pot_round;

    public PlayerInfo(int balance, int money_in_pot, int money_in_pot_round){
        this.balance = balance;
        this.money_in_pot = money_in_pot;
        this.money_in_pot_round = money_in_pot_round;
    }

}
