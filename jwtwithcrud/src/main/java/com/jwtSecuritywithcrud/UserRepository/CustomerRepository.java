package com.jwtSecuritywithcrud.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwtSecuritywithcrud.model.Customer;


@Repository
public interface CustomerRepository  extends  JpaRepository<Customer, Long>{

}
