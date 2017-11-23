package com.videorentalstore.repository;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.Order;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class OrderRepositoryTest extends H2DatabaseTest {

	private OrderRepository orderRepository;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		orderRepository = getHandle().attach(OrderRepository.class);
	}

	@Test
	public void testAddOrderToDAO() throws Exception {
		Order order = new Order(new BigDecimal(0), new BigDecimal(0), 1, "SEK");
		long orderId = orderRepository.addNewOrder(order);

		Optional<Order> retrievedOrder = orderRepository.getOrderById(orderId);
		assertThat(retrievedOrder, is(notNullValue()));
	}

	@Test
	public void testGetNotExistingOrder() throws Exception {
		Optional<Order> retrievedOrder = orderRepository.getOrderById(456);
		assertThat(retrievedOrder, is(Optional.empty()));
	}

	@Test
	public void testGetOrdersByCustomerId() throws Exception {
		// Test without existing Customer ID
		List<Order> orders = orderRepository.getOrdersByCustomerId(55);
		assertThat(orders.size(), is(0));

		// Test with existing Customer ID
		orders = orderRepository.getOrdersByCustomerId(1);
		assertEquals(orders.size() > 0, true);
	}

}
