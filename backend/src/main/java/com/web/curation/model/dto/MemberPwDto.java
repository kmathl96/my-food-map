package com.web.curation.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberPwDto implements Serializable {
	private int userid;
	private String email;
	private String password;
	private String newpassword;
	private String nickname;
	private LocalDateTime createDate;

	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public MemberPwDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "MemberDto [userid=" + userid + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", createDate=" + createDate + "]";
	}
	public MemberPwDto(int userid, String email, String password, String nickname, LocalDateTime createDate) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.createDate = createDate;
	}
	public MemberPwDto(int userid, String email, String password, String nickname) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	


}
