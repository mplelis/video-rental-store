package com.videorentalstore.resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Video;
import com.videorentalstore.model.resources.VideoRentalOrder;
import com.videorentalstore.model.resources.VideoRentalReturn;
import com.videorentalstore.model.resources.response.ResponseOfOrderVideos;
import com.videorentalstore.processors.CustomerProcessor;
import com.videorentalstore.processors.OrderProcessor;
import com.videorentalstore.processors.customers.RegularCustomerProcessor;
import com.videorentalstore.processors.orders.RegularOrderProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;
import com.videorentalstore.repository.VideoOrderCorrelationRepository;
import com.videorentalstore.repository.VideoRepository;

@SuppressWarnings("unused")
@Path(OrderResources.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResources {

	public static final String PATH = "/";
	public static final String PLACE_ORDER = "/placeOrder";
	public static final String RETURN_ORDER = "/returnOrder";
	public static final String GET_VIDEOS_BY_ORDER = "/getVideosByOrder/{orderId}";

	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;
	private VideoOrderCorrelationRepository videoOrderCorrelationRepository;
	private VideoRepository videoRepository;
	private CustomerProcessor customerProcessor;
	private OrderProcessor orderProcessor;

	public OrderResources(OrderRepository orderRepository, CustomerRepository customerRepository,
			VideoOrderCorrelationRepository videoOrderCorrelationRepository, VideoRepository videoRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.videoOrderCorrelationRepository = videoOrderCorrelationRepository;
		this.videoRepository = videoRepository;
	}

	@POST
	@Path(PLACE_ORDER)
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	public Response placeOrder(VideoRentalOrder videoRentalOrder) throws VideoApplicationException {
		orderProcessor = new RegularOrderProcessor(orderRepository, customerRepository,
				videoOrderCorrelationRepository, videoRepository);
		customerProcessor = new RegularCustomerProcessor(orderRepository, customerRepository);
		Customer customer = customerProcessor.addNewCustomerOrReturnExisting(videoRentalOrder.getCustomerId());
		orderProcessor.processNewOrderRequest(videoRentalOrder, customer);

		String totalAmount = videoRentalOrder.getTotalPrice().stripTrailingZeros().toPlainString();
		Map<String, String> responseEntity = new LinkedHashMap<>();
		responseEntity.put("totalPrice", totalAmount);
		responseEntity.put("currency", videoRentalOrder.getCurrencyCode());
		return Response.status(Response.Status.OK).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path(RETURN_ORDER)
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	public Response returnOrder(VideoRentalReturn videoRentalReturn) throws VideoApplicationException {
		orderProcessor = new RegularOrderProcessor(orderRepository, customerRepository,
				videoOrderCorrelationRepository, videoRepository);
		List<Video> listOfVideos = orderProcessor.retrieveVideosByOrder(videoRentalReturn.getOrderId());
		orderProcessor.processReturnOrderRequest(videoRentalReturn, listOfVideos);

		String totalSurcharges = videoRentalReturn.getTotalSurcharges().stripTrailingZeros().toPlainString();
		Map<String, String> responseEntity = new LinkedHashMap<>();
		responseEntity.put("totalSurcharges", totalSurcharges);
		responseEntity.put("currency", videoRentalReturn.getCurrencyCode());
		return Response.status(Response.Status.OK).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path(GET_VIDEOS_BY_ORDER)
	@Timed
	public ResponseOfOrderVideos getVideosByOrder(@PathParam("orderId") @Min(value = 1) long orderId)
			throws VideoApplicationException {
		orderProcessor = new RegularOrderProcessor(orderRepository, customerRepository,
				videoOrderCorrelationRepository, videoRepository);
		List<Video> listOfVideos = orderProcessor.retrieveVideosByOrder(orderId);
		return new ResponseOfOrderVideos(orderId, listOfVideos);
	}
}
