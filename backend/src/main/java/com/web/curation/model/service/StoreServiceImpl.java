package com.web.curation.model.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.web.curation.model.dto.RestaurantsDto;
import com.web.curation.model.dto.ReviewDto;
import com.web.curation.model.repository.StoreDao;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StoreService storeservice;
	
	@Override
	public List<ReviewDto> searchAllreview() {
		return storeDao.searchAllreview();
	}

	@Override
	public List<RestaurantsDto> searchAll() {
		return storeDao.searchAll();
	}

	@Override
	public RestaurantsDto search(int no) {
		return storeDao.search(no);
	}

	@Override
	public List<ReviewDto> searchreview(int no) {
		return storeDao.searchreview(no);
	}
	@Override
	public int register(RestaurantsDto dto) {
		return storeDao.register(dto);
	}

	@Override
	public List<RestaurantsDto> image(List<RestaurantsDto> list) {
		int leng = list.size();
		boolean check = false;
		for (int i = 0; i < leng; i++) {
		 		String temp = list.get(i).getRes_type();
				switch (temp) {
				case "감성주점":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/bar1.jpg");
					else
						list.get(i).setImage("resimg/bar2.jpg");
					break;
				case "경양식":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/dishes1.jpg");
					else
						list.get(i).setImage("resimg/dishes2.jpg");
					break;
				case "과자점":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/bakery.jpg");
					else
						list.get(i).setImage("resimg/bakery2.jpg");
					break;
				case "기타":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/etc.png");
					else
						list.get(i).setImage("resimg/etc2.jpg");
					break;
				case "기타 휴게음식점":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/restingetc.jpg");
					else
						list.get(i).setImage("resimg/restingetc2.jpg");
					break;
				case "김밥(도시락)":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/gimbap.jpg");
					else
						list.get(i).setImage("resimg/gimbap2.png");
					break;
				case "까페":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/cafe.jpg");
					else
						list.get(i).setImage("resimg/cafe2.jpg");
					break;
				case "다방":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/dabang.jpg");
					else
						list.get(i).setImage("resimg/dabang2.jpg");
					break;
				case "라이브카페":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("esimg/livecafe.jpg");
					else
						list.get(i).setImage("resimg/livecafe2.jpg");
					break;
				case "백화점":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/department.jpg");
					else
						list.get(i).setImage("resimg/department2.jpg");
					break;
				case "분식":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/bunsick.jpg");
					else
						list.get(i).setImage("resimg/bunsick2.jpg");
					break;
				case "뷔페식":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/buffet.jpg");
					else
						list.get(i).setImage("resimg/buffet2.png");
					break;
				case "식육(숯불구이)":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/meat.jpg");
					else
						list.get(i).setImage("resimg/meat2.jpeg");
					break;
				case "외국음식전문점(인도태국등)":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/india.jpg");
					else
						list.get(i).setImage("resimg/india2.jpg");
					break;
				case "일반조리판매":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/common.jpg");
					else
						list.get(i).setImage("resimg/common2.jpg");
					break;
				case "일식":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/japanese.jpg");
					else
						list.get(i).setImage("resimg/japanese2.png");
					break;
				case "전통찻집":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/teashop.jpg");
					else
						list.get(i).setImage("resimg/teashop2.jpg");
					break;
				case "정종대포집소주방":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/soju.jpeg");
					else
						list.get(i).setImage("resimg/soju2.jpeg");
					break;
				case "중국식":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/china.jpg");
					else
						list.get(i).setImage("resimg/china2.jpg");
					break;
				case "커피숍":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/coffee.jpg");
					else
						list.get(i).setImage("resimg/coffee2.jpg");
					break;
				case "통닭(치킨)":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/chicken.jpg");
					else
						list.get(i).setImage("resimg/chicken2.png");
					break;
				case "패스트푸드":
					if (list.get(i).getIdrestaurants() % 2 == 0)
						check = true;
					else
						check = false;

					if (check)
						list.get(i).setImage("resimg/fastfood.jpg");
					else
						list.get(i).setImage("resimg/fastfood2.jpg");
					break;
				case "한식":
					int tempint = list.get(i).getIdrestaurants() % 10;
					for(int j=0; j<10; j++) {
						if(tempint == j) {
							list.get(i).setImage("resimg/korean"+tempint+".jpg");
							break;
						}
					}
					break;
				default:
					list.get(i).setImage("resimg/korean1.jpg");
				}
		}
		return list;
	}

	@Override
	public RestaurantsDto image(RestaurantsDto dto) {
		boolean check = false;
			String temp = dto.getRes_type();
			switch (temp) {
			case "감성주점":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/bar1.jpg");
				else
					dto.setImage("resimg/bar2.jpg");
				break;
			case "경양식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/dishes1.jpg");
				else
					dto.setImage("resimg/dishes2.jpg");
				break;
			case "과자점":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/bakery.jpg");
				else
					dto.setImage("resimg/bakery2.jpg");
				break;
			case "기타":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/etc.png");
				else
					dto.setImage("resimg/etc2.jpg");
				break;
			case "기타 휴게음식점":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/restingetc.jpg");
				else
					dto.setImage("resimg/restingetc2.jpg");
				break;
			case "김밥(도시락)":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/gimbap.jpg");
				else
					dto.setImage("resimg/gimbap2.png");
				break;
			case "카페":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/cafe.jpg");
				else
					dto.setImage("resimg/cafe2.jpg");
				break;
			case "다방":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/dabang.jpg");
				else
					dto.setImage("resimg/dabang2.jpg");
				break;
			case "라이브카페":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("esimg/livecafe.jpg");
				else
					dto.setImage("resimg/livecafe2.jpg");
				break;
			case "백화점":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/department.jpg");
				else
					dto.setImage("resimg/department2.jpg");
				break;
			case "분식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/bunsick.jpg");
				else
					dto.setImage("resimg/bunsick2.jpg");
				break;
			case "뷔페식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/buffet.jpg");
				else
					dto.setImage("resimg/buffet2.png");
				break;
			case "식육(숯불구이)":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/meat.jpg");
				else
					dto.setImage("resimg/meat2.jpeg");
				break;
			case "외국음식전문점(인도태국등)":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/india.jpg");
				else
					dto.setImage("resimg/india2.jpg");
				break;
			case "일반조리판매":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/common.jpg");
				else
					dto.setImage("resimg/common2.jpg");
				break;
			case "일식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/japanese.jpg");
				else
					dto.setImage("resimg/japanese2.png");
				break;
			case "전통찻집":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/teashop.jpg");
				else
					dto.setImage("resimg/teashop2.jpg");
				break;
			case "정종대포집소주방":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/soju.jpeg");
				else
					dto.setImage("resimg/soju2.jpeg");
				break;
			case "중국식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/china.jpg");
				else
					dto.setImage("resimg/china2.jpg");
				break;
			case "커피숍":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/coffee.jpg");
				else
					dto.setImage("resimg/coffee2.jpg");
				break;
			case "통닭(치킨)":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/chicken.jpg");
				else
					dto.setImage("resimg/chicken2.png");
				break;
			case "패스트푸드":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/fastfood.jpg");
				else
					dto.setImage("resimg/fastfood2.jpg");
				break;
			case "한식":
				if (dto.getIdrestaurants() % 2 == 0)
					check = true;
				else
					check = false;

				if (check)
					dto.setImage("resimg/korean.jpg");
				else
					dto.setImage("resimg/korean2.png");
				break;
			default:
				dto.setImage("resimg/korean.jpg");
			}
		return dto;
	}

	@Override
	public ReviewDto rankone(int no) {
		return storeDao.rankone(no);
	}
	
	@Override
	public List<RestaurantsDto> searchStore(String input) {
		return storeDao.searchStore(input);
	}


	@Override
	public List<RestaurantsDto> meter(List<RestaurantsDto> list) {
		
		for(int i=0; i<list.size(); i++) {
			double distanceMeter =
		            distance(37.512, 127.031, list.get(i).getLon(), list.get(i).getLat(), "meter");
			list.get(i).setMeter(Math.floor(distanceMeter));
			//System.err.println(distanceMeter);
		}
		Collections.sort(list, new Comparator<RestaurantsDto>() {
			@Override
			public int compare(RestaurantsDto o1, RestaurantsDto o2) {
				 long thisBits = Double.doubleToLongBits(o1.getMeter());
				 long anotherBits = Double.doubleToLongBits(o2.getMeter());// TODO Auto-generated method stub
				
				 return (thisBits == anotherBits ?  0 : // Values are equal
			            (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
			             1));  
			}
		
		});
		return list;
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
         
        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }
 
        return (dist);
    }
     
 
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }





}
