package com.web.curation.model.dto;

public class FollowDto {
	private int no;
	private int followerId;
	private int followingId;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getFollowerId() {
		return followerId;
	}
	public void setFollowerId(int followerId) {
		this.followerId = followerId;
	}
	public int getFollowingId() {
		return followingId;
	}
	public void setFollowingId(int followingId) {
		this.followingId = followingId;
	}
	
	@Override
	public String toString() {
		return "FollowDto [no=" + no + ", followerId=" + followerId + ", followingId=" + followingId + "]";
	}
	
	public FollowDto() {
		super();
	}
	public FollowDto(int no, int followerId, int followingId) {
		super();
		this.no = no;
		this.followerId = followerId;
		this.followingId = followingId;
	}
}
