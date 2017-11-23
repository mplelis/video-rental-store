package com.videorentalstore.processors.orders;

import java.math.BigDecimal;

import java.util.List;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Video;
import com.videorentalstore.model.resources.VideoRentalOrder;
import com.videorentalstore.model.resources.VideoRentalOrderItem;
import com.videorentalstore.model.resources.VideoRentalReturn;
import com.videorentalstore.model.resources.VideoRentalReturnItem;
import com.videorentalstore.processors.OrderProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;

//This class could be used in the future to handle orders with discount differently.
public class DiscountOrderProcessor implements OrderProcessor {

	@Override
	public void processNewOrderRequest(VideoRentalOrder videoRentalOrder, Customer customer)
			throws VideoApplicationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void processReturnOrderRequest(VideoRentalReturn videoRentalReturn, List<Video> listOfVideos)
			throws VideoApplicationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Video> retrieveVideosByOrder(long orderId) throws VideoApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrderRepository(OrderRepository orderRepository) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomerRepository(CustomerRepository customerRepository) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVideoOrderCorrelationRepository(VideoOrderCorrelationRepository videoOrderCorrelationRepository) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVideoRepository(VideoRepository videoRepository) {
		// TODO Auto-generated method stub

	}

	@Override
	public void calculateBonusPoints(VideoRentalOrder videoRentalOrder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void calculateTotalPrice(VideoRentalOrder videoRentalOrder) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal calculateItemPrice(VideoRentalOrderItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculateSurcharges(VideoRentalReturnItem item) {
		// TODO Auto-generated method stub

	}

}
