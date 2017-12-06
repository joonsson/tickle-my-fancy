package se.academy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.academy.domain.Customer;
import se.academy.domain.Product;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class DbRepository {

    @Autowired
    private DataSource dataSource;

    public DbRepository() {

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
        }
    }

    public Product getProduct(int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE productID = (?)")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Product product = new Product
                        (rs.getInt("productID"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getString("desciption"),
                                rs.getString("image"),
                                rs.getString("category"),
                                rs.getString("subcategory"),
                                rs.getInt("quantity"));
                return product;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("ERROR IN getProduct");
            e.printStackTrace();
        }
        return null;
    }

      public Customer loginCustomer(String email, String password){
        try(Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = ? and password = ? ;")){
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;//TODO return a errorobject/interface thingie???
            } else {
                Customer freshLoginCustomer = new Customer(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("address"),
                        rs.getString("zip"),
                        rs.getString("city"),
                        rs.getString("phone")
                );
                return freshLoginCustomer;
            }
        } catch (SQLException e) {
            System.err.println("ERROR IN loginCustomer");
            e.printStackTrace();
        }
        return null;
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

    public Queue<Product> search(String searchString) {
        try {
            Connection conn = dataSource.getConnection();
            Queue<Product> products = new LinkedList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE name = (?)");
            statement.setString(1, searchString);
            queueHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE name = (?)");
            statement.setString(1, "%" + searchString + "%");
            queueHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE subcategory = (?)");
            statement.setString(1, searchString);
            queueHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE category = (?)");
            statement.setString(1, searchString);
            queueHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE subcategory = (?)");
            statement.setString(1, "%" + searchString + "%");
            queueHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE category = (?)");
            statement.setString(1, "%" + searchString + "%");
            queueHelper(products, statement.executeQuery());

            return products;
        } catch (SQLException e) {
            System.err.println("ERROR IN search");
            e.printStackTrace();
        }
        return null;
    }

    public Queue<Product> queueHelper ( Queue<Product> products, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Product product = new Product
                    (rs.getInt("productID"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("description"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("subcategory"),
                            rs.getInt("quantity"));
            products.add(product);
        }
        return  products;
    }
}
