package com.jwtSecuritywithcrud.serviceImpl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwtSecuritywithcrud.UserRepository.UserRepository;
import com.jwtSecuritywithcrud.service.UserService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);	
	@Autowired
	private  UserRepository userRepository;
   
	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			logger.info(username+"PRSDFJHDHFHDHFSDHFKDSLHH");
				// TODO Auto-generated method stub
				return userRepository.findByEmail(username)
						.orElseThrow(()-> new UsernameNotFoundException("user not found"));
			}
		};
	}
	
	
	
}
