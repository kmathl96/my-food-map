package com.web.curation.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.dto.MemberPwDto;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class UserTestController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	private int loginid;

	@ExceptionHandler
	private ResponseEntity<Map<String, Object>> Success(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	private ResponseEntity<Map<String, Object>> Fail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "no");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}



	
	@ApiOperation(value ="토큰 로그인")
	@RequestMapping(value ="/user/signin", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> signin(@RequestBody MemberDto user, HttpServletResponse res){
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		
		MemberDto loginUser;
		try {
			loginUser = userService.login(user.getEmail(), user.getPassword());
			String token = jwtService.create(loginUser);
			res.setHeader("Authorization",token);
			result.put("status",true);
			result.put("data", loginUser);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e1) {
			e1.printStackTrace();
			result.put("message","로그인 실패");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(result,status);
	}


	@ApiOperation("로그인한 회원 정보 반환")
	@GetMapping("/info")
	public ResponseEntity<Map<String,Object>> getInfo(HttpServletRequest req,
			@RequestBody MemberDto user){
		Map<String,Object> result = new HashMap<>();
		HttpStatus status = null;
		try {
			result.putAll(jwtService.getting(req.getHeader("Authorization")));
			
			result.put("status",true);
			result.put("message","토큰 인증");
			result.put("data",user);
			status=HttpStatus.ACCEPTED;
		}catch (Exception e) {
			e.printStackTrace();
			result.put("message","토큰 인증 실패");
			status=HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(result,status);
	}

}
