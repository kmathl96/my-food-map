package com.web.curation.model.dto;

import java.sql.Date;

public class ReviewDto {
	private int no;
	private int userid;
	private int resid;
	private String content;
	private int reviewrank;
	private Date create_date;
	private String image;
	private int like_cnt;
	private String nickname;
	private String resname;
	private String email;
	private boolean isLike;
	private int comment_cnt;
	private String user_image;
	

	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public String getResname() {
		return resname;
	}
	public void setResname(String resname) {
		this.resname = resname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getResid() {
		return resid;
	}
	public void setResid(int resid) {
		this.resid = resid;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}
	public boolean isLike() {
		return isLike;
	}
	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReviewrank() {
		return reviewrank;
	}
	public void setReviewrank(int reviewrank) {
		this.reviewrank = reviewrank;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "ReviewDto [no=" + no + ", userid=" + userid + ", resid=" + resid + ", content=" + content + ", rank="
				+ reviewrank + ", create_date=" + create_date + ", image=" + image + ", like_cnt=" + like_cnt + ", nickname="
				+ nickname + ", email=" + email + ", isLike=" + isLike + ", comment_cnt=" + comment_cnt + "]";
	}
	public ReviewDto(int no, String email, String content, int rank, Date create_date, String image, int userid) {
		super();
		this.no = no;
		this.email = email;
		this.content = content;
		this.reviewrank = rank;
		this.create_date = create_date;
		this.image = image;
		this.userid = userid;
	}
	public ReviewDto() {
		super();
	}
	
	
}
