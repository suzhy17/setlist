package com.daou.setlist.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.daou.setlist.common.exception.EmsException;
import com.daou.setlist.common.exception.EmsXmlException;
import com.daou.setlist.web.annotation.Authentication;
import com.daou.setlist.web.annotation.AuthenticationType;
import com.daou.setlist.web.model.LoginSessionDto;
import com.daou.setlist.web.service.AuthService;
import com.daou.setlist.web.util.SessionUtil;

/**
 * AdminLog Annotation으로 접근이력 로깅
 * 
 * @author suzhy
 *
 */
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(AuthCheckInterceptor.class);

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		log.debug("--- 인증 체크 ---");
		
		Authentication auth = ((HandlerMethod) handler).getMethodAnnotation(Authentication.class);

		if (auth != null) {
			if (auth.value() == AuthenticationType.PRODUCT) {
				LoginSessionDto loginSession = authService.login(request, response);
				log.info("로그인 세션 [loginSession={}]", loginSession);
			}
			else if (auth.value() == AuthenticationType.PHOTO_EDTIOR) {

				LoginSessionDto loginSession = SessionUtil.getLoginSession(request);
				log.info("로그인 세션 [loginSession={}]", loginSession);

				// 기존 세션이 있으면 인증 성공
				// 세션이 없을 경우 companyNo, userId, h로 로그인 인증을 한다.
				if (loginSession == null) {
					try {
						loginSession = authService.login(request, response);
					} catch (EmsException e) {
						log.warn("인증 실패 [세션 없음]");
						throw new EmsXmlException("01", "인증에 실패하였습니다.\n다시 시도해 주세요."); 
					}

					if (loginSession == null) {
						log.warn("인증 실패 [세션 없음]");
						throw new EmsXmlException("01", "인증에 실패하였습니다.\n다시 시도해 주세요."); 
					}
				}
			}
			else if (auth.value() == AuthenticationType.PHOTO_EDTIOR_POPUP) {

				LoginSessionDto loginSession = SessionUtil.getLoginSession(request);
				log.info("로그인 세션 [loginSession={}]", loginSession);

				// 기존 세션이 있으면 인증 성공
				// 세션이 없을 경우 companyNo, userId, h로 로그인 인증을 한다.
				if (loginSession == null) {
					loginSession = authService.login(request, response);

					if (loginSession == null) {
						log.warn("인증 실패 [세션 없음]");
						throw new EmsException("인증에 실패하였습니다. 팝업을 닫고 새로고침(F5) 후에 다시 시도해 주세요."); 
					}
				}
			}
			else if (auth.value() == AuthenticationType.API) {
				authService.checkAllowIp(request);
			}
		}
		
		log.info("인증 패스");

		return true;
	}
}
