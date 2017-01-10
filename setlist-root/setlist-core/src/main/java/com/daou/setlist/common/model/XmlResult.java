package com.daou.setlist.common.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.daou.setlist.common.result.xml.Result;

@XmlRootElement(name = "root")
public class XmlResult {

	private Result result;

	public XmlResult() {
	}
	
	public XmlResult(Result result) {
		this.result = result;
	}

	@XmlElement(name = "result")
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	
}
