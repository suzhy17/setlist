package com.daou.setlist.common.result.xml;

import javax.xml.bind.annotation.XmlAttribute;

public class Result {

	private String resultCode = "";
	private String resultMessage = "";

	@XmlAttribute(name = "result_code")
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@XmlAttribute(name = "result_message")
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public Result() {
	}
	
	public Result(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		
	}
}
