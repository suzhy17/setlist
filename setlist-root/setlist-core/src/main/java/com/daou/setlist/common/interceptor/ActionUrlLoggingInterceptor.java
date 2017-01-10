package com.daou.setlist.common.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ActionUrlLoggingInterceptor extends HandlerInterceptorAdapter{

	private static final Log logger = LogFactory.getLog (ActionUrlLoggingInterceptor.class);
	
	// 로그로 남기면 안되는 파라미터 값의 이름들에 포함된 단어
	private static final String[] pwWords = new String[] { "pwd", "password", "passwd" };
	
	private String actionName;
	private long begin;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//-----------------------------
		// 로그 식별 값 셋팅
		//-----------------------------
		// 로그 분석을 쉽게 하기 위해 MDC를 통해 요청IP를 36진수로 변환한 값을 출력한다.

		String remoteAddr = request.getRemoteAddr();
		remoteAddr = StringUtils.remove(request.getRemoteAddr(), ".");
		String identity = "LOCAL";
		if (StringUtils.isNumeric(remoteAddr)) {
			identity = Long.toString(Long.parseLong(remoteAddr), 36);
			identity = StringUtils.substring(identity, -4);
		}
		identity = StringUtils.upperCase(identity);
		
		actionName = StringUtils.substringBeforeLast(StringUtils.substringAfterLast(request.getRequestURL().toString(), "/"), ".");
		
		MDC.put("identity", identity+":"+ actionName);
		
		actionName = StringUtils.substringAfterLast(StringUtils.substringBeforeLast(request.getRequestURL().toString(), "/"), "/") + "/" + actionName;
		
		//-----------------------------
		// Action URL 기록
		//-----------------------------
		
		List<String> paramList = new ArrayList<String>();

		StringBuffer brParams = new StringBuffer();
		
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String nm = paramNames.nextElement();
			String value = "";

			// 패스워드는 별표(*) 처리
			String lowerNm = StringUtils.lowerCase(nm);
			if (StringUtils.indexOfAny(lowerNm, pwWords) > -1) {
				value = "****";
			} else {
				value = request.getParameter(nm);
			}

			brParams
				.append(nm)
				.append("=")
				.append(value)
				.append("&");

			paramList.add(StringUtils.rightPad(nm, 20, " ") + " | " + StringUtils.abbreviate(value, 1000));
		}
		
		String requestUrl = request.getRequestURL()+"?"+brParams.toString();
		MDC.put("requestUrl", requestUrl);

		logger.info("=============== Action URL ================");
		logger.info("[" + request.getRemoteAddr() +"] " + request.getMethod() + " " + StringUtils.abbreviate(requestUrl, 1000));
		if (logger.isDebugEnabled()) {
			logger.debug("-------------------------------------------");
			for (String param : paramList) {
				logger.debug(" " + param);
			}
		}
		logger.info("===========================================");

		
		//-----------------------------
		// 수행시간 기록
		//-----------------------------

		StringBuffer beginLog = new StringBuffer();
		beginLog.append("*** ").append(actionName).append(" 처리시작");
		logger.info(beginLog);

		// 액션 수행 시작 시각
		begin = System.currentTimeMillis();
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {

		logger.info("============== Action Result ==============");
		
		// 액션 수행 시간
		long runningTime = System.currentTimeMillis() - begin;

		StringBuffer endLog = new StringBuffer();
		endLog.append("*** ").append(actionName).append(" 수행시간 : ").append(runningTime).append(" ms.");
		logger.info(endLog);
		
		MDC.remove("identity");
		MDC.remove("returnUrl");
	}
}
