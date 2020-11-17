package com.web.curation.controller;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.curation.api.KakaoAPI;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="{*}", maxAge=6000)
@RestController
@EnableAutoConfiguration
public class UserSNSController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private KakaoAPI kakao;
	
	private String temptoken;
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handler(Exception e){
		return Fail(e.getMessage(), HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> Success(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("message", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> Fail(Object data, HttpStatus status) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state",  "fail");
		resultMap.put("message",  data);
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@RequestMapping(value="/login")
	public  ResponseEntity   login(@RequestParam("code") String code, HttpSession session) {
		String access_Token = kakao.getAccessToken(code);
      //  System.out.println("controller access_token : " + access_Token);
        
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        temptoken = access_Token;
       // System.out.println("login Controller : " + userInfo);
      //  System.out.println(userInfo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://i3a409.p.ssafy.io/user/login/"));
        
        
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("access_Token", access_Token);
        }
        try {
			if(userService.checkuser((String)userInfo.get("email"))) {
				//System.out.println(" 해당 유저가 존재 합니다 ( 로그인 진행 ");
				MemberDto result = userService.login((String)userInfo.get("email"),"kakao123");
				
				session.setAttribute("email", userInfo.get("email"));
				session.setAttribute("userid",result.getUserid());
				session.setAttribute("nickname",result.getNickname());
				headers.setLocation(URI.create("http://i3a409.p.ssafy.io/user/login/"+userInfo.get("email")+"/"+result.getUserid()+"/"+result.getNickname()));
				return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			}else {
				//System.out.println(" 해당 유저가 존재 하지 않습니다 ( 회원가입 진행 ) ");
				MemberDto dto = new MemberDto();
				//System.out.println(userInfo.get("email"));
				 
				dto.setEmail((String)userInfo.get("email"));
				dto.setNickname((String)userInfo.get("nickname"));
				dto.setPassword("kakao123");
				dto.setImage((String)userInfo.get("image"));
				userService.join(dto);
				
				//System.out.println(" 회원가입 후 로그인 진행 중 .... ");
				MemberDto result = userService.login((String)userInfo.get("email"),"kakao123");
				session.setAttribute("email", userInfo.get("email"));
				session.setAttribute("userid",result.getUserid());
				session.setAttribute("nickname",result.getNickname());
				headers.setLocation(URI.create("http://i3a409.p.ssafy.io/user/login/"+userInfo.get("email")+"/"+result.getUserid()+"/"+result.getNickname()));
				return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        //System.out.println("오류 발생");
        MemberDto dto = null;
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@ApiOperation(value = "로그아웃")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, Object>> logout( HttpSession session) throws Exception {
		 kakao.kakaoLogout(temptoken);
		 session.removeAttribute("access_Token");
		 session.removeAttribute("userId");
		 
		 // 토큰 삭제 
		 
		 return Success("로그아웃에 성공하셨습니다");
		    
		
	}
}



