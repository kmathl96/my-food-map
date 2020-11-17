package com.web.curation.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.curation.model.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("jwtService")
public class JwtServiceImpl implements JwtService{

	static private String salt="Ssafy409";
	
	static private Long expireMin=5L;
	
	@Override
	public <T> String create(MemberDto user){
		//System.out.print("Time: {}"+expireMin );
		
		String jwt = Jwts.builder()
						 .setHeaderParam("typ", "JWT")
						 .setHeaderParam("alg", "HS256")
						 .setHeaderParam("regDate", System.currentTimeMillis())
						 .setSubject("로그인토큰")
						 .setExpiration(new Date(System.currentTimeMillis()+1000*60*expireMin))
						 .claim("userid", user.getUserid())
						 .claim("nickname", user.getNickname())
						 .signWith(SignatureAlgorithm.HS256, salt.getBytes())
						 .compact();
			//System.out.println("jwt 토큰 발행: "+jwt);
		return jwt;
	}	


	@Override
	public boolean checkValid(String token) throws RuntimeException {
		try{
			//System.out.printf("토큰 점검 :{}",token);
			
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(salt.getBytes())
					  .parseClaimsJws(token);
			return true;
			
		}catch (Exception e) {
			throw new RuntimeException("토큰이 옳바르지 않습니다.");
		}
	}
	

	
	@Override
	public String get(String key) throws RuntimeException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwtbearer = request.getHeader("Authorization");
		
		if(jwtbearer==null) {
			throw new RuntimeException();
		}
		
		String[] strs=jwtbearer.split(" ");
		String jwt = strs[strs.length-1];
		//System.out.println("jwt split: "+jwt);
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(salt.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
			
			//System.out.println(this.checkValid(jwt));
		} catch (Exception e) {
			throw new RuntimeException();
		}
		//System.out.println(claims.getBody().get(key));
		if(claims.getBody().get(key)==null || claims.getBody().get(key).equals("")) {
			return null;
		}else {
			return claims.getBody().get(key).toString();
		}
	}


	@Override
	public Map<String, Object> getting(String jwt) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		}catch(final Exception e) {
			throw new RuntimeException();
		}
		//System.out.println("Claims : 토큰 얻기");
		//System.out.println(claims);
		
		return claims.getBody();
	}

	
}
