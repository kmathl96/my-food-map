package com.web.curation.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.exception.MyException;
import com.web.curation.model.dto.LikeDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.dto.MemberPwDto;
import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;
import com.web.curation.model.service.CommentService;
import com.web.curation.model.service.ReviewService;
import com.web.curation.model.service.StoreService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class StoreController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreService storeservice;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CommentService commentService;
	
	private String loginid;

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
	
  	@ApiOperation(value="해당 음식점 모든 리뷰 조회 서비스", response=List.class)
	@RequestMapping(value = "/restaurants/{restaruantId}/reviews/{userid}", method = RequestMethod.GET)
  	public ResponseEntity<Map<String,Object>> listSearch(@PathVariable int restaruantId, @PathVariable int userid)  throws Exception {
  		
  		List<ReviewDto> list=null;
  		list=storeservice.searchreview(restaruantId);
  		for (int i=0; i<list.size();i++) {
			LikeDto dto = new LikeDto();
			ReviewDto review = list.get(i);
			int temp = review.getResid();
			int temp2 = review.getUserid();
			
			String resname = reviewService.resname(temp);
			list.get(i).setResname(resname);
			String nickname = userService.nickname(temp2);
			list.get(i).setNickname(nickname);
			list.get(i).setUser_image(userService.userimage(temp2));
			list.get(i).setComment_cnt(commentService.count_comment(review.getNo()));
			dto.setReviewid(review.getNo());
			dto.setUserid(userid); 
			review.setLike(reviewService.searchLike(dto));
		}
  		
  		if (list==null || list.size()==0) {
  			return Fail("no",HttpStatus.NO_CONTENT);
  		}
  		
  		return Success(list);
	}
    
	@ApiOperation(value = "restaurants 번호로 idrestaurants 의 정보를 찾는다.", response = List.class)
	@RequestMapping(value = "/restaurants/{restaruantId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> findResByNo(@PathVariable int restaruantId) throws Exception {
	 	System.out.println(restaruantId);
	 	RestaurantsDto one = storeservice.search(restaruantId);
	 	// 리뷰중에 좋아요 제일 많은거 출력
	 	ReviewDto review = storeservice.rankone(restaruantId);
	 	
	 	if(review == null || review.getImage().equals("")||review.getImage()==null) {
	 		one = storeservice.image(one);	 		
	 	}else {
	 		one.setImage(review.getImage());
	 	}
		if (one==null || one.getIdrestaurants()==0) {
			return Fail("no",HttpStatus.NO_CONTENT);
		}
		return Success(one);
	}

    
    @ApiOperation(value = "음식점 list를 받아온다 ", response = List.class)
   	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
   	public ResponseEntity<Map<String,Object>> listRes() throws Exception {
    	
   		List<RestaurantsDto> list = storeservice.searchAll();
   		list = storeservice.image(list);
   		return Success(list);
   	}

    @ApiOperation("식당 검색")
	@RequestMapping(value="/restaurants/search", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> SearchStore(@RequestBody RestaurantsDto dto) throws Exception {
		try {
			System.out.println(dto.getDoro());
			List<RestaurantsDto> store_list = storeservice.searchStore(dto.getDoro());
			store_list = storeservice.meter(store_list);
			store_list = storeservice.image(store_list);
			System.out.println(store_list);
			
			return Success(store_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("식당 검색 실패", HttpStatus.OK);
	}

}