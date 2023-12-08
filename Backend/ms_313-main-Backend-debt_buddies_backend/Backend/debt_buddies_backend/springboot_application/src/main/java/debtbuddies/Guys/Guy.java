package debtbuddies.Guys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Vivek Bengre
 *
 */

@Entity
public class Guy {

     /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String guyName;
    private String guysFriend;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(guyu)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a guyu object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the guyu table will have a field called laptop_id
     */

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;*/

    public Guy(String guyuName, String email) {
        this.guyName = guyuName;
        this.guysFriend = email;
    }

    public Guy() {
    }

    // =============================== Getters and Setters for each field ================================== //


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getguyName(){
        return guyName;
    }

    public void setguyName(String guyuName){
        this.guyName = guyuName;
    }

    public String getGuysFriend(){
        return guysFriend;
    }
    public void setEmail(String guysFriend){
        this.guysFriend = guysFriend;
    }

    /*public Laptop getLaptop(){
        return laptop;
    }

    public void setLaptop(Laptop laptop){
        this.laptop = laptop;
    }*/

}
