package com.videorentalstore.repository;

import java.util.Optional;

import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

import com.videorentalstore.model.Customer;
import com.videorentalstore.repository.dao.CustomerDAO;

public abstract class CustomerRepository {
	
    @CreateSqlObject
    abstract CustomerDAO customerDAO();

    @Transaction
    public Optional<Customer> getCustomerById(long id) {
        Customer customer = customerDAO().getCustomerById(id);
        return Optional.ofNullable(customer);
    }
    
    @Transaction
    public long addNewCustomer(Customer customer) {
        return customerDAO().addNewCustomer(customer);
    }
    
    @Transaction
    public void addNewCustomerWithId(Customer customer) {
        customerDAO().addNewCustomerWithId(customer);
    }
    
    @Transaction
    public void updateCustomer(Customer customer) {
        customerDAO().updateCustomer(customer);
    }
}
