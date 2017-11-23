package com.videorentalstore.repository;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.Customer;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class CustomerRepositoryTest extends H2DatabaseTest {

	private CustomerRepository customerRepository;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		customerRepository = getHandle().attach(CustomerRepository.class);
	}

	@Test
	public void testAddCustomerToDAO() throws Exception {
		Customer customer = new Customer(123, 0, 0);
		customerRepository.addNewCustomerWithId(customer);

		Optional<Customer> retrievedCustomer = customerRepository.getCustomerById(customer.getId());
		assertThat(retrievedCustomer, is(notNullValue()));
	}

	@Test
	public void testGetNonExistentCustomer() throws Exception {
		Optional<Customer> retrievedCustomer = customerRepository.getCustomerById(456);
		assertThat(retrievedCustomer, is(Optional.empty()));
	}

	@Test
	public void testUpdateCustomerToDAO() throws Exception {
		Customer customer = new Customer(123, 0, 0);
		customerRepository.addNewCustomerWithId(customer);

		customer.setBonusPoints(5);
		customer.setTotalOrders(66);

		customerRepository.updateCustomer(customer);

		Customer retrievedCustomer = customerRepository.getCustomerById(customer.getId()).get();
		assertEquals(retrievedCustomer, customer);
	}

}
