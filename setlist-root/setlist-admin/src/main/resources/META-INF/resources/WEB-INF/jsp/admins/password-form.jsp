<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="container login" id="body">
	<form class="form-signin" id="admin-form" onsubmit="return false;">
        <h2 class="form-signin-heading">비밀번호 변경</h2>
        <input type="password" name="oldPassword" id="oldPassword" class="form-control" placeholder="기존 비밀번호" required="required">
        <input type="password" name="newPassword" id="newPassword" class="form-control" placeholder="새 비밀번호" required="required">
        <input type="password" id="newPassword2" class="form-control" placeholder="새 비밀번호 확인" required="required">
        <button class="btn btn-lg btn-primary btn-block" type="submit">비밀번호 변경</button>
		<br/>
		<div class="alert alert-info" role="alert">
			<strong>비밀번호 변경 시 유의사항</strong>
			<p>
				- 비밀번호는 10~20자의 영문+숫자 조합으로 설정해주세요.<br>
				- 생일, 주민등록번호 등 타인이 알아내기 쉬운 비밀번호는 사용을 자제해주세요.<br>
				- 주기성 문자(abcd, 1234 등) 및 키보드상의 연속된 배열(asdf, qwerty 등)로 구성된 비밀번호는 사용을 자제해주세요.<br>
				- 안전한 비밀번호 관리를 위해 한 달에 한 번 주기로 비밀번호를 변경하시길 권고합니다.
			</p>
		</div>
    </form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$('#admin-form').submit(function(event) {
			$.ajax({
				method: 'PUT',
				url: '/admins/password',
				data: $(this).serialize()
			}).done(function(data) {
				alert(data.resMsg);
				if (data.resCd === 'SUCCESS') {
					location.href = document.referrer;
				}
			});
			return false;
		});
	});
</script>