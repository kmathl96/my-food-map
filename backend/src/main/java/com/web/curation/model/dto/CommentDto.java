package com.web.curation.model.dto;

import java.sql.Date;

public class CommentDto {
	private int no;
	private int userid;
	private int reviewid;
	private String content;
	private Date create_date;
	private String nickname;
	private boolean is_checked;
	private String user_image;
	
	
	
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getReviewid() {
		return reviewid;
	}
	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isChecked() {
		return is_checked;
	}
	public void setChecked(boolean isChecked) {
		this.is_checked = isChecked;
	}
	
	public CommentDto() {
		super();
	}
	public CommentDto(int no, int userid, int reviewid, String content, Date create_date, String nickname,
			boolean isChecked) {
		super();
		this.no = no;
		this.userid = userid;
		this.reviewid = reviewid;
		this.content = content;
		this.create_date = create_date;
		this.nickname = nickname;
		this.is_checked = isChecked;
	}
	
	@Override
	public String toString() {
		return "CommentDto [no=" + no + ", userid=" + userid + ", reviewid=" + reviewid + ", content=" + content
				+ ", create_date=" + create_date + ", nickname=" + nickname + ", isChecked=" + is_checked + "]";
	}
}
