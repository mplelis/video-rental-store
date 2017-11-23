package com.videorentalstore.repository.dao;

import java.math.BigDecimal;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.videorentalstore.model.Order;
import com.videorentalstore.objectmappers.OrderMapper;

public interface OrderDAO {
	
	@SqlQuery("SELECT * from orders where id=:id")
	@RegisterMapper(OrderMapper.class)
	Order getOrderById(@Bind("id") long orderId);
	
	@SqlQuery("SELECT * from orders where customer_id=:customer_id")
	@RegisterMapper(OrderMapper.class)
	List<Order> getOrdersByCustomerId(@Bind("customer_id") long customerId);

	@SqlUpdate("INSERT INTO orders(price,surcharges,customer_id,currency_code) VALUES (:price, 0, :customerId, :currencyCode)")
	@GetGeneratedKeys
	long addNewOrder(@BindBean Order order);
	
	@SqlUpdate("UPDATE orders set surcharges=:surcharges, is_order_returned=:isOrderReturned where id=:id")
	void updateOrder(@Bind("surcharges") BigDecimal surcharges,@Bind("isOrderReturned") boolean isOrderReturned,
			@Bind("id") long id);
}
