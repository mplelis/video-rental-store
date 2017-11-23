package com.videorentalstore.processors;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Video;
import com.videorentalstore.processors.orders.RegularOrderProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.util.database.H2DatabaseTest;

public class OrderProcessorTest extends H2DatabaseTest {

	private CustomerRepository customerRepository;
	private OrderRepository orderRepository;
	private VideoOrderCorrelationRepository videoOrderCorrelationRepository;
	private VideoRepository videoRepository;
	private RegularOrderProcessor orderProcessor;

	@Before
	public void setUp() {
		createH2Database(getDataSource());
		customerRepository = getHandle().attach(CustomerRepository.class);
		orderRepository = getHandle().attach(OrderRepository.class);
		videoOrderCorrelationRepository = getHandle().attach(VideoOrderCorrelationRepository.class);
		videoRepository = getHandle().attach(VideoRepository.class);
		orderProcessor = new RegularOrderProcessor(orderRepository, customerRepository, videoOrderCorrelationRepository, videoRepository);
	}

	@Test
	public void testGetOrderListByExistingOrder() throws Exception {
		long orderId = 1;
		List<Video> videosListByOrder = orderProcessor.retrieveVideosByOrder(orderId);
		assertEquals(videosListByOrder.size()>0, true);
	}
	
	@Test (expected = VideoApplicationException.class)
	public void testGetOrderListByNotExistingOrderShouldThrowVideoApplicationException() throws Exception {
		long orderId = 1111;
		orderProcessor.retrieveVideosByOrder(orderId);
	}

}
