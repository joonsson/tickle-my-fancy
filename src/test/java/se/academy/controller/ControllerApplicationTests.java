package se.academy.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import se.academy.domain.Customer;
import se.academy.domain.Product;
import se.academy.repository.DbRepository;

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
	public void getProductTest() {
		Product product = repository.getProduct(2);
		Assert.notNull(product, "Product must not be null");
		Assert.isTrue(product.getProductID() == 2, "ProductID must equal input id");
		product = repository.getProduct(3);
		Assert.notNull(product, "Product must not be null");
		Assert.isTrue(product.getProductID() == 3, "ProductID must equal input id");
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
	public void customerTests() {
		boolean success = repository.registerCustomer(new Customer("test@test.com", "test", " ", " ", " ", " ", " ", " "));
		Assert.isTrue(success, "Registration must be successful");

		Customer customer = repository.loginCustomer("test@test.com", "test");

		Assert.notNull(customer, "Customer must not be null");
		Assert.isTrue(customer.getEmail().equals("test@test.com"), "Customer email must equal input email");

		success = repository.removeCustomer("test@test.com");
		customer = repository.loginCustomer("test@test.com", "test");

		Assert.isTrue(success, "Removal must be successful");
		Assert.isTrue(customer == null, "Removed customer must not be found");
	}

}
