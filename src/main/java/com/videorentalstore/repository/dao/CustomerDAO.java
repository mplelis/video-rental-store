package com.videorentalstore.repository.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.videorentalstore.model.Customer;
import com.videorentalstore.objectmappers.CustomerMapper;

public interface CustomerDAO {
	
	@SqlQuery("SELECT * from customers where id=:id")
	@RegisterMapper(CustomerMapper.class)
	Customer getCustomerById(@Bind("id") long id);

	@SqlUpdate("INSERT INTO customers(bonus_points,total_orders) VALUES (:bonusPoints, 0)")
	@GetGeneratedKeys
	long addNewCustomer(@BindBean Customer customer);
	
	@SqlUpdate("INSERT INTO customers(id,bonus_points,total_orders) VALUES (:id, :bonusPoints, 0)")
	void addNewCustomerWithId(@BindBean Customer customer);
	
	@SqlUpdate("UPDATE customers set bonus_points=:bonusPoints, total_orders=:totalOrders where id=:id")
	void updateCustomer(@BindBean Customer customer);
}
