<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<div class="container" id="body">
	<c:if test="${empty adminUser.userId}">
	<h1>운영자 등록</h1>
	</c:if>
	<c:if test="${not empty adminUser.userId}">
	<h1>운영자 정보 변경</h1>
	</c:if>

	<form id="admin-form" method="post">
		<input type="hidden" id="modifyYn" value="${not empty adminUser.userId ? 'Y' : 'N'}" />
		<c:choose>
			<c:when test="${not empty adminUser.userId}">
				<div class="form-group">
					<label for="title">ID</label>
					<input type="text" class="form-control" id="userId" disabled="disabled" value="${adminUser.userId}">
				</div>
				<div class="form-group">
					<label for="title">운영자명</label>
					<input type="text" class="form-control" disabled="disabled" value="${adminUser.userNm}">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="title">ID</label>
					<input type="text" class="form-control" id="userId" name="userId" value="${adminUser.userId}">
				</div>
				<div class="form-group">
					<label for="title">운영자명</label>
					<input type="text" class="form-control" id="userNm" name="userNm" value="${adminUser.userNm}">
				</div>
			</c:otherwise>
		</c:choose>
		<div class="form-group">
			<label for="title">휴대폰번호</label>
			<input type="text" class="form-control" id="mobileNo" name="mobileNo" value="${adminUser.mobileNo}">
		</div>
		<div class="form-group text-right">
			<button class="btn btn-primary" type="submit">저장</button>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('#admin-form').submit(function() {
			var modifyYn = $('#modifyYn').val();
			
			// 생성
			if (modifyYn === 'N') {
				$.ajax({
					method: 'POST',
					url: '/admins',
					data: $(this).serialize()
				}).done(function() {
					alert("생성되었습니다.");
					location.href = document.referrer;
				});
			}
			// 수정
			else {
				$.ajax({
					method: 'PUT',
					url: '/admins/'+$('#userId').val(),
					data: {
						mobileNo: $('#mobileNo').val()
					}
				}).done(function() {
					alert("변경되었습니다.");
					location.href = document.referrer;
				});
			}

			return false;
		});
	});
</script>