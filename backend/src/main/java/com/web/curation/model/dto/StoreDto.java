package com.web.curation.model.dto;

public class StoreDto {
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
	private int id;
	private int code;
	private String storename;
	private String dolo;
	private String jibun;
	private String store_number;
	private String type;
	private int typeint;
	private String menu;
	private String deal_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getDolo() {
		return dolo;
	}
	public void setDolo(String dolo) {
		this.dolo = dolo;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getStore_number() {
		return store_number;
	}
	public void setStore_number(String store_number) {
		this.store_number = store_number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTypeint() {
		return typeint;
	}
	public void setTypeint(int typeint) {
		this.typeint = typeint;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getDeal_date() {
		return deal_date;
	}
	public void setDeal_date(String deal_date) {
		this.deal_date = deal_date;
	}
	@Override
	public String toString() {
		return "StoreDto [id=" + id + ", code=" + code + ", storename=" + storename + ", dolo=" + dolo + ", jibun="
				+ jibun + ", store_number=" + store_number + ", type=" + type + ", typeint=" + typeint + ", menu="
				+ menu + ", deal_date=" + deal_date + "]";
	}
	public StoreDto(int id, int code, String storename, String dolo, String jibun, String store_number, String type) {
		super();
		this.id = id;
		this.code = code;
		this.storename = storename;
		this.dolo = dolo;
		this.jibun = jibun;
		this.store_number = store_number;
		this.type = type;

	}
	
	public StoreDto(int id, int code, String storename, String dolo, String jibun, String store_number, String type,
			int typeint, String menu, String deal_date) {
		super();
		this.id = id;
		this.code = code;
		this.storename = storename;
		this.dolo = dolo;
		this.jibun = jibun;
		this.store_number = store_number;
		this.type = type;
		this.typeint = typeint;
		this.menu = menu;
		this.deal_date = deal_date;
	}
	public StoreDto() {
		super();
	}
	
	
	
}
