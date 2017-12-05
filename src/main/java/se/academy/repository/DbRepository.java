package se.academy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    public void registerCustomer(String email, String password) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO customer(email,password,firstName,lastName,address,zip,city,phone) VALUES (?,?,'0','0','0','0','0','0');")) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR IN registerCustomer");
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
                                rs.getInt("quantity"));
                return product;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("ERROR IN getProduct");
        }
        return null;
    }

    public Queue<Product> search(String searchString) {
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE name = (?)");
            statement.setString(1, searchString);
            statement.addBatch();
            statement = conn.prepareStatement("SELECT * FROM products WHERE name = %(?)%");
            statement.setString(1, searchString);
            statement.addBatch();
            statement = conn.prepareStatement("SELECT * FROM products WHERE category = (?)");
            statement.setString(1, searchString);
            statement.addBatch();
            statement = conn.prepareStatement("SELECT * FROM products WHERE category = %(?)%");
            statement.setString(1, searchString);
            statement.addBatch();

            ResultSet rs = statement.executeQuery();
            statement.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

            Queue<Product> products = new LinkedList<>();
            while (rs.next()) {
                Product product = new Product
                        (rs.getInt("productID"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getString("desciption"),
                                rs.getString("image"),
                                rs.getString("category"),
                                rs.getInt("quantity"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.err.println("ERROR IN getProduct");
        }
        return null;
    }
}
