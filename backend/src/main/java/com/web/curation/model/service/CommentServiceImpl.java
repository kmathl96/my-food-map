package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.repository.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<CommentDto> comment_list(int reviewId) {
		return commentDao.list(reviewId);
	}

	@Override
	public boolean create_comment(CommentDto comment) throws Exception {
		try {
			commentDao.create(comment);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 작성 오류");
		}
	}

	@Override
	public boolean delete_comment(CommentDto comment) throws Exception {
		try {
			commentDao.delete(comment);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 삭제 오류");
		}
	}

	
	@Override
	public int count_comment(int reviewId) throws Exception {
		try {
			return commentDao.count(reviewId);
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 카운트 오류");
		}
	}

	@Override
	public boolean check_comment(CommentDto comment) throws Exception {
		try {
			commentDao.check(comment);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("댓글 확인 오류");
		}
	}

}
