package com.web.curation.model.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.dto.LikeDto;
import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	@Autowired
	SqlSession session;
	
	@Override
	public List<ReviewDto> review_list() {
		return session.selectList("review.selectreviewlist");
	}
	
	@Override
	public List<ReviewDto> user_review(int userId) {
		return session.selectList("review.selectUser", userId);
	}

	@Override
	public void insertLike(LikeDto like) {
		session.insert("review.insertLike", like);
		session.update("review.plusLikeCnt", like);
	}

	@Override
	public void deleteLike(LikeDto like) {
		session.delete("review.deleteLike", like);
		session.update("review.minusLikeCnt", like);
	}
	
	@Override
	public LikeDto searchLike(LikeDto like) {
		return session.selectOne("review.searchLike", like);
	}
	
	@Override
	public void register(ReviewDto dto) {
		 session.insert("review.insertreview",dto);
	}

	@Override
	public void changerank(ReviewDto dto) {
		RestaurantsDto res = session.selectOne("review.selectres",dto.getResid());
		float tgrade = res.getgrade();
		int tcountgrade = res.getCountgrade();
		float temp = tgrade * tcountgrade;
		//System.out.println(tgrade+" "+tcountgrade+" "+temp);
		
		float grade = (temp + dto.getReviewrank())/(tcountgrade+1);
		grade=(float) (Math.round(grade*10)/10.0);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("resid",dto.getResid());
		session.update("review.changecountrank",map);
		map.put("grade",grade);
		session.update("review.changerank",map);
		
	}

	@Override
	public String resname(int resid) {
		return session.selectOne("review.resname",resid);

	}
	
	@Override
	public ReviewDto getReview(int reviewId) {
		return session.selectOne("review.selectByReviewId", reviewId);
	}

	@Override
	public List<ReviewDto> searchReview(String input) {
		return session.selectList("review.searchReview", input);
	}


}
