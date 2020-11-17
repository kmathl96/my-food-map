package com.web.curation.model.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.MemberDto;
import com.web.curation.model.dto.MemberPwDto;
import com.web.curation.model.repository.FollowDao;
import com.web.curation.model.repository.RegisterDao;
import com.web.curation.util.MailUtil;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	RegisterDao userRepository;
	
	@Autowired
	FollowDao followDao;

	@Override
	public void join(MemberDto member) throws Exception {
		if(member.getImage()==null||member.getImage().equals("")) {
			member.setImage("https://firebasestorage.googleapis.com/v0/b/my-food-652b5.appspot.com/o/kakao_profile.jpg?alt=media&token=f3636cc6-b8ed-415b-97ab-91c49a7bdde0");
		}
			userRepository.join(member);
	}
	
	@Override
	public MemberDto login(String email, String pw) throws Exception {
		MemberDto info = userRepository.selectByEmail(email);
		//System.out.println("info " + info);
		if (info != null && info.getPassword().equals(pw)) {
			return info;
		} else {
			return null;
		}
	}
	@Override
	public boolean signOut(int id, String pw) {
		try {
			MemberDto finduser = userRepository.select(id);
			if(pw.equals(finduser.getPassword())) {
				userRepository.delete(finduser);
				return true;
			}else {
				throw new SQLException("잘못된 비밀번호입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String update(MemberPwDto user) throws UnsupportedEncodingException {
	try {
			
			MemberDto finduser = userRepository.select(user.getUserid());

			if(user.getNickname()!=null&&!user.getNickname().equals("")) {
				finduser.setNickname(user.getNickname());
			}
			if(user.getNewpassword()!=null&&!user.getNewpassword().equals("")) {
				if(user.getPassword().equals(user.getNewpassword())) {
					throw new Exception("password가 같습니다.");
				}else {
					finduser.setPassword(user.getNewpassword());
				}
			}
			//System.out.println(finduser);
			userRepository.update(finduser);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "오류 발생";
		}
	}

	@Override
	public boolean ChecknickName(String nickName) {
		try {
			if(userRepository.selectonenickname(nickName)==null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changePw(MemberPwDto user) throws Exception {
		try {
			MemberDto finduser = userRepository.select(user.getUserid());

			if (user.getNickname() != null && !user.getNickname().equals("")) {
				finduser.setNickname(user.getNickname());
			}
			if (user.getNewpassword() != null && !user.getNewpassword().equals("")) {
				if (user.getPassword().equals(user.getNewpassword())) {
					throw new Exception("password가 같습니다.");
				} else {
					finduser.setPassword(user.getNewpassword());
				}
			}
			userRepository.update(finduser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MemberDto select(int userid) throws SQLException {
		return userRepository.select(userid);
	}

	@Override
	public String email(int id) throws Exception {
		MailUtil mu = new MailUtil();

		String code = mu.CreateAuthCode();// 이메일 인증 코드 생성부
		String subject = "[SSAFY SNS] 이메일 인증 코드 입니다. ";
		StringBuffer sbuff = new StringBuffer();
		sbuff.append("<div align='center' style='border:1px solid black; font-family:verdana'>")
				.append("<h3 style='font-size: 130%'> SSAFY SNS 이메일 인증 코드를 안내해 드리겠습니다.</h3>")
				.append("<div style='font-size: 130%'> SSAFY SNS 이메일 인증 코드는 <strong>").append(code)
				.append("</strong> 입니다.</div> <br/></div>");
		String msg = sbuff.toString();
		
		MemberDto dto = userRepository.select(id);
		if (dto != null) {
			try {
				mu.sendMail(dto.getEmail(), subject, msg);
				return code;
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new Exception();
			}
		}
		return null;
	}
	
	// follow

	@Override
	public void insertFollow(FollowDto follow) {
		followDao.insertFollow(follow);
	}

	@Override
	public void deleteFollow(FollowDto follow) {
		followDao.deleteFollow(follow);
	}
	@Override
	public MemberDto selectByNickname(String nickname) throws SQLException {
		return userRepository.selectonenickname(nickname);
	}


	@Override
	public boolean searchFollow(FollowDto follow) throws Exception {
		FollowDto dto = followDao.searchFollow(follow);
		if (dto==null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<Integer> getFollowingList(int userId) {
		return followDao.getFollowingList(userId);
	}

	@Override
	public List<Integer> getFollowerList(int userId) {
		return followDao.getFollowerList(userId);
	}

	@Override
	public String nickname(int userid) {
		return userRepository.nickname(userid);
	}

	@Override
	public boolean checkuser(String email) throws Exception {
		MemberDto info = userRepository.selectByEmail(email);
		if(info == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public List<MemberDto> searchUser(String input) throws SQLException {
		return userRepository.searchUser(input);
	}

	@Override
	public String userimage(int userid) {
		return userRepository.userimage(userid);
	}

}
