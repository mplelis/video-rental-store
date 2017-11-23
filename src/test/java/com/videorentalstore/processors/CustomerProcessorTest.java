package com.videorentalstore.processors;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Order;
import com.videorentalstore.processors.customers.RegularCustomerProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class CustomerProcessorTest extends H2DatabaseTest {

	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	private CustomerProcessor customer;
	
	@Before
	public void setUp() {
		createH2Database(getDataSource());
		customerRepository = getHandle().attach(CustomerRepository.class);
		orderRepository = getHandle().attach(OrderRepository.class);
		customer = new RegularCustomerProcessor(orderRepository, customerRepository);
	}

	@Test
	public void testGetOrderListByExistingCustomer() throws Exception {
		long customerId = 1;
		List<Order> orderList = customer.getOrderListByCustomer(customerId);
		assertEquals(orderList.size()>0, true);
	}
	
	@Test (expected = VideoApplicationException.class)
	public void testGetOrderListByNotExistingCustomerShouldThrowVideoApplicationException() throws Exception {
		long customerId = 1111;
		customer.getOrderListByCustomer(customerId);
	}

}
