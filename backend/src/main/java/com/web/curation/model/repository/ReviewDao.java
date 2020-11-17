package com.web.curation.model.repository;

import java.util.List;

import com.web.curation.model.dto.LikeDto;
import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;

public interface ReviewDao {
	public List<ReviewDto> review_list();
	public List<ReviewDto> user_review(int userId);
	public void insertLike(LikeDto like);
	public void deleteLike(LikeDto like);
	public LikeDto searchLike(LikeDto like);
	public void register(ReviewDto dto);
	public void changerank(ReviewDto dto);
	public String resname(int resid);
	public List<ReviewDto> searchReview(String input);
	public ReviewDto getReview(int reviewId);

}
