<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>운영자 관리</h3>
	<br>
	<div class="table-wrapper">
		<table class="table table-striped table-hover table-condensed">
			<thead>
				<tr>
					<th class="text-center">ID</th>
					<th class="text-center">운영자명</th>
					<th class="text-center">권한</th>
					<th class="text-center">최종 로그인</th>
					<th class="text-center">로그인 실패</th>
					<th class="text-center">임시 비밀번호 여부</th>
					<th class="text-center">임시 비밀번호 발급</th>
					<th class="text-center">변경</th>
					<th class="text-center">삭제</th>
					<th class="text-center">OTP초기화</th>
				<tr>
			</thead>
			<tbody class="table-hover">
				<c:forEach var="item" items="${pageResult.content }">
				<tr>
					<td class="text-center">${item.userId}</td>
					<td class="text-center">${item.userNm}</td>
					<td class="text-center">${item.auth}</td>
					<td class="text-center">${item.lastLoginDt}</td>
					<td class="text-center">${item.loginFailCnt} 건</td>
					<td class="text-center">${item.pwdTmpYn}</td>
					<td class="text-center">
						<button type="button" name="tempPassword" class="btn btn-default" value="${item.userId}">발급</button>
					</td>
					<td class="text-center">
						<a href="/admins/${item.userId}" class="btn btn-default">변경</a>
					</td>
					<td class="text-center">
						<button type="button" name="delete" class="btn btn-danger" value="${item.userId}">삭제</button>
					</td>
					<td class="text-center">
						<button type="button" name="otpReset" class="btn btn-default" value="${item.userId}">OTP초기화</button>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="text-right">
		<a href="/admins?write" class="btn btn-primary">추가</a>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";

		$('button[name="tempPassword"]').click(function() {
			var userid = $(this).val();
			
			if (!confirm(userid + '님 계정 비밀번호를 임시 비밀번호로 변경하겠습니까?')) {
				return;
			}

			$.ajax({
				method: 'PUT',
				url: '/admins/' + userid + '/tempPassword'
			}).done(function() {
		        alert("변경되었습니다.");
		        location.reload();
			});
		});

		$('button[name="delete"]').click(function() {
			var userid = $(this).val();
			
			if (!confirm(userid + '님 계정을 삭제하겠습니까?')) {
				return;
			}

			$.ajax({
				method: 'DELETE',
				url: '/admins/'+userid
			}).done(function() {
		        alert("삭제되었습니다.");
		        location.reload();
			});
		});
		
		$('button[name="otpReset"]').click(function() {
			var userid = $(this).val();
			
			if (!confirm(userid + '님 계정의 OTP 코드를 초기화하겠습니까?')) {
				return;
			}

			$.ajax({
				method: 'PUT',
				url: '/admins/' + userid + '/otpReset'
			}).done(function() {
				alert("초기화되었습니다.");
				location.reload();
			});
		});
	});
</script>
