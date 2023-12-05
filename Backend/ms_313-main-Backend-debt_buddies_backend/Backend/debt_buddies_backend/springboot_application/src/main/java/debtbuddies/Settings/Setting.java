package debtbuddies.Settings;

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
public class Setting {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean sound;
/*    private int cpuCores;
    private int ram;
    private String manufacturer;
    private int cost;*/

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToOne
    @JsonIgnore
    private Person person;

    public Setting(boolean sound) {
        this.sound = sound;
    }

    public Setting() {
                this.sound = true;
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean getSound(){
        return sound;
    }

    public void setCpuClock(boolean sound){
        this.sound = sound;
    }

    public Person getPerson(){
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }


}
