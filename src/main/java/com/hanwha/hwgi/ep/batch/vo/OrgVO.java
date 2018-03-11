package com.hanwha.hwgi.ep.batch.vo;

public class OrgVO {
	private String orgcd;
	private String orgnm;
	
	public OrgVO() {
		
	}
	
	public OrgVO(String orgcd, String orgnm) {
		this.orgcd = orgcd;
		this.orgnm = orgnm;
	}

	public String getOrgcd() {
		return orgcd;
	}

	public void setOrgcd(String orgcd) {
		this.orgcd = orgcd;
	}

	public String getOrgnm() {
		return orgnm;
	}

	public void setOrgnm(String orgnm) {
		this.orgnm = orgnm;
	}
	
	
}
