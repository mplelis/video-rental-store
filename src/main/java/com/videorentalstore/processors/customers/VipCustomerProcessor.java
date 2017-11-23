package com.videorentalstore.processors.customers;

import java.util.List;

import com.videorentalstore.exception.VideoApplicationException;
import com.videorentalstore.model.Customer;
import com.videorentalstore.model.Order;
import com.videorentalstore.processors.CustomerProcessor;
import com.videorentalstore.repository.CustomerRepository;
import com.videorentalstore.repository.OrderRepository;

// This class could be used in the future to handle VIP Customer orders differently.
public class VipCustomerProcessor implements CustomerProcessor {

	@Override
	public List<Order> getOrderListByCustomer(long customerId) throws VideoApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBonusPointsByCustomer(long customerId) throws VideoApplicationException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getCustomerById(long customerId) throws VideoApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doesCustomerExist(long customerId) throws VideoApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer addNewCustomerOrReturnExisting(long customerId) throws VideoApplicationException {
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

}
