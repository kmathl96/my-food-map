package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> comment_list(int reviewId);
	public boolean create_comment(CommentDto comment) throws Exception;
	public boolean delete_comment(CommentDto comment) throws Exception;
	public int count_comment(int reviewId) throws Exception;
	public boolean check_comment(CommentDto comment) throws Exception;

}
