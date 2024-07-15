package com.jwtSecuritywithcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtSecuritywithcrud.dto.CustomerDto;
import com.jwtSecuritywithcrud.model.Customer;
import com.jwtSecuritywithcrud.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private CustomerService  customerService;
	
	@GetMapping("/normal")
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("hi user");
	}
	
	
	@PostMapping("/customer/create")
    public Customer createCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

	 @PutMapping("/customer/update/{id}")
	    public Customer updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
	        return customerService.updateCustomer(id, customerDto);
	    }
	
	 
	 @GetMapping("/getAllCustomer")
	    public Page<Customer> getAllCustomers(
	            @PageableDefault(page = 0, size = 10) Pageable pageable) {
	        return customerService.getAllCustomers(pageable);
	    }
	 
	 @GetMapping("/getCustomerById/{id}")
	    public Customer getCustomerById(@PathVariable Long id) {
	        return customerService.getCustomerById(id);
	    }
	 
	 @DeleteMapping("/deleteCustomerById/{id}")
	    public void deleteCustomer(@PathVariable Long id) {
	        customerService.deleteCustomer(id);
	    }
	
}
