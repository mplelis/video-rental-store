package com.videorentalstore.processors.customers;

import java.util.List;
import java.util.Optional;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Order;
import com.videorentalstore.processors.CustomerProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;

public class RegularCustomerProcessor implements CustomerProcessor {

	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;

	public RegularCustomerProcessor(OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
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
	public List<Order> getOrderListByCustomer(long customerId) throws VideoApplicationException {
		if (!doesCustomerExist(customerId)) {
			throw new VideoApplicationException("Customer with ID " + customerId + " does not exist.");
		}
		List<Order> listOfOrdersByCustomer = orderRepository.getOrdersByCustomerId(customerId);
		if (listOfOrdersByCustomer.size() == 0) {
			throw new VideoApplicationException("Customer with ID " + customerId + " has not placed any orders.");
		}
		return listOfOrdersByCustomer;
	}

	@Override
	public int getBonusPointsByCustomer(long customerId) throws VideoApplicationException {
		Customer customer = getCustomerById(customerId);
		return customer.getBonusPoints();
	}

	@Override
	public Customer getCustomerById(long customerId) throws VideoApplicationException {
		Optional<Customer> customerOptional = customerRepository.getCustomerById(customerId);
		if (!customerOptional.isPresent()) {
			throw new VideoApplicationException("Customer with ID " + customerId + " does not exist.");
		}
		Customer customer = customerOptional.get();
		return customer;
	}

	@Override
	public boolean doesCustomerExist(long customerId) throws VideoApplicationException {
		Optional<Customer> customerOptional = customerRepository.getCustomerById(customerId);
		if (!customerOptional.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public Customer addNewCustomerOrReturnExisting(long customerId) throws VideoApplicationException {
		Customer customer;
		try {
			Optional<Customer> customerOptional = customerRepository.getCustomerById(customerId);
			if (!customerOptional.isPresent()) {
				if (customerId > 1) {
					customer = new Customer(customerId, 0, 0);
					customerRepository.addNewCustomerWithId(customer);
				} else {
					customer = new Customer(0, 0);
					long newCustomerId = customerRepository.addNewCustomer(customer);
					customer.setId(newCustomerId);
				}
			} else {
				customer = customerOptional.get();
			}
		} catch (Exception e) {
			throw new VideoApplicationException("Error occured while trying to add a new customer.");
		}
		return customer;
	}

}
