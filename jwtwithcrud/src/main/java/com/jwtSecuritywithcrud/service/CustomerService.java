package com.jwtSecuritywithcrud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jwtSecuritywithcrud.dto.CustomerDto;
import com.jwtSecuritywithcrud.model.Customer;

public interface CustomerService {
	
	Customer createCustomer(CustomerDto customerDto);

    Customer updateCustomer(Long id, CustomerDto customerDto);

    Page<Customer> getAllCustomers(Pageable pageable);

    Customer getCustomerById(Long id);

    void deleteCustomer(Long id);

}
