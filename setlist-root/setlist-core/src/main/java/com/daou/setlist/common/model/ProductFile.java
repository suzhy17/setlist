package com.daou.setlist.common.model;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.daou.setlist.common.constant.Const;

/**
 * @author suzhy
 */
public class ProductFile {

	private Integer fileNo;
	private Integer fileKey;
	private Integer seqNo;
	private String originFileNm;
	private String savedFileNm;
	private String filePath;
	private String fileExt;
	private Long fileSize = 0L;
	private String useYn = "Y";
	private Integer regUserNo;
	private LocalDateTime regDt;
	private String sizeCode;
	private Integer width;
	private Integer height;
	
	/**
	 * 이미지의 절대경로로 filePath, savedFileNm, fileExt 셋팅
	 * <p>예>
	 * <br>입력
	 * <br> - fullImgPath=/home/FILE/PUBLIC/MTContent/ETC/20/20161208/1481155137443_7.png
	 * <br>출력
	 * <br> - filePath=/PUBLIC/MTContent/ETC/20/20161208
	 * <br> - savedFileNm=1481155137443_7.png
	 * <br> - fileExt=png
	 * @param fullImgPath
	 */
	public void setImgPath(String fullImgPath) {
		if (StringUtils.isBlank(fullImgPath)) {
			throw new IllegalArgumentException("파일 경로 파라미터가 누락되었습니다.");
		}
		
		// /home/FILES 제거 (없으면 무시)
		String rootPath = "";
		if (StringUtils.contains(fullImgPath, "/PRIVATE")) {
			rootPath = StringUtils.substringBefore(fullImgPath, "/PRIVATE");
		} else {
			rootPath = StringUtils.substringBefore(fullImgPath, "/PUBLIC");
		}
		fullImgPath = StringUtils.removeStart(fullImgPath, rootPath);
		
		this.filePath = StringUtils.substringBeforeLast(fullImgPath, "/");
		this.savedFileNm = StringUtils.substringAfterLast(fullImgPath, "/");
		this.fileExt = StringUtils.substringAfterLast(fullImgPath, ".");
	}
	
	/**
	 * 이미지 경로
	 * <p>filePath + "/" + savedFileNm
	 * <p>예> /PUBLIC/MTContent/ETC/20/20161208/1481155137443_7.png
	 * @return
	 */
	public String getImgPath() {
		if (StringUtils.isBlank(filePath) || StringUtils.isBlank(savedFileNm)) {
			throw new IllegalArgumentException("파일 경로가 설정되지 않았습니다.");
		}
		return filePath + "/" + savedFileNm;
	}
	
	/**
	 * 배경 이미지 여부
	 * @return
	 */
	public boolean isBgImg() {
		return this.seqNo.intValue() == Const.IMAGE_SIZE_CD.NORMAL.getIntValue() && StringUtils.equals(this.useYn, "Y");
	}
	
	/**
	 * 썸네이리 이미지 여부
	 * @return
	 */
	public boolean isThumbImg() {
		return this.seqNo.intValue() == Const.IMAGE_SIZE_CD.THUMBNAIL.getIntValue() && StringUtils.equals(this.useYn, "Y");
	}
	
	/**
	 * 추가이미지 여부
	 * @return
	 */
	public boolean isAddImg() {
		return StringUtils.equals(this.useYn, "N");
	}
	
	public void setImgSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getFileNo() {
		return fileNo;
	}

	public void setFileNo(Integer fileNo) {
		this.fileNo = fileNo;
	}

	public Integer getFileKey() {
		return fileKey;
	}

	public void setFileKey(Integer fileKey) {
		this.fileKey = fileKey;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getOriginFileNm() {
		return originFileNm;
	}

	public void setOriginFileNm(String originFileNm) {
		this.originFileNm = originFileNm;
	}

	public String getSavedFileNm() {
		return savedFileNm;
	}

	public void setSavedFileNm(String savedFileNm) {
		this.savedFileNm = savedFileNm;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
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
