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

    public void registerCustomer(String email, String password){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO customer(email,password,firstName,lastName,address,zip,city,phone) VALUES (?,?,'0','0','0','0','0','0');")){
            statement.setString(1,email);
            statement.setString(2,password);
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("ERROR IN registerCustomer");
        }
    }






}
