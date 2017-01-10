package com.daou.setlist.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.daou.setlist.common.exception.EmsException;
import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.common.exception.EmsXmlException;
import com.daou.setlist.common.model.JsonResult;
import com.daou.setlist.common.model.XmlResult;
import com.daou.setlist.common.result.xml.Result;

@ControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

//	private static final String UNKOWN_ERROR_MSG = "알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.";

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class, RuntimeException.class })
	public String exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {

		log.error("서버 에러 발생", exception);

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		return "error";
	}
	
	@ResponseBody
	@ExceptionHandler(EmsJsonException.class)
	public JsonResult emsJsonExceptionHandler(EmsJsonException exception, HttpServletRequest request, HttpServletResponse response) {

		log.warn(exception.toString());

		return new JsonResult(JsonResult.FAIL, exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(EmsXmlException.class)
	public XmlResult emsXmlExceptionHandler(EmsXmlException exception, HttpServletRequest request, HttpServletResponse response) {

		log.warn(exception.toString());

		return new XmlResult(new Result(exception.getCode(), exception.getMessage()));
	}
	
	@ExceptionHandler(EmsException.class)
	public String emsExceptionHandler(EmsException exception, HttpServletRequest request, HttpServletResponse response) {

		log.warn(exception.toString());
		request.setAttribute("result", new JsonResult(JsonResult.FAIL, exception.getMessage()));

		return "error";
	}
}
