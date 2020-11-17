package com.web.curation.controller;

import java.io.UnsupportedEncodingException;
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
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@EnableAutoConfiguration
public class UserController {

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

	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/user/join", method = RequestMethod.POST)
	public  ResponseEntity<Map<String, Object>> Join(@RequestBody MemberDto dto) throws Exception {
		try {
			userService.join(dto);
			boolean check=false;
			if(dto!=null)check =true;
			if (check) {
				return Success("회원가입에 성공하셨습니다");
			}else {
				return Fail("다시 확인해주세요", HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return Fail(e.toString(),HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<MemberDto> Login(@RequestBody MemberDto dto) throws Exception {
		MemberDto result = userService.login(dto.getEmail(), dto.getPassword());
//		System.out.println(result);
//		loginid = result.getUserid();
		return new ResponseEntity<MemberDto>(result, HttpStatus.OK);
	}

	@ApiOperation("user 업데이트")
	@PutMapping("/user")
	public ResponseEntity<Map<String, Object>> update(@RequestBody MemberPwDto user) {
		try {
			String msg = userService.update(user);
			if (msg.equals("OK")) {
				return Success("회원정보 변경에 성공하셨습니다");
			} else {
				return Fail(msg, HttpStatus.OK);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Fail("회원정보 변경 실패", HttpStatus.OK);
		}
	}

	@ApiOperation("닉네임 중복확인")
	@GetMapping("/user/checkNickname/{nickName}")
	public ResponseEntity<Map<String, Object>> checkNickname(@PathVariable String nickName) {
		//System.out.println("in Controller: " + nickName);
		if (userService.ChecknickName(nickName)) {
			return Success("ok");
		} else {
			return Fail("no", HttpStatus.OK);
		}
	}

	@ApiOperation("이메일 인증 코드 생성")
	@GetMapping("/user/emailAuth/{id}")
	public ResponseEntity<Map<String, Object>> emailAuth(@PathVariable int id) {
		String code = null;
		try {
			code = userService.email(id);
			if (code == null) {
				return Fail("코드생성 실패", HttpStatus.OK);
			}
			return Success(code);

		} catch (Exception e) {
			return Fail("코드 생성 실패", HttpStatus.OK);
		}
	}

	@ApiOperation("회원 탈퇴")
	@PutMapping("/user/delete")
	public ResponseEntity<Map<String, Object>> signOut(@RequestBody MemberDto user) {
		if (userService.signOut(user.getUserid(), user.getPassword())) {
			return Success("회원 탈퇴 성공");
		} else {
			return Fail("비밀번호가 맞지 않습니다.", HttpStatus.OK);
		}
	}

	@ApiOperation("비밀번호 변경")
	@PutMapping("/user/changepw")
	public ResponseEntity<Map<String, Object>> changePW(@RequestBody MemberPwDto user) {
		if (user.getNewpassword().equals(user.getPassword())) {
			return Fail("비밀번호가 같습니다.", HttpStatus.OK);
		}
		try {
			if (userService.changePw(user)) {
				return Success("비밀번호 변경에 성공하셨습니다.");
			} else {
				return Fail("이다시 입력해주세요.", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Fail("다시 입력해주세요.", HttpStatus.OK);
	}

	@RequestMapping(value = "/user/nickname/{nickname}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<MemberDto> findByNickname(@PathVariable("nickname") String nickname, @PathVariable("userId") int userId) throws Exception {
		MemberDto dto = userService.selectByNickname(nickname);
		if (dto == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			FollowDto follow = new FollowDto();
			follow.setFollowerId(userId);
			follow.setFollowingId(dto.getUserid());
			dto.setFollowed(userService.searchFollow(follow));
			return new ResponseEntity<MemberDto>(dto, HttpStatus.OK);
		}
	}

	
	@ApiOperation("로그인한 회원 정보 반환")
	@GetMapping("/user")
	public ResponseEntity<Map<String,Object>> MyInfo(){
		MemberDto user=null;
		try {
			user = userService.select(loginid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null) return Fail("오류발생", HttpStatus.OK);
		user.setPassword(null);
		return Success(user);
	}
	
	@ApiOperation(value = "특정 회원 정보 반환", response = MemberDto.class)
	@RequestMapping(value = "/user/{id}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<MemberDto> findById(@PathVariable("id") int id, @PathVariable("userId") int userId) throws Exception {
		MemberDto dto = userService.select(id);
		FollowDto follow = new FollowDto();
		follow.setFollowerId(userId);
		follow.setFollowingId(id);
		dto.setFollowed(userService.searchFollow(follow));
		if (dto == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			//System.out.println(dto);
			return new ResponseEntity<MemberDto>(dto, HttpStatus.OK);			
		}
	}


	// follow
	// userId : 로그인 상태인 사용자
	// followingId : 팔로우되는 사용자
	@ApiOperation(value="팔로우")
	@RequestMapping(value="/user/follow", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> followUser(@RequestBody FollowDto dto) throws Exception {
		boolean check = userService.searchFollow(dto);
		if (check) {
			userService.deleteFollow(dto);
			return Success("Following -1");
		} else {
			userService.insertFollow(dto);
			return Success("Following +1");
		}
	}
	
	@ApiOperation("팔로잉 회원 목록")
	@RequestMapping(value = "/user/following/{profileUserId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> followingList(@PathVariable("profileUserId") int profileUserId, @PathVariable("userId") int userId) throws Exception {
		try {			
			List<Integer> following_list = userService.getFollowingList(profileUserId);
			List<MemberDto> followings = new ArrayList<MemberDto>();
			for (int i=0; i<following_list.size(); i++) {
				MemberDto following = userService.select(following_list.get(i));
				FollowDto follow = new FollowDto();
				follow.setFollowerId(following.getUserid());
				follow.setFollowingId(userId);
				following.setFollowed(userService.searchFollow(follow));
				following.setPassword("");
				followings.add(following);
			}
			return Success(followings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Fail("", HttpStatus.OK);
	}
	
	@ApiOperation("팔로워 회원 목록")
	@RequestMapping(value = "/user/follower/{profileUserId}/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> followerList(@PathVariable("profileUserId") int profileUserId, @PathVariable("userId") int userId) throws Exception {
		try {			
			List<Integer> follower_list = userService.getFollowerList(profileUserId);
			List<MemberDto> followers = new ArrayList<MemberDto>();
			for (int i=0; i<follower_list.size(); i++) {
				MemberDto follower = userService.select(follower_list.get(i));
				FollowDto follow = new FollowDto();
				follow.setFollowerId(userId);
				follow.setFollowingId(follower.getUserid());
				follower.setFollowed(userService.searchFollow(follow));
				follower.setPassword("");
				followers.add(follower);
			}
			return Success(followers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Fail("", HttpStatus.OK);
	}
}
