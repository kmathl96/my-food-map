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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.dto.ReviewDto;
import com.web.curation.model.service.CommentService;
import com.web.curation.model.service.ReviewService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class NoticeController {

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
	
	@ApiOperation("새 댓글 목록 조회")
	@RequestMapping(value="/notice/comment/{userId}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> NoticeList(@PathVariable int userId) throws Exception {
		try {
			List<ReviewDto> review_list = reviewService.user_review(userId);
			List<CommentDto> new_comment_list = new ArrayList<CommentDto>();
			for (int i=0; i<review_list.size(); i++) {
				List<CommentDto> comment_list = commentService.comment_list(review_list.get(i).getNo());
				for (int j=0; j<comment_list.size(); j++) {
					if (comment_list.get(j).isChecked() == false) {						
						new_comment_list.add(comment_list.get(j));
					}
				}
			}
			return Success(new_comment_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("새 댓글 목록 조회 실패", HttpStatus.OK);
	}
	
	@ApiOperation("새 댓글 확인")
	@RequestMapping(value="/notice/comment/{userId}/{reviewId}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> CheckNewComment(@PathVariable("userId") int userId, @PathVariable("reviewId") int reviewId) throws Exception {
		try {
			List<CommentDto> comment_list = commentService.comment_list(reviewId);
			//System.out.println(comment_list);
			for (int i=0; i<comment_list.size(); i++) {
				if (comment_list.get(i).isChecked() == false) {
					commentService.check_comment(comment_list.get(i));
				}
			}
			return Success("새 댓글 확인 성공");
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("새 댓글 확인 실패", HttpStatus.OK);
	}
}
