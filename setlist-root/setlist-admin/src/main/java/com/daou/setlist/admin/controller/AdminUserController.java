package com.daou.setlist.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daou.setlist.common.model.JsonResult;
import com.daou.setlist.admin.domain.AdminUser;
import com.daou.setlist.admin.service.AdminUserService;

@Controller
public class AdminUserController {
	
	private static final Logger log = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 로그인 폼
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model) {
		log.debug("로그인 폼");
		return "login";
	}

	/**
	 * 비밀번호 변경
	 * @return
	 */
	@RequestMapping(value = "/admins/password", method = RequestMethod.GET)
	public String passwordForm() {
		return "admins/password-form";
	}

	/**
	 * 비밀번호 변경 처리
	 * @param oldPassword
	 * @param newPassword
	 */
	@ResponseBody
	@RequestMapping(value = "/admins/password", method = RequestMethod.PUT)
	public JsonResult changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {

		adminUserService.changePassword(oldPassword, newPassword);
		
		return new JsonResult();
	}

	/**
	 * 운영자 리스트
	 * @param model
	 * @param pageable
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public String admins(Model model, @PageableDefault(sort = "lastLoginDt", direction = Direction.DESC) Pageable pageable) {

		Page<AdminUser> pageResult = adminUserService.getAdminUser(pageable);
		
		model.addAttribute("pageResult", pageResult);

		return "admins/admins";
	}

	/**
	 * 운영자 등록 폼
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins", params = "write", method = RequestMethod.GET)
	public String adminWriteForm(Model model) {
		return "admins/admin-form";
	}

	/**
	 * 운영자 등록 처리
	 * @param model
	 * @param adminUser
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admins", method = RequestMethod.POST)
	public void adminWrite(Model model, AdminUser adminUser) {
		adminUserService.createUser(adminUser);
	}

	/**
	 * 운영자 수정 폼
	 * @param model
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admins/{userId}", method = RequestMethod.GET)
	public String adminModifyForm(Model model, @PathVariable("userId") String userId) {
		model.addAttribute("adminUser", adminUserService.loadUserByUsername(userId));
		return "admins/admin-form";
	}

	/**
	 * 운영자 수정 처리
	 * @param model
	 * @param userId
	 * @param mobileNo
	 */
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admins/{userId}", method = RequestMethod.PUT)
	public void adminModify(
			@PathVariable("userId") String userId,
			@RequestParam String mobileNo) {
		adminUserService.updateUser(userId, mobileNo);
	}

	/**
	 * 운영자 삭제
	 * @param model
	 * @param id
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admins/{userId}", method = RequestMethod.DELETE)
	public void adminDelete(@PathVariable("userId") String userId) {
		adminUserService.deleteUser(userId);
	}

	/**
	 * 임시 비밀번호 발급
	 * @param model
	 * @param id
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admins/{userId}/tempPassword", method = RequestMethod.PUT)
	public void adminPasswordReset(Model model, @PathVariable("userId") String userId) {
		adminUserService.tempPassword(userId);
	}
	
	/**
	 * OTP 초기화
	 * @param model
	 * @param id
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping(value = "/admins/{userId}/otpReset", method = RequestMethod.PUT)
	public void adminOtpReset(Model model, @PathVariable("userId") String userId) {
		adminUserService.otpReset(userId);
	}
}