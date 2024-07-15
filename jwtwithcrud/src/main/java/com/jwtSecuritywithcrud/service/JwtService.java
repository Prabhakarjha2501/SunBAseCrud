package com.jwtSecuritywithcrud.service;

import java.util.Map;


import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	
   String extractUserName(String jwt);	
   String genrateToken(UserDetails userDerails);
   boolean isTokenValid(String token, UserDetails userDetails);
   String generateRefreshToken(Map<String,Object> extrClaims,  UserDetails userdetails);

}
