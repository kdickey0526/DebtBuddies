package debtbuddies.Friends;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Vivek Bengre
 *
 */

@Entity
public class Friend {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String personName;
    private String friendswithname;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(Friend)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a Friend object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the Friend table will have a field called laptop_id
     */

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;*/

    public Friend(String PersonName, String friendswithname) {
        this.personName = PersonName;
        this.friendswithname = friendswithname;
    }

    public Friend() {
    }

    // =============================== Getters and Setters for each field ================================== //


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPersonName(){
        return personName;
    }

    public void setPersonName(String PersonName){
        this.personName = PersonName;
    }

    public String getfriendswithname(){
        return friendswithname;
    }
    public void setfriendswithname(String friendswithname){
        this.friendswithname = friendswithname;
    }


}
