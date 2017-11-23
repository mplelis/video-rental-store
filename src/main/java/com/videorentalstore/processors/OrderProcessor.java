package com.videorentalstore.processors;

import java.math.BigDecimal;
import java.util.List;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Video;
import com.videorentalstore.model.resources.VideoRentalOrder;
import com.videorentalstore.model.resources.VideoRentalOrderItem;
import com.videorentalstore.model.resources.VideoRentalReturn;
import com.videorentalstore.model.resources.VideoRentalReturnItem;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;

public interface OrderProcessor {

	void processNewOrderRequest(VideoRentalOrder videoRentalOrder, Customer customer) throws VideoApplicationException;

	void processReturnOrderRequest(VideoRentalReturn videoRentalReturn, List<Video> listOfVideos)
			throws VideoApplicationException;

	List<Video> retrieveVideosByOrder(long orderId) throws VideoApplicationException;

	void setOrderRepository(OrderRepository orderRepository);

	void setCustomerRepository(CustomerRepository customerRepository);

	void setVideoOrderCorrelationRepository(VideoOrderCorrelationRepository videoOrderCorrelationRepository);

	void setVideoRepository(VideoRepository videoRepository);

	void calculateBonusPoints(VideoRentalOrder videoRentalOrder);

	void calculateTotalPrice(VideoRentalOrder videoRentalOrder);

	BigDecimal calculateItemPrice(VideoRentalOrderItem item);

	void calculateSurcharges(VideoRentalReturnItem item);

}
