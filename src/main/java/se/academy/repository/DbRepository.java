package se.academy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DbRepository {

    @Autowired
    private DataSource dataSource;

    public DbRepository(){

    }

    public void registerCustomer(String email, String password, String firstName, String lastName, String address, String zip, String city, String phone){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO customer(email,password,firstName,lastName,address,zip,city,phone) VALUES (?,?,?,?,?,?,?,?);")){
            statement.setString(1,email);
            statement.setString(2,password);
            statement.setString(3,firstName);
            statement.setString(4,lastName);
            statement.setString(5,address);
            statement.setString(6,zip);
            statement.setString(7,city);
            statement.setString(8,phone);

            statement.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("ERROR IN registerCustomer");
        }
    }






}
