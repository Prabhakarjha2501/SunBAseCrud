package com.jwtSecuritywithcrud.service;

import com.jwtSecuritywithcrud.dto.JwtAuthenticationResponse;
import com.jwtSecuritywithcrud.dto.RefershTokenRequest;
import com.jwtSecuritywithcrud.dto.SignUpRequest;
import com.jwtSecuritywithcrud.dto.SigninRequest;
import com.jwtSecuritywithcrud.model.User;

public interface AuthenticationService {

	JwtAuthenticationResponse signin(SigninRequest SigninRequest);
	 User signup(SignUpRequest signUpRequest);
	 JwtAuthenticationResponse refereshToken(RefershTokenRequest refershTokenRequest);
}
