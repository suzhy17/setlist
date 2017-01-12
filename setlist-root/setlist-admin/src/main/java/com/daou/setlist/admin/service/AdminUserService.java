package com.daou.setlist.admin.service;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.common.util.CommonUtil;
import com.daou.setlist.admin.domain.AdminUser;
import com.daou.setlist.admin.repository.AdminUserRepository;

@Service
public class AdminUserService implements UserDetailsManager {
	
	private static final Logger log = LoggerFactory.getLogger(AdminUserService.class);

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Autowired(required = false)
	private SaltSource saltSource;

	@Autowired(required = false)
	private ShaPasswordEncoder passwordEncoder;

	private static final char[] codes = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '`', '~', '!', '@', '#', '$', '%', '^',
			'&', '*', '(', ')', '-', '_', '=', '+', '\\', '|', '[', ']', '{',
			'}', ';', ':', '\'', '"', ',', '.', '<', '>', '/', '?', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AdminUser loadUserByUsername(String userName) throws UsernameNotFoundException {
		return Stream
				.of(adminUserRepository.findOne(userName))
				.filter(user -> user != null)
				.peek(user -> user.setAuthorities(AuthorityUtils.createAuthorityList(user.getAuth().split(","))))
				.findFirst().orElse(null);
	}

	@Override
	@Transactional
	public void changePassword(String oldPassword, String newPassword) {

		if (StringUtils.isBlank(oldPassword)) {
			throw new EmsJsonException("기존비밀번호가 없습니다.");
		}

		if (StringUtils.isBlank(newPassword)) {
			throw new EmsJsonException("새 비밀번호가 없습니다.");
		}

		if (StringUtils.equals(oldPassword, newPassword)) {
			throw new EmsJsonException("기존 비밀번호와 동일합니다.");
		}

		if (!CommonUtil.isComplexPassword(newPassword)) {
			throw new EmsJsonException("비밀번호는 숫자/영문자 조합으로 10자리 이상으로만 사용 가능합니다.");
		}

		if (!CommonUtil.isStrengthPassword(newPassword)) {
			throw new EmsJsonException("비밀번호는 동일한 숫자/영문자, 연속된 숫자/영문자를 3개 초과하여 사용할 수 없습니다.");
		}

		AdminUser adminUser = this.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String encodeOldPassword = passwordEncoder.encodePassword(oldPassword, saltSource.getSalt(adminUser));

		if (!StringUtils.equals(adminUser.getPassword(), encodeOldPassword)) {
			throw new EmsJsonException("비밀번호가 틀렸습니다.");
		}

		adminUser.setSalt(RandomStringUtils.random(45, codes));
		adminUser.setPassword(passwordEncoder.encodePassword(newPassword, saltSource.getSalt(adminUser)));
		adminUser.setPwdTmpYn("N");
		adminUser.setPwdModDt(LocalDateTime.now());

		this.updateUser(adminUser);
		SecurityContextHolder.clearContext();
	}

	@Override
	@Transactional
	public void createUser(UserDetails userDetails) {

		String tempPassword = RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomNumeric(9);

		AdminUser delpAdminUser = (AdminUser) userDetails;
		delpAdminUser.setAuth("ROLE_USER");
		delpAdminUser.setSalt(RandomStringUtils.random(45, codes));
		delpAdminUser.setPassword(passwordEncoder.encodePassword(tempPassword, saltSource.getSalt(delpAdminUser)));
		delpAdminUser.setPwdTmpYn("Y");
		delpAdminUser.setLoginFailCnt(0);
		delpAdminUser.setPwdModDt(LocalDateTime.now());

		adminUserRepository.save(delpAdminUser);

		// 임시 비밀번호 SMS 전송
//		tblSubmitQueueRepository.save(new TblSubmitQueue(
//				"SMS",
//				delpAdminUser.getPhone(),
//				"15882658",
//				"콜믹스 통합관리 임시 패스워드 발급",
//				"콜믹스 통합관리 임시 패스워드가 발급되었습니다. [" + tempPassword + "]"
//				));
	}

	@Override
	public void deleteUser(String id) {
		adminUserRepository.delete(id);
	}

	@Override
	public void updateUser(UserDetails userDetails) {
		adminUserRepository.save((AdminUser) userDetails);
	}
	
	public void updateUser(String id, String mobileNo) {
		AdminUser delpAdminUser = this.loadUserByUsername(id);
		delpAdminUser.setMobileNo(mobileNo);
		adminUserRepository.save(delpAdminUser);
	}
	
	/**
	 * 임시 패스워드 발급
	 * @param id
	 */
	public void tempPassword(String id) {
		String tempPassword = RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomNumeric(9);

		AdminUser adminUser = this.loadUserByUsername(id);
		adminUser.setSalt(RandomStringUtils.random(45, codes));
		adminUser.setPassword(passwordEncoder.encodePassword(tempPassword, saltSource.getSalt(adminUser)));
		adminUser.setPwdTmpYn("Y");
		adminUser.setPwdModDt(LocalDateTime.now());
		adminUser.setLastLoginDt(LocalDateTime.now());
		adminUser.setLoginFailCnt(0);

		adminUserRepository.save(adminUser);
		
		String msg = "이모티콘 통합관리 임시 패스워드가 발급되었습니다. [" + tempPassword + "]";
		log.debug(msg);

		// 임시 비밀번호 SMS 전송
//		tblSubmitQueueRepository.save(new TblSubmitQueue(
//				"SMS",
//				delpAdminUser.getPhone(),
//				"15882658",
//				"콜믹스 통합관리 임시 패스워드 발급",
//				"콜믹스 통합관리 임시 패스워드가 발급되었습니다. [" + tempPassword + "]"
//				));
	}
	
	/**
	 * OTP 초기화
	 * @param id
	 */
	public void otpReset(String id) {
		AdminUser adminUser = this.loadUserByUsername(id);
		adminUser.setEncodedKey("");
		adminUserRepository.save(adminUser);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean userExists(String userName) {
		return adminUserRepository.exists(userName);
	}

	/**
	 * 모든 관리자 리스트
	 * @param pageable
	 * @return
	 */
	public Page<AdminUser> getAdminUser(Pageable pageable) {
		return adminUserRepository.findAll(pageable);
	}

}
