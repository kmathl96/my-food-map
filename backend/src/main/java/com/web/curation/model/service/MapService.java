package com.web.curation.model.service;

import java.util.ArrayList;
import java.util.List;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.SidoCodeDTO;



public interface MapService {
	
	List<SidoCodeDTO> selectSido() throws Exception;

	List<SidoCodeDTO> selectGugun(String sido) throws Exception;

	List<SidoCodeDTO> selectDong(String gugun) throws Exception;

	List<RestaurantsDto> selectStore(String dong) throws Exception;
	List<RestaurantsDto> selectMyStore(RestaurantsDto dto) throws Exception;
}

