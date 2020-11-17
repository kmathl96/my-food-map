package com.web.curation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.web.curation.model.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(request.getMethod()+" : "+ request.getServletPath());
		
		
		final String token = request.getHeader(HEADER_AUTH);
		//String token = request.getHeader("jwt-auth-token");

		if(token != null && jwtService.checkValid(token)){
			//jwtService.checkValid(token);
			//log.trace("토큰 사용 가능 : {}",token);
			
			return true; 
			
		}else{
			throw new RuntimeException("인증 토큰이 없습니다.");
		}
	}
}
