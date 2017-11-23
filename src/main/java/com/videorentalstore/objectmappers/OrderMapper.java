package com.videorentalstore.objectmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.videorentalstore.model.Order;

public class OrderMapper implements ResultSetMapper<Order> {

	public Order map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Order(r.getLong("id"), r.getBigDecimal("price"), r.getBigDecimal("surcharges"),
				 r.getLong("customer_id"), r.getString("currency_code"), r.getBoolean("is_order_returned"));
	}
}