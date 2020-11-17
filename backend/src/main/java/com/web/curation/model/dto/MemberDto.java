package com.web.curation.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberDto implements Serializable {
	private int userid;
	private String email;
	private String password;
	private String nickname;
	private LocalDateTime create_date;
	private boolean isFollowed;
	private int follower_cnt;
	private int following_cnt;
	private String image;

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public MemberDto() {
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
	public boolean isFollowed() {
		return isFollowed;
	}
	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	public int getFollower_cnt() {
		return follower_cnt;
	}
	public void setFollower_cnt(int follower_cnt) {
		this.follower_cnt = follower_cnt;
	}
	public int getFollowing_cnt() {
		return following_cnt;
	}
	public void setFollowing_cnt(int following_cnt) {
		this.following_cnt = following_cnt;
	}
	
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "MemberDto [userid=" + userid + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", create_date=" + create_date + "]";
	}
	public MemberDto(int userid, String email, String password, String nickname, LocalDateTime createDate) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.create_date = createDate;
	}
	public MemberDto(int userid, String email, String password, String nickname) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
	public MemberDto(int userid, String email, String password, String nickname, LocalDateTime create_date,
			boolean isFollowed, int follower_cnt, int following_cnt) {
		super();
		this.userid = userid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.create_date = create_date;
		this.isFollowed = isFollowed;
		this.follower_cnt = follower_cnt;
		this.following_cnt = following_cnt;
	}

}
