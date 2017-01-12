package com.daou.setlist.admin.domain;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author suzhy
 *
 */
@Entity
@Table(name = "EMS_ADMIN_USER")
public class AdminUser implements UserDetails {

	private static final long serialVersionUID = 4357431598832811388L;

	@Id
	@Column(length = 20)
	private String userId;
	
	@Column(length = 128)
	private String password;
	
	@Column(length = 20, nullable = false)
	private String userNm;
	
	@Column(length = 50)
	private String auth;

	@Column(length = 45)
	private String salt;

	@Column(length = 16)
	private String encodedKey;

	@Column
	private LocalDateTime lastLoginDt = LocalDateTime.now();

	@Column(length = 2, nullable = false)
	private Integer loginFailCnt = 0;

	@Column
	private LocalDateTime pwdModDt = LocalDateTime.now();
	
	@Column(length = 1, nullable = false)
	private String pwdTmpYn = "N";

	@Column(length = 12)
	private String mobileNo;

	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	public AdminUser() {
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public LocalDateTime getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(LocalDateTime ltLoginDt) {
		this.lastLoginDt = ltLoginDt;
	}

	public Integer getLoginFailCnt() {
		return loginFailCnt;
	}

	public void setLoginFailCnt(Integer loginFailCnt) {
		this.loginFailCnt = loginFailCnt;
	}

	public LocalDateTime getPwdModDt() {
		return pwdModDt;
	}

	public void setPwdModDt(LocalDateTime pwdModDt) {
		this.pwdModDt = pwdModDt;
	}

	public String getPwdTmpYn() {
		return pwdTmpYn;
	}

	public void setPwdTmpYn(String pwdTmpYn) {
		this.pwdTmpYn = pwdTmpYn;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return getUserId();
	}

	/**
	 * 마지막 로그인 정상 여부
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true; // LocalDate.now().minusMonths(3).isBefore(ltLoginDt.toLocalDate());
	}

	/**
	 * 계정 안잠김 여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		return loginFailCnt < 5;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * 비밀번호 3개월이내 변경 여부
	 */
	public boolean isPasswordNonExpired() {
		return true; // LocalDate.now().minusMonths(3).isBefore(pwdModDt.toLocalDate());
	}

	/**
	 * 임시 비밀번호 여부
	 */
	public boolean isPasswordNonTemporary() {
		return !StringUtils.equalsIgnoreCase(pwdTmpYn, "y");
	}
	

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
