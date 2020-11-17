package com.web.curation.model.dto;

public class LikeDto {
	private int no;
	private int reviewid;
	private int userid;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getReviewid() {
		return reviewid;
	}
	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userId2) {
		this.userid = userId2;
	}
	public LikeDto() {
		super();
	}
	public LikeDto(int no, int reviewid, int userid) {
		super();
		this.no = no;
		this.reviewid = reviewid;
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "LikeDto [no=" + no + ", reviewid=" + reviewid + ", userid=" + userid + "]";
	}
}
