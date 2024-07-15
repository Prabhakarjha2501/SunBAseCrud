package com.jwtSecuritywithcrud.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jwtSecuritywithcrud.UserRepository.CustomerRepository;
import com.jwtSecuritywithcrud.dto.CustomerDto;
import com.jwtSecuritywithcrud.model.Customer;
import com.jwtSecuritywithcrud.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
    private CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(CustomerDto customerDto) {
		 Customer customer = new Customer();
		mapDtoToEntity(customerDto, customer);
	        return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Long id, CustomerDto customerDto) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            mapDtoToEntity(customerDto, customer);
            return customerRepository.save(customer);
        }
        return null;
	}

	@Override
	public Page<Customer> getAllCustomers(Pageable pageable) {
		 return customerRepository.findAll(pageable);
	}

	@Override
	public Customer getCustomerById(Long id) {
		 return customerRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteCustomer(Long id) {
		 customerRepository.deleteById(id);
	}
	
	 private void mapDtoToEntity(CustomerDto customerDto, Customer customer) {
	        customer.setFirstName(customerDto.getFirstName());
	        customer.setLastName(customerDto.getLastName());
	        customer.setStreet(customerDto.getStreet());
	        customer.setAddress(customerDto.getAddress());
	        customer.setCity(customerDto.getCity());
	        customer.setState(customerDto.getState());
	        customer.setEmail(customerDto.getEmail());
	        customer.setPhone(customerDto.getPhone());
	    }

}
