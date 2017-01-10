package com.daou.setlist.common.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Search {
	private String searchType;
	private String searchKeyword;
	private String searchStDt;
	private String searchEnDt;
	
	public Search() {
	}
	
	/**
	 * 
	 * @param searchType 검색 타입
	 * @param searchKeyword 검색어
	 * @param searchStDt 검색 시작 일자
	 * @param searchEnDt 검색 끝 일자
	 */
	public Search(String searchType, String searchKeyword, String searchStDt, String searchEnDt) {
		this.searchType = searchType;
		this.searchKeyword = searchKeyword;
		this.searchStDt = searchStDt;
		this.searchEnDt = searchEnDt;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchStDt() {
		return searchStDt;
	}

	public void setSearchStDt(String searchStDt) {
		this.searchStDt = searchStDt;
	}

	public String getSearchEnDt() {
		return searchEnDt;
	}

	public void setSearchEnDt(String searchEnDt) {
		this.searchEnDt = searchEnDt;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
