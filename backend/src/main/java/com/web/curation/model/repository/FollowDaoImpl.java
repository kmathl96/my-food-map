package com.web.curation.model.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.MemberDto;

@Repository
public class FollowDaoImpl implements FollowDao {
	
	@Autowired
	SqlSession session;
	
	@Override
	public void insertFollow(FollowDto follow) {
		session.insert("user.insertFollow", follow);
		session.update("user.plusFollowUserFollowerCnt", follow);
		session.update("user.plusUserFollowingCnt", follow);
	}

	@Override
	public void deleteFollow(FollowDto follow) {
		session.delete("user.deleteFollow", follow);
		session.update("user.minusFollowUserFollowerCnt", follow);
		session.update("user.minusUserFollowingCnt", follow);
	}
	
	@Override
	public FollowDto searchFollow(FollowDto follow) throws SQLException {
		FollowDto dto = session.selectOne("user.searchFollow", follow);
		return dto;
	}

	@Override
	public List<Integer> getFollowingList(int userId) {
		return session.selectList("user.selectFollowing", userId);
	}

	@Override
	public List<Integer> getFollowerList(int userId) {
		return session.selectList("user.selectFollower", userId);
	}

}