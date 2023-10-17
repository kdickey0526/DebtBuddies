package onetomany.Friend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Users.User;

import javax.persistence.CascadeType;


/**
 * 
 * @author Vivek Bengre
 */ 

@Entity
public class Friend {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Friend1
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private User user1;

    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user2;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToOne
    @JsonIgnore
    private User user;

    public Friend(String Friend1, String Friend2) {
        this.user1 = Friend1;
        this.user2 = Friend2;
    }

    public Laptop() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getFriend1(){
        return user1;
    }

    public void setFriend1(String Friend1){
        this.user1 = Friend1;
    }

    public int getFriend2(){
        return user2;
    }

    public void setFriend2(String Friend2){
        this.user2 = Friend2;
    }

}
