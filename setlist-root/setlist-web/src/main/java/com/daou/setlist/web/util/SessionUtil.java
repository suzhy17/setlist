package com.daou.setlist.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.daou.setlist.web.model.LoginSessionDto;

public class SessionUtil {
	
	private static final String ASP_MEMBER_SESSION = "__ASP_MEMBER_SESSION";

	/***
	 * 로그인 세션 가져오기
	 * @param request
	 * @return
	 */
	public static LoginSessionDto getLoginSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		return (LoginSessionDto) session.getAttribute(ASP_MEMBER_SESSION);
	}

	/**
	 * 로그인 세션 생성
	 * @param request
	 * @param sName
	 * @param sValue
	 */
	public static void setLoginSession(HttpServletRequest request, LoginSessionDto sessionDto) {
		HttpSession session = request.getSession(true);
		session.setAttribute(ASP_MEMBER_SESSION, sessionDto);
	}

	/**
	 * 세션 삭제
	 * @param request
	 */
	public static void deleteSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
	}

}
