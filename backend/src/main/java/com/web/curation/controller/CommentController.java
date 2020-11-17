package com.web.curation.controller;

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

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.service.CommentService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class CommentController {

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
	
	@ApiOperation("리뷰별 댓글 조회")
	@RequestMapping(value="/comment/{reviewId}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> CommentList(@PathVariable int reviewId) throws Exception {
		try {
			List<CommentDto> comment_list = commentService.comment_list(reviewId);
			for (int i=0; i<comment_list.size(); i++) {
				MemberDto user = userService.select(comment_list.get(i).getUserid());
				comment_list.get(i).setNickname(user.getNickname());
				comment_list.get(i).setUser_image(userService.userimage(comment_list.get(i).getUserid()));
			}
			return Success(comment_list);
		} catch (Exception e){
			e.printStackTrace();
		}
		return Fail("댓글 조회 실패", HttpStatus.OK);
	}
	
	@ApiOperation("댓글 작성")
	@RequestMapping(value="/comment/create_comment", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentDto comment) throws Exception {
		boolean check = commentService.create_comment(comment);
		if (check) {
			return Success("댓글 작성 성공");
		} else {
			return Fail("댓글 작성 실패", HttpStatus.OK);
		}
	}
	
	@ApiOperation("댓글 삭제")
	@RequestMapping(value="/comment/delete_comment/{userId}", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody CommentDto comment, @PathVariable("userId") int userId) throws Exception {
		if (userId == comment.getUserid()) {
			boolean check = commentService.delete_comment(comment);
			if (check) {
				return Success("댓글 삭제 성공");
			} else {
				return Fail("댓글 삭제 실패", HttpStatus.OK);
			}
		}
		return Fail("댓글 삭제 실패", HttpStatus.OK);
	}
}
