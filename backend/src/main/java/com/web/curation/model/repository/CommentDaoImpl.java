package com.web.curation.model.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.dto.CommentDto;

@Repository
public class CommentDaoImpl implements CommentDao {

	@Autowired
	SqlSession session;

	@Override
	public List<CommentDto> list(int reviewId) {
		return session.selectList("comment.selectList", reviewId);
	}

	@Override
	public void create(CommentDto comment) {
		session.insert("comment.create", comment);
	}

	@Override
	public void delete(CommentDto comment) {
		session.delete("comment.delete", comment);
	}

	@Override
	public int count(int reviewId) {
		return session.selectOne("comment.count", reviewId);
	}

	@Override
	public void check(CommentDto comment) {
		session.update("comment.check", comment);
	}
}
