package com.daou.setlist.common.model;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 포토 이모티콘 베스트
 * 
 * @author suzhy
 */
public class ProductBest {

	private Integer bestNo;
	private Integer productNo;
	private LocalDateTime bestDt;
	private String categoryTypeCd;
	private LocalDateTime regDt;

	public Integer getBestNo() {
		return bestNo;
	}

	public void setBestNo(Integer bestNo) {
		this.bestNo = bestNo;
	}

	public Integer getProductNo() {
		return productNo;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}

	public LocalDateTime getBestDt() {
		return bestDt;
	}

	public void setBestDt(LocalDateTime bestDt) {
		this.bestDt = bestDt;
	}

	public String getCategoryTypeCd() {
		return categoryTypeCd;
	}

	public void setCategoryTypeCd(String categoryTypeCd) {
		this.categoryTypeCd = categoryTypeCd;
	}

	public LocalDateTime getRegDt() {
		return regDt;
	}

	public void setRegDt(LocalDateTime regDt) {
		this.regDt = regDt;
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
