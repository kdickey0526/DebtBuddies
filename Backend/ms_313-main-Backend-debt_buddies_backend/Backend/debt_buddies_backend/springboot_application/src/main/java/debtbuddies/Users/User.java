package debtbuddies.Users;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;

/**
 *
 * @author Vivek Bengre
 *
 */

@Entity
public class User {

     /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private boolean isOnline;
    private String email;
    private String password;
    private int Coins;
    private String icon;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;*/

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.Coins = 0;
        this.email = email;
        this.password = password;
        this.isOnline = true;
        this.icon = "icon0";
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public String convert_hex(String input) {

        try {
            // Create a SHA-256 message digest object
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Update the digest with the input string bytes
            byte[] hash = digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            System.out.println("SHA-256 Hash: " + hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return "";
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
    public int getCoins(){
        return Coins;
    }

    public void setCoins(int Coins){
        this.Coins = Coins;
    }

    public Boolean getIsOnline(){
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline){
        this.isOnline = isOnline;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = convert_hex(password);
    }

    /*public Laptop getLaptop(){
        return laptop;
    }

    public void setLaptop(Laptop laptop){
        this.laptop = laptop;
    }*/

}
