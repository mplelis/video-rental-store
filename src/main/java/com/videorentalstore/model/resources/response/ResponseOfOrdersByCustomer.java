package com.videorentalstore.model.resources.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.videorentalstore.model.Order;

public class ResponseOfOrdersByCustomer {
	private long customerId;
	private List<Order> orderList;

	public ResponseOfOrdersByCustomer(@JsonProperty("customerId") long customerId,
			@JsonProperty("orderList") List<Order> orderList) {
		this.customerId = customerId;
		this.orderList = orderList;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

}
