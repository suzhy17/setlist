package com.daou.setlist.admin.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.daou.setlist.common.util.LogUtil;
import com.daou.setlist.common.util.OtpUtil;
import com.daou.setlist.admin.domain.AdminUser;
import com.daou.setlist.admin.service.AdminUserService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

	@Autowired
	private AdminUserService adminUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		AdminUser adminUser = (AdminUser) authentication.getPrincipal();
		
		//---------------------------
		// OTP 인증키 생성
		//---------------------------
		// OTP 인증키가 없을 경우만 해당
		// 인증키 생성, 저장 후 QR코드 URL 리턴
		if (StringUtils.isBlank(adminUser.getEncodedKey())) {
			String newEncodedKey = OtpUtil.createEncodedKey();

			String hostUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			log.debug("hostUrl={}", hostUrl);

			String QRUrl = OtpUtil.getQRBarcodeURL(adminUser.getUsername(), newEncodedKey, hostUrl);
			log.debug("QRUrl={}", QRUrl);
			
			request.setAttribute("QRUrl", QRUrl);
			
			// OTP 인증키 저장
			adminUser.setEncodedKey(newEncodedKey);
			adminUserService.updateUser(adminUser);

			LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), authentication.getName(), request.getRequestURI(), "N", "");
			SecurityContextHolder.clearContext();

			// 에러 메시지 저장
			request.setAttribute("errMsg", "QR코드로 OTP생성 후 진행하세요.");
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		
		//---------------------------
		// OTP 인증코드 유효성 검사
		//---------------------------

		String otpNumber = request.getParameter("otpNumber");

		if (StringUtils.length(otpNumber) == 0 || !StringUtils.isNumeric(otpNumber)) {
			LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), authentication.getName(), request.getRequestURI(), "N", "");
			SecurityContextHolder.clearContext();

			// 에러 메시지 저장
			request.setAttribute("errMsg", "OTP인증코드를 입력하세요.");
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		
		//---------------------------
		// 입력된 OTP 인증코드 검증
		//---------------------------
		
		boolean isLoginOtp = OtpUtil.loginOtp(adminUser.getUsername(), adminUser.getEncodedKey(), Integer.parseInt(otpNumber), 20);

		if (!isLoginOtp) {
			LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), authentication.getName(), request.getRequestURI(), "N", "");
			SecurityContextHolder.clearContext();

			// 에러 메시지 저장
			request.setAttribute("errMsg", "OTP인증코드가 일치하지 않습니다.");
			request.getRequestDispatcher("/login").forward(request, response);
			return;
		}

		//---------------------------
		// 성공시 처리
		//---------------------------
		adminUser.setLastLoginDt(LocalDateTime.now());
		adminUser.setLoginFailCnt(0);
		adminUserService.updateUser(adminUser);

		// 어드민 접근 이력
		LogUtil.adminLog("/weblog/AdminLog/", "admin", "access", request.getRemoteAddr(), authentication.getName(), request.getRequestURI(), "Y", "");

		// 메인 페이지로 이동
		response.sendRedirect("/");
	}

}
