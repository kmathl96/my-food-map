package com.web.curation.model.repository;

import java.util.List;

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.dto.ReviewDto;

public interface CommentDao {
	public List<CommentDto> list(int reviewId);
	public void create(CommentDto comment);
	public void delete(CommentDto comment);
	public int count(int reviewId);
	public void check(CommentDto comment);
}
