package se.academy.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import se.academy.domain.Customer;
import se.academy.domain.Order;
import se.academy.domain.Product;
import se.academy.domain.SubOrder;
import se.academy.repository.DbRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerApplicationTests {
	@Autowired
	private DbRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getProductCategoryTest() {
		//TODO category test
	}

	@Test
	public void getProductSubcategoryTest() {
		//TODO subcategory test
	}

	@Test
	public void searchTest() {
		Queue<Product> products = repository.search("fransar");

		Assert.notNull(products, "Must get a productlist");
		Assert.notEmpty(products, "Productlist must not be empty");
	}

	@Test
	public void dbTests() {
        List<Product> products = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        Product product = repository.getProduct(2);
        Assert.notNull(product, "Product must not be null");
        Assert.isTrue(product.getProductID() == 2, "ProductID must equal input id");
        products.add(product);
        product = repository.getProduct(3);
        Assert.notNull(product, "Product must not be null");
        Assert.isTrue(product.getProductID() == 3, "ProductID must equal input id");
        products.add(product);
        product = repository.getProduct(6);
        Assert.notNull(product, "Product must not be null");
        Assert.isTrue(product.getProductID() == 6, "ProductID must equal input id");
        products.add(product);
        quantities.add(4);
        quantities.add(8);
        quantities.add(42);

        boolean success = repository.registerCustomer(new Customer("test@test.com", "test", " ", " ", " ", " ", " ", " "));
        Assert.isTrue(success, "Registration must be successful");

        Customer customer = repository.loginCustomer("test@test.com", "test");

        Assert.notNull(customer, "Customer must not be null");
        Assert.isTrue(customer.getEmail().equals("test@test.com"), "Customer email must equal input email");

	    int orderID = repository.addOrder(products, quantities, customer.getEmail());
	    Assert.isTrue(orderID != 0, "Must get a valid orderID");

	    Order order = repository.getOrder(orderID);
	    Assert.notNull(order, "Order must not be null");

	    List<SubOrder> subOrders = repository.getWholeOrder(orderID);
	    Assert.notNull(subOrders, "Suborders must not be null");
	    Assert.notEmpty(subOrders, "SubOrders msut not be empty");

	    success = repository.removeOrder(orderID);
	    Assert.isTrue(success, "Removal must be successful");
	    order = repository.getOrder(orderID);
	    Assert.isNull(order, "Should not find order");
        subOrders = repository.getWholeOrder(orderID);
        Assert.isTrue(subOrders.isEmpty(), "Should not find suborders");


        success = repository.removeCustomer("test@test.com");
        customer = repository.loginCustomer("test@test.com", "test");

        Assert.isTrue(success, "Removal must be successful");
        Assert.isTrue(customer == null, "Removed customer must not be found");
    }
}
