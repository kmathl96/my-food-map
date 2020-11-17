package com.web.curation.model.repository;

import java.util.List;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;


public interface StoreDao {
	public List<ReviewDto> searchAllreview();
	public List<RestaurantsDto> searchAll();
	public RestaurantsDto search(int no);
	public List<ReviewDto> searchreview(int no);
	public int register(RestaurantsDto dto);
	public ReviewDto rankone(int no);
	public List<RestaurantsDto> searchStore(String input);
	
}
