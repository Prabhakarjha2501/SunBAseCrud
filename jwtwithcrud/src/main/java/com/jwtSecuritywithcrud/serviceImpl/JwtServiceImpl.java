package com.jwtSecuritywithcrud.serviceImpl;
import java.security.Key;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jwtSecuritywithcrud.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);	
	
	private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);	
	
	public String genrateToken(UserDetails userdetails) {
		
		return Jwts.builder().setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000*60*24)).signWith(getSiginKey(),SignatureAlgorithm.HS256).compact();
		
	}
	
	
	//expire in 7 days
public String generateRefreshToken(Map<String,Object> extrClaims,  UserDetails userdetails) {
		
		return Jwts.builder().setClaims(extrClaims)
				.setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 604800000))
				.signWith(getSiginKey(),SignatureAlgorithm.HS256).compact();
		
	}
	
	//get user name
	
	public String extractUserName(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	
	// to extract the username from toke
	
	private <T> T extractClaim(String token, Function<Claims, T>claimsResolvers) {
		final Claims claims= extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSiginKey())
				.build().parseClaimsJws(token).getBody();
	}
	
	private Key getSiginKey() {
		  byte [] key= Decoders.BASE64.decode("Z3VybWFuLmV4YW1wbGUuY29tL3N0cm9uZy1yYW5kb20tYmFzZTY0LWtleQ==");
		  return Keys.hmacShaKeyFor(key);
	}
	
	
	
//	private Key getSinginKey() {
//        return Keys.secretKeyFor(SignatureAlgorithm.HS256); // or HS384, HS512
//    }
	
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username= extractUserName(token);
		logger.info(username+"this is token valid info");
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}






//package com.jwtSecuritywithcrud.serviceImpl;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import com.jwtSecuritywithcrud.service.JwtService;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class JwtServiceImpl implements JwtService {
//
//  // Generate and store this key securely in a real application
//  private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//  
//  @Override
//  public String genrateToken(UserDetails userDetails) {
//      return Jwts.builder()
//              .setSubject(userDetails.getUsername())
//              .setIssuedAt(new Date(System.currentTimeMillis()))
//              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//              .signWith(SIGNING_KEY)
//              .compact();
//  }
//
//  // expire in 7 days
//  public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//      return Jwts.builder()
//              .setClaims(extraClaims)
//              .setSubject(userDetails.getUsername())
//              .setIssuedAt(new Date(System.currentTimeMillis()))
//              .setExpiration(new Date(System.currentTimeMillis() + 604800000))
//              .signWith(SIGNING_KEY)
//              .compact();
//  }
//
//  public String extractUserName(String token) {
//      return extractClaim(token, Claims::getSubject);
//  }
//
//  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//      final Claims claims = extractAllClaims(token);
//      return claimsResolver.apply(claims);
//  }
//
//  private Claims extractAllClaims(String token) {
//      return Jwts.parserBuilder()
//              .setSigningKey(SIGNING_KEY)
//              .build()
//              .parseClaimsJws(token)
//              .getBody();
//  }
//
//  public boolean isTokenValid(String token, UserDetails userDetails) {
//      final String username = extractUserName(token);
//      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//  }
//
//  private boolean isTokenExpired(String token) {
//      return extractClaim(token, Claims::getExpiration).before(new Date());
//  }
//
////	@Override
////	public String genrateToken(UserDetails userDerails) {
////		// TODO Auto-generated method stub
////		return null;
////	}
//}
//
