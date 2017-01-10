package com.daou.setlist.common.model;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 포토 이모티콘
 * 
 * @author suzhy
 */
public class Product {

	private Integer productNo;
	private Integer originProductNo = 0;
	private Integer fileKey;
	private String categoryCd;
	private String categoryNm;
	private String productTypeCd;
	private String makerCd;
	private String sendContentTypeCd;
	private String contentNm;
	private String templateSource;
	private String useYn;
	private String saleYn;
	private String publicYn;
	private String saveYn;
	private Integer sendCnt = 0;
	private Integer totSendCnt = 0;
	private Integer readCnt = 0;
	private Integer regUserNo;
	private LocalDateTime regDt;
	private Integer modUserNo;
	private LocalDateTime modDt;

	// 파일 정보
	private String imgPath;
	private String thumbImgPath;

	// 베스트 정보
	private Integer bestNo;
	private String bestStat;
	private LocalDateTime bestDt;
	
	// 태그 정보
	private String tagNm;

	public Integer getProductNo() {
		return productNo;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}

	public Integer getOriginProductNo() {
		return originProductNo;
	}

	public void setOriginProductNo(Integer originProductNo) {
		this.originProductNo = originProductNo;
	}

	public Integer getFileKey() {
		return fileKey;
	}

	public void setFileKey(Integer fileKey) {
		this.fileKey = fileKey;
	}

	public String getCategoryCd() {
		return categoryCd;
	}

	public void setCategoryCd(String categoryCd) {
		this.categoryCd = categoryCd;
	}

	public String getProductTypeCd() {
		return productTypeCd;
	}

	public void setProductTypeCd(String productTypeCd) {
		this.productTypeCd = productTypeCd;
	}

	public String getMakerCd() {
		return makerCd;
	}

	public void setMakerCd(String makerCd) {
		this.makerCd = makerCd;
	}

	public String getSendContentTypeCd() {
		return sendContentTypeCd;
	}

	public void setSendContentTypeCd(String sendContentTypeCd) {
		this.sendContentTypeCd = sendContentTypeCd;
	}

	public String getContentNm() {
		return contentNm;
	}

	public void setContentNm(String contentNm) {
		this.contentNm = contentNm;
	}

	public String getTemplateSource() {
		return templateSource;
	}

	public void setTemplateSource(String templateSource) {
		this.templateSource = templateSource;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getSaleYn() {
		return saleYn;
	}

	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}

	public String getPublicYn() {
		return publicYn;
	}

	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}

	public String getSaveYn() {
		return saveYn;
	}

	public void setSaveYn(String saveYn) {
		this.saveYn = saveYn;
	}

	public Integer getSendCnt() {
		return sendCnt;
	}

	public void setSendCnt(Integer sendCnt) {
		this.sendCnt = sendCnt;
	}

	public Integer getTotSendCnt() {
		return totSendCnt;
	}

	public void setTotSendCnt(Integer totSendCnt) {
		this.totSendCnt = totSendCnt;
	}

	public Integer getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public Integer getRegUserNo() {
		return regUserNo;
	}

	public void setRegUserNo(Integer regUserNo) {
		this.regUserNo = regUserNo;
	}

	public LocalDateTime getRegDt() {
		return regDt;
	}

	public void setRegDt(LocalDateTime regDt) {
		this.regDt = regDt;
	}

	public Integer getModUserNo() {
		return modUserNo;
	}

	public void setModUserNo(Integer modUserNo) {
		this.modUserNo = modUserNo;
	}

	public LocalDateTime getModDt() {
		return modDt;
	}

	public void setModDt(LocalDateTime modDt) {
		this.modDt = modDt;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getThumbImgPath() {
		return thumbImgPath;
	}

	public void setThumbImgPath(String thumbImgPath) {
		this.thumbImgPath = thumbImgPath;
	}

	public Integer getBestNo() {
		return bestNo;
	}

	public void setBestNo(Integer bestNo) {
		this.bestNo = bestNo;
	}

	public String getBestStat() {
		return bestStat;
	}

	public void setBestStat(String bestStat) {
		this.bestStat = bestStat;
	}

	public LocalDateTime getBestDt() {
		return bestDt;
	}

	public void setBestDt(LocalDateTime bestDt) {
		this.bestDt = bestDt;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	
	public String getTagNm() {
		return tagNm;
	}

	public void setTagNm(String tagNm) {
		this.tagNm = tagNm;
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
