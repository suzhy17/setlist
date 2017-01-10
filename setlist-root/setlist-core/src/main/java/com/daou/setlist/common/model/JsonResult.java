package com.daou.setlist.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResult {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	
	private Object resObj;
	private String resCd;
	private String resMsg;

	public String getResCd() {
		return resCd;
	}

	public void setResCd(String resCd) {
		this.resCd = resCd;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public Object getResObj() {
		return resObj;
	}

	public void setResObj(Object resObj) {
		this.resObj = resObj;
	}
	
	public JsonResult() {
		this.setResCd(SUCCESS);
		this.setResMsg("정상처리되었습니다.");
	}

	public JsonResult(String resMsg) {
		this.setResCd(SUCCESS);
		this.setResMsg(resMsg);
	}

	public JsonResult(Object resObj) {
		this.setResCd(SUCCESS);
		this.setResMsg("정상처리되었습니다.");
		this.setResObj(resObj);
	}

	public JsonResult(String resCd, String resMsg) {
		this.setResCd(resCd);
		this.setResMsg(resMsg);
	}

	public JsonResult(String resCd, String resMsg, Object resObj) {
		this.setResCd(resCd);
		this.setResMsg(resMsg);
		this.setResObj(resObj);
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return null;
		}
		return json;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
