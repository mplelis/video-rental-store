package com.videorentalstore.resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
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
import com.videorentalstore.model.Order;
import com.videorentalstore.model.resources.response.ResponseOfOrdersByCustomer;
import com.videorentalstore.processors.CustomerProcessor;
import com.videorentalstore.processors.customers.RegularCustomerProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;

@Path(CustomerResources.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResources {

    public static final String PATH = "/customer";
    public static final String GET_ORDERS_BY_CUSTOMER_ID = "/getOrdersByCustomer/{customerId}";
    public static final String GET_BONUS_POINTS_BY_CUSTOMER_ID = "/getBonusPointsByCustomer/{customerId}";
    public static final String ADD_NEW_CUSTOMER_OR_RETURN_EXISTING = "/addNewCustomerOrReturnExisting/{customerId}";
    
	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;
	private CustomerProcessor customerProcessor;
	
	public CustomerResources(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}

	@GET
	@Path(GET_ORDERS_BY_CUSTOMER_ID)
	@Timed
	public ResponseOfOrdersByCustomer getOrdersByCustomerId(@PathParam("customerId") @Min(value = 1) long customerId) 
			throws VideoApplicationException {
		customerProcessor = new RegularCustomerProcessor(orderRepository, customerRepository);
		List<Order> listOfOrdersByCustomer = customerProcessor.getOrderListByCustomer(customerId);
		return new ResponseOfOrdersByCustomer(customerId,listOfOrdersByCustomer);
	}

	@GET
	@Path(GET_BONUS_POINTS_BY_CUSTOMER_ID)
	@Timed
	public Response getBonusPointsByCustomerId(@PathParam("customerId") @Min(value = 1) long customerId)
			throws VideoApplicationException {
		customerProcessor = new RegularCustomerProcessor(orderRepository, customerRepository);
		long bonusPoints = customerProcessor.getBonusPointsByCustomer(customerId);
		Map<String, Long> responseEntity = new LinkedHashMap<>();
		responseEntity.put("customerId", customerId);
		responseEntity.put("bonusPoints", bonusPoints);
		return Response.status(Response.Status.OK).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path(ADD_NEW_CUSTOMER_OR_RETURN_EXISTING)
	@Timed
	public Customer addNewCustomerOrReturnExisting(@PathParam("customerId") @Min(value = 1) long customerId)
			throws VideoApplicationException {
		customerProcessor = new RegularCustomerProcessor(orderRepository, customerRepository);
		Customer customer = customerProcessor.addNewCustomerOrReturnExisting(customerId);
		return customer;
	}
}
