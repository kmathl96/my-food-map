package com.web.curation.model.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.SidoCodeDTO;


@Repository
public class MapDAOImpl implements MapDAO {

	@Autowired
	SqlSession session;
	

	@Override
	public List<SidoCodeDTO> selectSido() throws Exception {
	
		return session.selectList("map.selectsido");
	}

	@Override
	public List<SidoCodeDTO> selectGugun(String sido) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList("map.selectgugun",sido);
	}

	@Override
	public List<SidoCodeDTO> selectDong(String gugun) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList("map.selectdong",gugun);
	}

	@Override
	public List<RestaurantsDto> selectStore(String dong) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList("map.selectstore",dong);
	}

	@Override
	public List<RestaurantsDto> selectMyStore(RestaurantsDto dto) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("lon",dto.getLat());
		map.put("lat",dto.getLon());
		return session.selectList("map.selectmystore",map);
	}



}
