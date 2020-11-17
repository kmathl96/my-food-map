package com.web.curation.model.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;


@Repository
public class StoreDaoImpl implements StoreDao{
	
	@Autowired
	SqlSession session;

	@Override
	public RestaurantsDto search(int no) {
		return session.selectOne("review.selectres",no);
	}

	@Override
	public List<ReviewDto> searchreview(int no) {
		return session.selectList("review.selectreviewalllist",no);
	}

	@Override
	public int register(RestaurantsDto dto) {
		return session.insert("review.insert",dto);
	}

	public List<ReviewDto> searchAllreview() {
		return session.selectList("review.selectreviewlist");
	}

	@Override
	public List<RestaurantsDto> searchAll() {
		return session.selectList("review.selectreslist");
	}

	@Override
	public ReviewDto rankone(int no) {
		return session.selectOne("review.selectrankone");
	}
	
	@Override
	public List<RestaurantsDto> searchStore(String input) {
		return session.selectList("map.searchStore", input);
	}

	
}
