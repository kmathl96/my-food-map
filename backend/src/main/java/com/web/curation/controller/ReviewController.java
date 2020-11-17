package com.web.curation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.LikeDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.dto.ReviewDto;
import com.web.curation.model.service.CommentService;
import com.web.curation.model.service.ReviewService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

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
	
	@ApiOperation(value = "전체 리뷰 조회")
	@RequestMapping(value = "/review/list/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> ReviewList(@PathVariable int userId) throws Exception {
		try {
			List<ReviewDto> review_list = reviewService.review_list();
			for (int i=0; i<review_list.size();i++) {
				LikeDto dto = new LikeDto();
				ReviewDto review = review_list.get(i);
				//System.out.println(review);
				int temp = review.getResid();
				int temp2 = review.getUserid();
				
				String resname = reviewService.resname(temp);
				review_list.get(i).setResname(resname);
				String nickname = userService.nickname(temp2);
				review_list.get(i).setNickname(nickname);
				review_list.get(i).setUser_image(userService.userimage(temp2));
				dto.setReviewid(review.getNo());
				dto.setUserid(userId);
				review_list.get(i).setLike(reviewService.searchLike(dto));
				review_list.get(i).setComment_cnt(commentService.count_comment(review.getNo()));
			}
			//System.out.println();
			return Success(review_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("리뷰 조회 실패", HttpStatus.OK);
	}

	@ApiOperation("팔로잉 리뷰 조회")
	@RequestMapping(value="/review/following/{userId}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> FollowingReviewList(@PathVariable int userId) throws Exception {
		try {
			List<ReviewDto> review_list = reviewService.review_list();
			List<ReviewDto> following_review_list = new ArrayList<ReviewDto>();
			for (int i=0; i<review_list.size();i++) {
				LikeDto dto = new LikeDto();
				ReviewDto review = review_list.get(i);
				//System.out.println(review);
				int temp = review.getResid();
				int temp2 = review.getUserid();
			
				
				String resname = reviewService.resname(temp);
				review_list.get(i).setResname(resname);
				String nickname = userService.nickname(temp2);
				review_list.get(i).setNickname(nickname);
				review_list.get(i).setUser_image(userService.userimage(temp2));
				
				int writerId = review.getUserid();
				FollowDto follow = new FollowDto();
				follow.setFollowingId(writerId);
				follow.setFollowerId(userId);
				dto.setReviewid(review.getNo());
				dto.setUserid(userId);
				review_list.get(i).setLike(reviewService.searchLike(dto));
				review_list.get(i).setComment_cnt(commentService.count_comment(review.getNo()));
				if (userService.searchFollow(follow)) {
					following_review_list.add(review);
				}
			}
			return Success(following_review_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("리뷰 조회 실패", HttpStatus.OK);
	}

	
	@ApiOperation(value="사용자별 리뷰 조회")
	@RequestMapping(value="/review/{writerId}/{userId}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> UserReview(@PathVariable("writerId") int writerId, @PathVariable("userId") int userId) throws Exception {
		try {
			List<ReviewDto> user_review = reviewService.user_review(writerId);
			for (int i=0; i<user_review.size();i++) {
				
				LikeDto dto = new LikeDto();
				ReviewDto review = user_review.get(i);
				int temp = review.getResid();
				int temp2 = review.getUserid();
				
				String resname = reviewService.resname(temp);
				user_review.get(i).setResname(resname);
				String nickname = userService.nickname(temp2);
				user_review.get(i).setNickname(nickname);
				user_review.get(i).setUser_image(userService.userimage(temp2));
				user_review.get(i).setComment_cnt(commentService.count_comment(review.getNo()));
				dto.setReviewid(review.getNo());
				dto.setUserid(userId);
				review.setLike(reviewService.searchLike(dto));
			}
			return Success(user_review);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("리뷰 조회 실패", HttpStatus.OK);
	}

	@ApiOperation(value="좋아요")
	@RequestMapping(value="/review/like", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> insertUser(@RequestBody LikeDto like) throws Exception {
		//System.out.println(like);
		boolean check = reviewService.searchLike(like);
		if (check) {
			reviewService.deleteLike(like);
			return Success("Like -1");
		} else {
			reviewService.insertLike(like);
			return Success("Like +1");
		}
	}
	
	@ApiOperation(value="리뷰 작성")
	@RequestMapping(value="/review/create", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> insertReview(@RequestBody ReviewDto review) throws Exception {
		//System.out.println(review.getReviewrank());
		
		if(review.getImage().length()<5) {
			review.setImage("null");
		}
		//System.out.println(review);
		boolean check2=reviewService.register(review);
		if (check2) {
			reviewService.changerank(review);
			return Success("리뷰 작성 성공");
		} else {
			return Fail("리뷰 작성 실패",HttpStatus.OK);			
			
		}
	}
	
	@ApiOperation("리뷰 검색")
	@RequestMapping(value="/review/search", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> SearchUser(String input) throws Exception {
		try {
			List<ReviewDto> review_list = reviewService.searchReview(input);
			return Success(review_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("리뷰 검색 실패", HttpStatus.OK);
	}
	
	@ApiOperation(value = "리뷰 조회")
	@RequestMapping(value = "/review/{reviewId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> ReviewDetail(@PathVariable int reviewId) throws Exception {
		try {
			ReviewDto review = reviewService.getReview(reviewId);
			MemberDto writer = userService.select(review.getUserid());
			String resname = reviewService.resname(review.getResid());
			review.setResname(resname);
			review.setNickname(writer.getNickname());
			review.setUser_image(userService.userimage(review.getUserid()));
			review.setComment_cnt(commentService.count_comment(review.getNo()));
			//System.out.println(review);
			return Success(review);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("리뷰 조회 실패", HttpStatus.OK);
	}
}
