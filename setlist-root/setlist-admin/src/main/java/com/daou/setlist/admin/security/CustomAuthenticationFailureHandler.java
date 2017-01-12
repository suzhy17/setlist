package com.daou.setlist.admin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.daou.setlist.admin.domain.AdminUser;
import com.daou.setlist.admin.service.AdminUserService;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

	@Autowired
	private AdminUserService adminUserService;

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception)
			throws IOException, ServletException {
		
		String userId = request.getParameter("userId");

		AdminUser adminUser = adminUserService.loadUserByUsername(userId);
		if (adminUser != null && adminUser.getLoginFailCnt() < 5) {
			adminUser.setLoginFailCnt(adminUser.getLoginFailCnt()+1);
			adminUserService.updateUser(adminUser);
			log.debug("로그인 실패 건수 업데이트 [id={}, loginFailCnt={}]", userId, adminUser.getLoginFailCnt());
		}
		
		// 방문 기록 : 로그인 실패
		// LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), request.getParameter("id"), request.getRequestURI(), "N", "");

		// 에러 메시지 저장
		request.setAttribute("errMsg", exception.getMessage());
		request.getRequestDispatcher("/login").forward(request, response);
	}
}
