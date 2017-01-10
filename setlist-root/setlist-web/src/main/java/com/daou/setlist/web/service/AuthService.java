package com.daou.setlist.web.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.daou.setlist.common.dao.CommDao;
import com.daou.setlist.common.exception.EmsException;
import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.common.model.Company;
import com.daou.setlist.common.model.User;
import com.daou.setlist.common.util.SecureUtil;
import com.daou.setlist.web.model.LoginSessionDto;
import com.daou.setlist.web.util.SessionUtil;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private Environment env;

	@Autowired
	private CommDao commDao;
	
	/**
	 * 고객사 정보 조회
	 * @param companyNo
	 * @return
	 */
	public Company selectCompanyByCompanyNo(Integer companyNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("companyNo", companyNo);
		return commDao.selectOne("AUTH.selectCompany", params);
	}
	
	/**
	 * 고객사 정보 조회
	 * @param companyId
	 * @return
	 */
	public Company selectCompanyByCompanyId(String companyId) {
		Map<String, Object> params = new HashMap<>();
		params.put("companyId", companyId);
		return commDao.selectOne("AUTH.selectCompany", params);
	}
	
	/**
	 * 사용자 조회
	 * @param companyNo
	 * @param userId
	 * @return
	 */
	public User selectUser(Integer companyNo, String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("companyNo", companyNo);
		params.put("userId", userId);
		return commDao.selectOne("AUTH.selectUser", params);
	}
	
	/**
	 * 사용자 등록
	 * @param user
	 */
	public void insertUser(User user) {
		commDao.insert("AUTH.insertUser", user);
	}
	
	/**
	 * 사용자의 마지막 로그인 일시 업데이트
	 * @param user
	 */
	public void updateUserForLastLoginDt(User user) {
		commDao.update("AUTH.updateUserForLastLoginDt", user);
	}
	
	/**
	 * 로그인 체크
	 * @param request
	 * @param response
	 * @return
	 */
	public LoginSessionDto login(HttpServletRequest request, HttpServletResponse response) {	  
	  	// 도메인이 다를 경우에 세션유지를 위해 필요 (IE)
		response.addHeader("p3p", "CP=NOI CURa ADMa DEVa TAIa OUR DELa BUS IND PHY ONL UNI COM NAV INT DEM PRE");
		
		//-------------------------------------
		// 인증 파라미터
		//-------------------------------------

		String authKey = request.getParameter("h");
		Integer companyNo = StringUtils.isNotBlank(request.getParameter("companyNo")) ? Integer.parseInt(request.getParameter("companyNo")) : 0;
		String userId = request.getParameter("userId");

		if (StringUtils.isBlank(authKey) || companyNo == 0 || StringUtils.isBlank(userId)) {
			log.info("인증 실패 [파라미터 누락]");
			throw new EmsException("인증에 실패하였습니다.");
		}
		
		
		//-------------------------------------
		// 현재 세션 체크
		//-------------------------------------

		// 현재 세션 정보 가져오기
		LoginSessionDto loginSession = SessionUtil.getLoginSession(request);
		log.debug("loginSession={}", loginSession);

		// 기존 세션이 유효하면 인증 성공
		if (loginSession != null) {

			if (loginSession.getCompanyNo().equals(companyNo)
				&& StringUtils.equals(loginSession.getUserId(), userId)) {
			
				log.info("인증 완료 [기존 세션이 유효]");
				
				return loginSession;
			}
		}
		
		// 세션이 일치하지 않으면 기존 세션 삭제
		log.info("기존 세션 삭제");
		SessionUtil.deleteSession(request);

		
		//-------------------------------------
		// 고객사 암호화 키 체크
		//-------------------------------------
		
		Company company = this.selectCompanyByCompanyNo(companyNo);
		if (company == null) {
			log.info("인증 실패 [고객사 정보가 없음]");
			throw new EmsException("인증에 실패하였습니다.");
		}

		log.debug("authKey={}", company.getAuthKey());

		String validCode = SecureUtil.encodeMD5(company.getCompanyId()+userId, company.getAuthKey());
		log.debug("input code={}", authKey);
		log.debug("valid code={}", validCode);
		
		if (!StringUtils.equals(authKey, validCode)) {
			log.info("인증 실패 [인증키 불일치]");
			throw new EmsException("인증에 실패하였습니다.");
		}
		
		
		//-------------------------------------
		// 고객사 암호화 키 체크
		//-------------------------------------
		// User 존재시 최종 로그인 일시 업데이트
		// User 미존재시 신규 등록
		
		User user = this.selectUser(companyNo, userId);
		if (user == null) {
			user = new User();
			user.setCompanyNo(companyNo);
			user.setUserId(userId);

			log.info("사용자 등록 [companyNo={}, userId={}]", companyNo, userId);
			this.insertUser(user);
		} else {
			log.info("사용자 업데이트 [companyNo={}, userId={}]", companyNo, userId);
			this.updateUserForLastLoginDt(user);
		}

		
		//-------------------------------------
		// 신규 세션 생성
		//-------------------------------------

		log.info("인증 완료 [신규 세션 생성]");

		LoginSessionDto newSessionDto = new LoginSessionDto();
		newSessionDto.setCompanyNo(user.getCompanyNo());
		newSessionDto.setCompanyId(company.getCompanyId());
		newSessionDto.setUserId(user.getUserId());
		newSessionDto.setUserNo(user.getUserNo());
		newSessionDto.setAuthKey(authKey);
		newSessionDto.setContentsSession(request.getSession().toString()); // 컨텐츠세션

		SessionUtil.setLoginSession(request, newSessionDto);
		
		return newSessionDto;
	}
	
	/**
	 * 허용IP 체크
	 * @param request
	 * @return
	 */
	public void checkAllowIp(HttpServletRequest request) {
		
		// 인증없이 허용된 IP 체크
		String remoteAddr = request.getRemoteAddr();
		log.debug("remoteAddr={}", remoteAddr);

		String apiClientIp = env.getProperty("API_CLIENT_IP");
		String[] bypasIps = StringUtils.split(apiClientIp, ",");
		log.debug("apiClientIp={}", apiClientIp);

		// 허용된 IP가 아니면 인증 실패
		if (StringUtils.indexOfAny(remoteAddr, bypasIps) < 0) {
			log.info("인증 실패 [허용IP 아님]");
			throw new EmsJsonException("인증에 실패하였습니다.");
		}
	}
}
