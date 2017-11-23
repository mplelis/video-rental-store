package com.videorentalstore.functionality;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.videorentalstore.model.Video;
import com.videorentalstore.model.VideoType;
import com.videorentalstore.model.resources.VideoRentalOrder;
import com.videorentalstore.model.resources.VideoRentalOrderItem;
import com.videorentalstore.model.resources.VideoRentalReturnItem;
import com.videorentalstore.processors.OrderProcessor;
import com.videorentalstore.processors.orders.RegularOrderProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;

public class VideoFunctionalityTest {

	private OrderProcessor orderProcessor;
	private VideoRentalOrder videoRentalOrder;

	@Before
	public void setUp() {
		OrderRepository orderRepository = mock(OrderRepository.class);
		CustomerRepository customerRepository = mock(CustomerRepository.class);
		VideoOrderCorrelationRepository videoOrderCorrelationRepository = mock(VideoOrderCorrelationRepository.class);
		VideoRepository videoRepository = mock(VideoRepository.class);
		orderProcessor = new RegularOrderProcessor(orderRepository, customerRepository,
				videoOrderCorrelationRepository, videoRepository);
		
		VideoRentalOrderItem videoRentalOrderItem1 = new VideoRentalOrderItem("Matrix 11", 2);
		VideoRentalOrderItem videoRentalOrderItem2 = new VideoRentalOrderItem("Spider Man", 8);
		VideoType videoType1 = new VideoType("New release", new BigDecimal(11), "EUR");
		Video video1 = new Video(1, "Matrix 11", "New release", videoType1);
		VideoType videoType2 = new VideoType("Regular film", new BigDecimal(7), "EUR");
		Video video2 = new Video(2, "Spider Man", "New release", videoType2);

		videoRentalOrderItem1.setVideo(video1);
		videoRentalOrderItem2.setVideo(video2);

		List<VideoRentalOrderItem> listOfVideos = new ArrayList<>();
		listOfVideos.add(videoRentalOrderItem1);
		listOfVideos.add(videoRentalOrderItem2);
		int customerId = 1;
		String currencyCode = "SEK";
		videoRentalOrder = new VideoRentalOrder(listOfVideos, customerId, currencyCode);
	}

	@Test
	public void testCalculationOfPricePerItem() throws Exception {
		VideoRentalOrderItem videoRentalOrderItem = new VideoRentalOrderItem("Matrix 11", 4);
		VideoType videoType = new VideoType("New release", new BigDecimal(11), "EUR");
		Video video = new Video(1, "Matrix 11", "New release", videoType);
		videoRentalOrderItem.setVideo(video);

		BigDecimal priceReturned = orderProcessor.calculateItemPrice(videoRentalOrderItem);
		BigDecimal priceExpected = new BigDecimal(44);

		assertEquals(priceExpected, priceReturned);
	}
	
	@Test
	public void testCalculationOfSurchargesPerReturnedItem() throws Exception {
		VideoRentalReturnItem videoRentalReturnItem = new VideoRentalReturnItem("Matrix 11", 4);
		VideoType videoType = new VideoType("New release", new BigDecimal(11), "EUR");
		Video video = new Video(1, "Matrix 11", "New release", videoType);
		videoRentalReturnItem.setVideo(video);

		orderProcessor.calculateSurcharges(videoRentalReturnItem);
		BigDecimal priceReturned = videoRentalReturnItem.getSurcharges();
		BigDecimal priceExpected = new BigDecimal(44);

		assertEquals(priceExpected, priceReturned);
	}

	@Test
	public void testCalculationOfBonusPointsPerOrder() throws Exception {
		orderProcessor.calculateBonusPoints(videoRentalOrder);

		int expectedBonusPoints = 3;
		int returnedBonusPoints = videoRentalOrder.getBonusPoints();
		
		assertEquals(expectedBonusPoints, returnedBonusPoints);
	}
	
	@Test
	public void testCalculationOfTotalPricePerOrder() throws Exception {
		orderProcessor.calculateTotalPrice(videoRentalOrder);

		BigDecimal expectedTotalPrice = new BigDecimal(64);
		BigDecimal returnedTotalPrice = videoRentalOrder.getTotalPrice();
		
		assertEquals(expectedTotalPrice, returnedTotalPrice);
	}

}