package onetoone.GameServer.Games.TexasHoldEm;

import onetoone.GameServer.DeckLibrary.Card;

public class UserInfo {

    private int bank;

    private int bet;

    private int ante;

    private Card card1;

    private Card card2;

    public int getBank(){
        return bank;
    }

    public int getBet(){
        return bet;
    }

    public int getAnte(){
        return ante;
    }

    public Card getCard(int index){
        return index == 0 ? card1 : card2;
    }

    public void setBank(int bank){
        this.bank = bank;
    }

    public void setBet(int bet){
        this.bet = bet;
    }

    public void setAnte(int ante){
        this.ante = ante;
    }

    public void setCard(int index, Card card){
        if(index == 0){
            card1 = card;
        }else{
            card2 = card;
        }
    }

    public void setInfo(int bank, int bet, int ante, Card card1, Card card2){
        setBank(bank);
        setBet(bet);
        setAnte(ante);
        setCard(0, card1);
        setCard(1, card2);
    }

}
