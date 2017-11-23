package com.videorentalstore.objectmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.videorentalstore.model.Customer;

public class CustomerMapper implements ResultSetMapper<Customer> {

	public Customer map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		return new Customer(r.getLong("id"), r.getInt("total_orders"), r.getInt("bonus_points"));
	}
}