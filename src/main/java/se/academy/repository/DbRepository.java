package se.academy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.academy.domain.Customer;
import se.academy.domain.Order;
import se.academy.domain.Product;
import se.academy.domain.SubOrder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Component
public class DbRepository {

    @Autowired
    private DataSource dataSource;

    public DbRepository() {

    }

    public boolean registerCustomer(Customer customer) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO customer(email,password,firstName,lastName,address,zip,city,phone) VALUES (?,?,?,?,?,?,?,?);")) {
            statement.setString(1, customer.getEmail());
            statement.setString(2, customer.getPassword());
            statement.setString(3, customer.getFirstname());
            statement.setString(4, customer.getLastname());
            statement.setString(5, customer.getAddress());
            statement.setString(6, customer.getZip());
            statement.setString(7, customer.getCity());
            statement.setString(8, customer.getPhone());
            int result = statement.executeUpdate();

            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("ERROR IN registerCustomer");
        }
        return false;
    }

    public Customer loginCustomer(String email, String password) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = ? and password = ? ;")) {
            statement.setString(1, email);
            statement.setString(2, password);
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

    public boolean checkIfCustomerExist(Customer customer) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = ?;")) {
            statement.setString(1, customer.getEmail());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Error");
        }
        return true;
    }

    public Queue<Product> search(String searchString) {
        try {
            Connection conn = dataSource.getConnection();
            Queue<Product> products = new LinkedList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE [name] = (?)");
            statement.setString(1, searchString);
            searchHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE [name] = (?)");
            statement.setString(1, "%" + searchString + "%");
            searchHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE [subcategory] = (?)");
            statement.setString(1, searchString);
            searchHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE [category] = (?)");
            statement.setString(1, searchString);
            searchHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE [subcategory] = (?)");
            statement.setString(1, "%" + searchString + "%");
            searchHelper(products, statement.executeQuery());
            statement = conn.prepareStatement("SELECT * FROM products WHERE [category] = (?)");
            statement.setString(1, "%" + searchString + "%");
            searchHelper(products, statement.executeQuery());

            return products;
        } catch (SQLException e) {
            System.err.println("ERROR IN search");
            e.printStackTrace();
        }
        return null;
    }


    public Queue<Product> getBySubCategory(String category) {
        Queue<Product> products = getHelper("SELECT * FROM products WHERE subcategory = (?)", category);
        return products;
    }

    public Queue<Product> getHelper(String sqlStatement, String category) {


        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sqlStatement)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            Queue<Product> products = new LinkedList<>();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("productID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getString("image"),
                        resultSet.getString("category"),
                        resultSet.getString("subcategory"),
                        resultSet.getInt("quantity")
                );
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeCustomer(String email) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM customer WHERE email = (?)")) {
            statement.setString(1, email);
            int rs = statement.executeUpdate();
            if (rs != 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("ERROR IN getProduct");
            e.printStackTrace();
        }
        return false;
    }

    public Queue<Product> getBySubCategoryTop3(String category) {

        Queue<Product> products = getHelper("SELECT TOP (3) products.[productID],[name],[price],[quantity],[subcategory],[category],[dbo].[imagetest].[image], [description] FROM products INNER JOIN imagetest ON [dbo].[products].[productID] = [dbo].[imagetest].[productID] WHERE subcategory = (?)", category);
        return products;

    }

    public Queue<Product> getByCategory(String category) {
        Queue<Product> products = getHelper("SELECT * FROM products WHERE category = (?)", category);
        return products;
    }

    public void searchHelper(Queue<Product> products, ResultSet rs) throws SQLException {
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
    }

    public Product getProduct(int productID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE productID = (?)")) {
            statement.setInt(1, productID);
            ResultSet rs = statement.executeQuery();
            Product product;
            while (rs.next()) {
                product = new Product(
                        rs.getInt("productID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("subcategory"),
                        rs.getInt("quantity")
                );
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addOrder(List<Product> products, List<Integer> quantities, String email) {
        try (Connection conn = dataSource.getConnection()) {
            if (!(products.size() == quantities.size())) {
                return 0;
            }
            int cost = 0;
            int totalQuantity = 0;
            for (int quantity : quantities) {
                totalQuantity += quantity;
            }
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                int quantity = quantities.get(i);
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM products WHERE productID = (?)");
                statement.setInt(1, product.getProductID());
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    cost += product.getPrice() * quantity;
                }
            }
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE email = (?)");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            int customerID = 0;
            if (rs.next()) {
                customerID = rs.getInt("customerID");
            }

            statement = conn.prepareStatement("INSERT INTO orders(customerID, cost, quantity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, customerID);
            statement.setInt(2, cost);
            statement.setInt(3, totalQuantity);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }
            rs = statement.getGeneratedKeys();
            int orderID = 0;
            if (rs.next()) {
                orderID = rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
            conn.setAutoCommit(false);
            int[] res;
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                int quantity = quantities.get(i);

                statement = conn.prepareStatement("INSERT INTO suborders(orderID, productID, price, quantity, cost) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, orderID);
                statement.setInt(2, product.getProductID());
                statement.setDouble(3, product.getPrice());
                statement.setInt(4, quantity);
                statement.setDouble(5, (quantity * product.getPrice()));
                statement.addBatch();
            }
            res = statement.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            /*result = 0;
            for (int r : res) {
                result += r;
            }
            if (!(result == products.size())) {
                throw new SQLException("Creating suborders failed, rows affected does not match nr of products");
            }*/
            return orderID;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<SubOrder> getWholeOrder(int orderID) {
        List<SubOrder> subOrders = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM suborders WHERE [orderID] = (?)");
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subOrders.add(new SubOrder(
                        resultSet.getInt("suborderID"),
                        resultSet.getInt("orderID"),
                        resultSet.getInt("productID"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("cost")));
            }
            return subOrders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrder(int orderID) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM orders WHERE [orderID] = (?)");
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = new Order(
                        resultSet.getInt("orderID"),
                        resultSet.getInt("customerID"),
                        resultSet.getDouble("cost"),
                        resultSet.getInt("quantity"));
            }
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeOrder(int orderID) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM suborders WHERE [orderID] = (?)");
            statement.setInt(1, orderID);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Deleting suborders failed, no rows affected");
            }
            statement = conn.prepareStatement("DELETE FROM orders WHERE [orderID] = (?)");
            statement.setInt(1, orderID);
            result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Deleting orders failed, no rows affected");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
