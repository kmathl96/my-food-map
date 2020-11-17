package com.web.curation.model.dto;

public class RestaurantsDto {
	/**
	 id : 고유 번호
	 code : 법정동 코드
	 storename : 가게 이름
	 dolo : 도로명 주소
	 jibun : 지번 주소
	 tyep : 업종 태
	 typeint : 업종 태 숫자로 나눌떄 쓰려고
	 menu : 메뉴 아직 미완
	 deal_date : 영업 시간 아직 미완
	   
	   */
	private int idrestaurants;
	private String res_type;
	private String menu;
	private String store_number;
	private String jibun;
	private String name;
	private int code;
	private String time;
	private String doro;
	private float grade;
	private int countgrade;
	private int typeint;
	private String image;
	private float lat;
	private float lon;
	private double Meter;
	


	public double getMeter() {
		return Meter;
	}
	public void setMeter(double meter) {
		Meter = meter;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public int getIdrestaurants() {
		return idrestaurants;
	}
	public void setIdrestaurants(int idrestaurants) {
		this.idrestaurants = idrestaurants;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getStore_number() {
		return store_number;
	}
	public void setStore_number(String store_number) {
		this.store_number = store_number;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDoro() {
		return doro;
	}
	public void setDoro(String doro) {
		this.doro = doro;
	}
	public float getgrade() {
		return grade;
	}
	public void setgrade(float grade) {
		this.grade = grade;
	}
	public int getCountgrade() {
		return countgrade;
	}
	public void setCountgrade(int countgrade) {
		this.countgrade = countgrade;
	}
	public int getTypeint() {
		return typeint;
	}
	public void setTypeint(int typeint) {
		this.typeint = typeint;
	}

	
	@Override
	public String toString() {
		return "RestaurantsDto [idrestaurants=" + idrestaurants + ", res_type=" + res_type + ", menu=" + menu
				+ ", store_number=" + store_number + ", jibun=" + jibun + ", name=" + name + ", code=" + code
				+ ", time=" + time + ", doro=" + doro + ", grade=" + grade + ", countgrade=" + countgrade + ", typeint="
				+ typeint + ", img=" + image + ", lat=" + lat + ", lon=" + lon + "]"+ "meter:" +Meter;
	}
	public RestaurantsDto(int idrestaurants, String res_type, String menu, String store_number, String jibun,
			String name, int code, String time, String doro, float grade, int countgrade, int typeint) {
		super();
		this.idrestaurants = idrestaurants;
		this.res_type = res_type;
		this.menu = menu;
		this.store_number = store_number;
		this.jibun = jibun;
		this.name = name;
		this.code = code;
		this.time = time;
		this.doro = doro;
		this.grade = grade;
		this.countgrade = countgrade;
		this.typeint = typeint;
	}
	public RestaurantsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
