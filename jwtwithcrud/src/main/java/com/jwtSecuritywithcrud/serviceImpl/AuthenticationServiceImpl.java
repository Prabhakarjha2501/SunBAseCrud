package com.jwtSecuritywithcrud.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtSecuritywithcrud.UserRepository.UserRepository;
import com.jwtSecuritywithcrud.dto.JwtAuthenticationResponse;
import com.jwtSecuritywithcrud.dto.RefershTokenRequest;
import com.jwtSecuritywithcrud.dto.SignUpRequest;
import com.jwtSecuritywithcrud.dto.SigninRequest;
import com.jwtSecuritywithcrud.model.Role;
import com.jwtSecuritywithcrud.model.User;
import com.jwtSecuritywithcrud.service.AuthenticationService;
import com.jwtSecuritywithcrud.service.JwtService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private final UserRepository   userRepository;
	
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder  passwordEncoder;
	private final JwtService jwtService;
	
	public User signup(SignUpRequest signUpRequest) {
		User user= new User();
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstname());
		user.setSecondname(signUpRequest.getLastname());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		
		return userRepository.save(user);
	}
	
	public JwtAuthenticationResponse signin(SigninRequest SigninRequest) {
		  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(SigninRequest.getEmail(), SigninRequest.getPassword()));
		  
		  User user=userRepository.findByEmail(SigninRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
		  String jwt=jwtService.genrateToken(user);
		  String refereshtoken=jwtService.generateRefreshToken(new HashMap<>(),user);
		  
		  JwtAuthenticationResponse jwtAuthenticationResponse =new JwtAuthenticationResponse();
		  
		   jwtAuthenticationResponse.setToken(jwt);;
		  jwtAuthenticationResponse.setRefreshtoken(refereshtoken);
		  
		  return jwtAuthenticationResponse;
	}
	
	public JwtAuthenticationResponse refereshToken(RefershTokenRequest refershTokenRequest) {
		String userEmail=jwtService.extractUserName(refershTokenRequest.getToken());
		User user= userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refershTokenRequest.getToken(), user)) {
			
			String jwt=jwtService.genrateToken(user);
			JwtAuthenticationResponse  jwtAuthenticationResponse = new JwtAuthenticationResponse(); 
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshtoken(refershTokenRequest.getToken());
			return jwtAuthenticationResponse;
		}
		
		
		return null;
		
		
		
	}
	
	
	
}
