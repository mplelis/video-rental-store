package com.videorentalstore.processors.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Order;
import com.videorentalstore.model.Video;
import com.videorentalstore.model.VideoOrderCorrelation;
import com.videorentalstore.model.VideoType;
import com.videorentalstore.model.resources.VideoRentalOrder;
import com.videorentalstore.model.resources.VideoRentalOrderItem;
import com.videorentalstore.model.resources.VideoRentalReturn;
import com.videorentalstore.model.resources.VideoRentalReturnItem;
import com.videorentalstore.processors.OrderProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;
import com.videorentalstore.util.VideoLogging;

public class RegularOrderProcessor implements OrderProcessor {

	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;
	private VideoOrderCorrelationRepository videoOrderCorrelationRepository;
	private VideoRepository videoRepository;

	public RegularOrderProcessor(OrderRepository orderRepository, CustomerRepository customerRepository,
			VideoOrderCorrelationRepository videoOrderCorrelationRepository, VideoRepository videoRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.videoOrderCorrelationRepository = videoOrderCorrelationRepository;
		this.videoRepository = videoRepository;
	}

	@Override
	public void processNewOrderRequest(VideoRentalOrder videoRentalOrder, Customer customer)
			throws VideoApplicationException {
		if (videoRentalOrder.getListOfVideos().size()==0) {
			throw new VideoApplicationException("Empty order is not allowed. Please add videos.");
		}
		try {
			String currencyCode = videoRentalOrder.getCurrencyCode();
			for (VideoRentalOrderItem item : videoRentalOrder.getListOfVideos()) {
				Video video = videoRepository.getVideoByTitle(item.getVideoTitle()).get();
				video.setVideoType(VideoType.videoTypes.get(video.getVideoTypeStr() + " - " + currencyCode));
				item.setVideo(video);
			}
			calculateBonusPoints(videoRentalOrder);
			calculateTotalPrice(videoRentalOrder);

			customer.setBonusPoints(customer.getBonusPoints() + videoRentalOrder.getBonusPoints());
			customer.setTotalOrders(customer.getTotalOrders() + 1);
			customerRepository.updateCustomer(customer);

			Order order = new Order(videoRentalOrder.getTotalPrice(), new BigDecimal(0), customer.getId(),
					currencyCode, false);
			long orderId = orderRepository.addNewOrder(order);

			List<VideoOrderCorrelation> videoOrderCorrelationList = new ArrayList<>();
			for (VideoRentalOrderItem item : videoRentalOrder.getListOfVideos()) {
				VideoOrderCorrelation videoOrderCorrelationEntry = new VideoOrderCorrelation(orderId,
						item.getVideo().getId(), item.getDaysToRent());
				videoOrderCorrelationList.add(videoOrderCorrelationEntry);
			}
			videoOrderCorrelationRepository.addNewVideoOrderCorrelationBatch(videoOrderCorrelationList);
		} catch (Exception e) {
			VideoLogging.logError(e.toString());
			throw new VideoApplicationException(e.getMessage());
		}
	}
	
	@Override
	public void calculateBonusPoints(VideoRentalOrder videoRentalOrder) {
		int bonusPoints = 0;
		for (VideoRentalOrderItem item : videoRentalOrder.getListOfVideos()) {
			String videoType = item.getVideo().getVideoType().getType();
			if (videoType.equals("New release")) {
				bonusPoints += 2;
			}
			if (videoType.equals("Regular film") || videoType.equals("Old film")) {
				bonusPoints++;
			}
		}
		videoRentalOrder.setBonusPoints(bonusPoints);
	}
	
	@Override
	public void calculateTotalPrice(VideoRentalOrder videoRentalOrder) {
		for (VideoRentalOrderItem item : videoRentalOrder.getListOfVideos()) {
			BigDecimal itemPrice = calculateItemPrice(item);
			videoRentalOrder.setTotalPrice(videoRentalOrder.getTotalPrice().add(itemPrice));
		}
	}

	@Override
	public BigDecimal calculateItemPrice(VideoRentalOrderItem item) {
		BigDecimal itemPrice = new BigDecimal(0);
		String videoType = item.getVideo().getVideoType().getType();
		BigDecimal videoPrice = item.getVideo().getVideoType().getPrice();
		
		if (videoType.equals("New release")) {
			itemPrice = videoPrice.multiply(new BigDecimal(item.getDaysToRent()));
		} 
		if (videoType.equals("Regular film")) { 
			itemPrice = item.getDaysToRent() <= 3 ? videoPrice : videoPrice.add(videoPrice.multiply(new BigDecimal(item.getDaysToRent()-3)));
		}
		if (videoType.equals("Old film")) {
			itemPrice = item.getDaysToRent() <= 5 ? videoPrice : videoPrice.add(videoPrice.multiply(new BigDecimal(item.getDaysToRent()-5)));
		}
		return itemPrice;
	}

	@Override
	public void processReturnOrderRequest(VideoRentalReturn videoRentalReturn, List<Video> listOfVideos) 
				throws VideoApplicationException {
		try {
			String currencyCode = videoRentalReturn.getCurrencyCode();
			for (VideoRentalReturnItem item : videoRentalReturn.getListOfVideos()) {
				Video video = videoRepository.getVideoByTitle(item.getTitle()).get();
				if (listOfVideos.contains(video)) {
					video.setVideoType(VideoType.videoTypes.get(video.getVideoTypeStr() + " - " + currencyCode));
					item.setVideo(video);
					calculateSurcharges(item);
					videoRentalReturn.setTotalSurcharges(videoRentalReturn.getTotalSurcharges().add(item.getSurcharges()));
				} else {
					throw new VideoApplicationException("Order with ID " + videoRentalReturn.getOrderId() + 
							" does not contain the video *" + video.getTitle() + "*. Please return the correct order!");
				}
			}
			long orderId = videoRentalReturn.getOrderId();
			Optional<Order> orderOptional = orderRepository.getOrderById(orderId);
			Order order;
			if (!orderOptional.isPresent()) {
				throw new VideoApplicationException(
						"Order with ID " + orderId + " was not found and it cannot be returned.");
			} else {
				order = orderOptional.get();
				if (order.isOrderReturned() == true) {
					throw new VideoApplicationException("Order with ID " + orderId + " has already been returned.");
				}
				order.setSurcharges(videoRentalReturn.getTotalSurcharges());
				order.setOrderReturned(true);
				orderRepository.updateOrder(order);
			}
		} catch (Exception e) {
			VideoLogging.logError(e.toString());
			throw new VideoApplicationException(e.getMessage());
		}
	}
	
	@Override
	public void calculateSurcharges(VideoRentalReturnItem item) {
		item.setSurcharges(new BigDecimal(item.getDaysDelayed()).multiply(item.getVideo().getVideoType().getPrice()));
	}

	@Override
	public List<Video> retrieveVideosByOrder(long orderId) throws VideoApplicationException {
		List<VideoOrderCorrelation> videosPerOrder = videoOrderCorrelationRepository
				.getVideoOrderCorrelationsByOrderId(orderId);
		if (videosPerOrder.size() == 0) {
			throw new VideoApplicationException("Order ID " + orderId + " does not exist.");
		}
		List<Long> listOfVideoIds = new ArrayList<>();
		for (VideoOrderCorrelation videoOrderCorrelation : videosPerOrder) {
			listOfVideoIds.add(videoOrderCorrelation.getVideoId());
		}
		List<Video> listOfVideos = new ArrayList<>();
		for (long videoId : listOfVideoIds) {
			Optional<Video> videoOptional = videoRepository.getVideoById(videoId);
			if (videoOptional.isPresent()) {
				listOfVideos.add(videoRepository.getVideoById(videoId).get());
			} else {
				throw new VideoApplicationException("Error while retrieving video with ID " + videoId);
			}
		}
		return listOfVideos;
	}

	@Override
	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void setVideoOrderCorrelationRepository(VideoOrderCorrelationRepository videoOrderCorrelationRepository) {
		this.videoOrderCorrelationRepository = videoOrderCorrelationRepository;
	}

	@Override
	public void setVideoRepository(VideoRepository videoRepository) {
		this.videoRepository = videoRepository;
	}

}
