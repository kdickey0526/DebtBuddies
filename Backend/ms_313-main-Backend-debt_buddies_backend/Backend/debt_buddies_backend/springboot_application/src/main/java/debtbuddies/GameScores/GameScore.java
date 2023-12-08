package debtbuddies.GameScores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import debtbuddies.person.Person;

/**
 * 
 * @author Vivek Bengre
 */ 

@Entity
public class GameScore {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int whack;
    private int warWon;
    private int warLost;
    private int blackJack;
    private String userName;
    //
    //private int WarLost;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToOne
    @JsonIgnore
    private Person person;

    public GameScore(int whack, int WarWon, int BlackJack, int WarLost, String userName) {
        this.whack = whack;
        this.warWon = WarWon;
        this.blackJack = BlackJack;
        this.warLost = WarLost;
        this.userName = userName;
    }

    public GameScore() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getwhack(){
        return whack;
    }

    public void setwhack(int whack){
        this.whack = whack;
    }

    public int getBlackJack(){
        return blackJack;
    }

    public void setBlackJack(int BlackJack){
        this.blackJack = BlackJack;
    }

    public int getWarLost(){
        return warLost;
    }

    public void setWarLost(int warLost){
        this.warLost = warLost;
    }

    public String getuserName(){
        return userName;
    }

    public void setuserName(String userName){
        this.userName = userName;
    }
    public int getWarWon(){
        return warWon;
    }

    public void setWarWon(int WarWon){
        this.warWon = WarWon;
    }

    public Person getPerson(){
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }


}
