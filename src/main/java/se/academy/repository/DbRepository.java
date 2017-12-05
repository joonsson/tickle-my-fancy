package se.academy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.academy.Domain.Customer;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DbRepository {

    @Autowired
    private DataSource dataSource;

    public DbRepository(){

    }

    public void registerCustomer(Customer formCustomer){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO customer(email,password,firstName,lastName,address,zip,city,phone) VALUES (?,?,?,?,?,?,?,?);")){
            statement.setString(1,formCustomer.getEmail());
            statement.setString(2,formCustomer.getPassword());
            statement.setString(3,formCustomer.getFirstname());
            statement.setString(4,formCustomer.getLastname());
            statement.setString(5,formCustomer.getAddress());
            statement.setString(6,formCustomer.getZip());
            statement.setString(7,formCustomer.getCity());
            statement.setString(8,formCustomer.getPhone());

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.err.println("ERROR IN registerCustomer");
        }
    }

    public Customer loginCustomer(String email, String password){
        try(Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = ? and password =? ;")){
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;//TODO return a errorobject/interface thingie???
            } else {
                Customer freshLoginCustomer = new Customer();
            }
    }
    catch (SQLException e){
        System.err.println("ERROR IN loginCustomer");
    }
    return null;
    }

    public boolean checkIfCustomerExist(Customer customer){
        try(Connection conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = ?;")){
                statement.setString(1,customer.getEmail());
                ResultSet rs = statement.executeQuery();
                if(!rs.next()) {
                    return false;
                } else {
                    return true;
                }
        }
        catch (SQLException e) {
                System.err.print("Error");
        }
        return true;
    }






}
