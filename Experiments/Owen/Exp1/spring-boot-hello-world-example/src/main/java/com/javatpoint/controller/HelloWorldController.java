package com.javatpoint.controller;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
 
public class MySqlConnection {
    public static void main(String arg[])
    {
        Connection connection = null;
        try {
//            Class.forName("com.gjt.mm.mysql.Driver"); //Debugging
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/CasinoDatabase","Casino", "MS313"); 
            Statement st = conn.createStatement();

      st.executeUpdate("INSERT INTO Users (Username, Password) "
          +"VALUES ('Owen', 'MS313'");


            connection.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
