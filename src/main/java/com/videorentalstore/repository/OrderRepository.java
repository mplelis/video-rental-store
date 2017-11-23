package com.videorentalstore.repository;

import java.util.List;
import java.util.Optional;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import com.videorentalstore.model.Order;
import com.videorentalstore.repository.dao.OrderDAO;

public abstract class OrderRepository {
	
    @CreateSqlObject
    abstract OrderDAO orderDAO();

    @Transaction
    public Optional<Order> getOrderById(long orderId) {
        Order order = orderDAO().getOrderById(orderId);
        return Optional.ofNullable(order);
    }
    
    @Transaction
    public List<Order> getOrdersByCustomerId(long customerId) {
    	List<Order> orders = orderDAO().getOrdersByCustomerId(customerId);
    	return orders;
    }
    
    @Transaction
    public long addNewOrder(Order order) {
        return orderDAO().addNewOrder(order);
    }
    
    @Transaction
    public void updateOrder(Order order) {
        orderDAO().updateOrder(order.getSurcharges(), order.isOrderReturned(), order.getId());
    }
    
}
