package com.videorentalstore.processors;

import java.util.List;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Order;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;

public interface CustomerProcessor {

	List<Order> getOrderListByCustomer(long customerId) throws VideoApplicationException;

	int getBonusPointsByCustomer(long customerId) throws VideoApplicationException;

	Customer getCustomerById(long customerId) throws VideoApplicationException;

	boolean doesCustomerExist(long customerId) throws VideoApplicationException;

	Customer addNewCustomerOrReturnExisting(long customerId) throws VideoApplicationException;

	void setOrderRepository(OrderRepository orderRepository);

	void setCustomerRepository(CustomerRepository customerRepository);

}
