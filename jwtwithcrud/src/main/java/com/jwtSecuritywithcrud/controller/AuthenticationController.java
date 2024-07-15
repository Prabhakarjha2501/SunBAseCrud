package com.jwtSecuritywithcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtSecuritywithcrud.dto.JwtAuthenticationResponse;
import com.jwtSecuritywithcrud.dto.RefershTokenRequest;
import com.jwtSecuritywithcrud.dto.SignUpRequest;
import com.jwtSecuritywithcrud.dto.SigninRequest;
import com.jwtSecuritywithcrud.model.User;
import com.jwtSecuritywithcrud.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	private AuthenticationService   authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest SignUpRequest){
	return ResponseEntity.ok(authenticationService.signup(SignUpRequest));	
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse>Sigin(@RequestBody SigninRequest SigninRequest){
	
		return  ResponseEntity.ok(authenticationService.signin(SigninRequest));
	}
	

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse>refresh(@RequestBody RefershTokenRequest refershTokenRequest){
	
		return  ResponseEntity.ok(authenticationService.refereshToken(refershTokenRequest));
	}
	
//	  @PostMapping("/refresh")
//	    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefershTokenRequest refershTokenRequest) {
//	        try {
//	            JwtAuthenticationResponse response = authenticationService.refereshToken(refershTokenRequest);
//	            if (response == null) {
//	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//	            }
//	            return ResponseEntity.ok(response);
//	        } catch (IllegalArgumentException e) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtAuthenticationResponse());
//	        } catch (UsernameNotFoundException e) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JwtAuthenticationResponse());
//	        }
//	    }
}
