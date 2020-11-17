package com.web.curation.model.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.SidoCodeDTO;
import com.web.curation.model.repository.MapDAO;

@Service
public class MapServiceImpl implements MapService {

	@Autowired
	private MapDAO mapDao;

	@Override
	public List<SidoCodeDTO> selectSido() throws Exception {
		// TODO Auto-generated method stub
		return mapDao.selectSido();
	}

	@Override
	public List<SidoCodeDTO> selectGugun(String sido) throws Exception {
		// TODO Auto-generated method stub
		return mapDao.selectGugun(sido);
	}

	@Override
	public List<SidoCodeDTO> selectDong(String gugun) throws Exception {
		// TODO Auto-generated method stub
		return mapDao.selectDong(gugun);
	}

	@Override
	public List<RestaurantsDto> selectStore(String dong) throws Exception {
		return mapDao.selectStore(dong);
	}

	@Override
	public List<RestaurantsDto> selectMyStore(RestaurantsDto dto) throws Exception {
		
		return mapDao.selectMyStore(dto);
	}

	

}
