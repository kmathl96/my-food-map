package com.web.curation.model.repository;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.MemberDto;

public interface FollowDao {
	public void insertFollow(FollowDto follow);
	public void deleteFollow(FollowDto follow);
	public FollowDto searchFollow(FollowDto follow) throws SQLException;
	public List<Integer> getFollowingList(int userId);
	public List<Integer> getFollowerList(int userId);
}
