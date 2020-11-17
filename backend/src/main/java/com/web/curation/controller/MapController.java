package com.web.curation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.SidoCodeDTO;
import com.web.curation.model.service.MapService;
import com.web.curation.model.service.StoreService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
public class MapController {
	// http://localhost:8080/swagger-ui.html hi
	public static final Logger logger = LoggerFactory.getLogger(MapController.class);
	
	@Autowired
	private MapService  mapService; 
	
	@Autowired
	private StoreService storeservice;
	
	
    @ApiOperation(value = "모든 sido의 정보를 반환한다.", response = List.class)
	@RequestMapping(value = "/map/sido", method = RequestMethod.POST)
	public ResponseEntity<List<SidoCodeDTO>> selectsido() throws Exception {
    	//System.out.println("반환해보자");
    	List<SidoCodeDTO> list = mapService.selectSido();

		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SidoCodeDTO>>(list, HttpStatus.OK);
	}
    
    @ApiOperation(value = "모든 gugun의 정보를 반환한다.", response = List.class)
    @RequestMapping(value = "/map/gugun", method = RequestMethod.POST)
    public ResponseEntity<List<SidoCodeDTO>> selectgugun(@RequestBody SidoCodeDTO dto) throws Exception {
    	//System.out.println(dto.getSido_name());
    	//System.out.println(dto.getSido_code());
    	List<SidoCodeDTO> list = mapService.selectGugun(dto.getSido_code());
    	if (list.isEmpty()) {
    		return new ResponseEntity(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<SidoCodeDTO>>(list, HttpStatus.OK);
    }
    
    @ApiOperation(value = "모든 dong의 정보를 반환한다.", response = List.class)
    @RequestMapping(value = "/map/dong", method = RequestMethod.POST)
    public ResponseEntity<List<SidoCodeDTO>> selectdong(@RequestBody SidoCodeDTO dto) throws Exception {
    	//System.out.println(dto.getGugun_code());
    	//System.out.println("무선일이야");
    	List<SidoCodeDTO> list = mapService.selectDong(dto.getGugun_code());
    	//System.out.println();
    	if (list.isEmpty()) {
    		return new ResponseEntity(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<SidoCodeDTO>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 음식점의 정보를 반환한다.", response = List.class)
    @RequestMapping(value = "/map/list", method = RequestMethod.POST)
    public ResponseEntity<List<RestaurantsDto>> selectStore(@RequestBody SidoCodeDTO dto) throws Exception {
    	//System.out.println(dto.getDong());
    	List<RestaurantsDto> sublist = mapService.selectStore(dto.getDong());
    	//System.out.println(sublist.size());
    	List<RestaurantsDto> list = sublist.subList(0,20);
    	//System.out.println(list);
    	list = storeservice.image(list);
    	if (list.isEmpty()) {
    		return new ResponseEntity(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<RestaurantsDto>>(list, HttpStatus.OK);
    }
    
    @ApiOperation(value = "근처 음식점의 정보를 반환한다.", response = List.class)
    @RequestMapping(value = "/map/search", method = RequestMethod.POST)
    public ResponseEntity<List<RestaurantsDto>> selectMyStore(@RequestBody RestaurantsDto dto) throws Exception {
    	
    	List<RestaurantsDto> list = mapService.selectMyStore(dto);
    	//System.out.println(list);
    	list = storeservice.image(list);
    	//System.out.println(list);
    	if (list.isEmpty()) {
    		return new ResponseEntity(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<RestaurantsDto>>(list, HttpStatus.OK);
    }
}
