<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta name="generator" content="Bootply" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	
	<title>EMS :: 통합 이모티콘 관리 서비스</title>
	
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="/static/css/bootstrap/login.css">
</head>
<body>
<div class="container">
	<!-- 테스트용 -->	
	<div id="loginModal" class="modal show" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="bi">EMS</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<c:if test="${not empty errMsg}">
							<div class="alert alert-danger" role="alert">
								<strong>로그인 실패!</strong>
								<p>${errMsg}</p>
								<c:if test="${not empty QRUrl}">
							    	<p>
										* Google OTP 어플 다운로드 후, QR코드를 스캔해주세요.<br/>
										* [안드로이드 : Google OTP, 아이폰 : Google Authenticator]<br/>
										* QR코드 스캔은 최초 1회만 진행됩니다.<br/>
										* 이후에는 어플에서 보여지는 6자리 숫자로 OTP 인증코드를 입력하시면 됩니다.<br/>
										<br/>
									</p>
								    <div class="text-center">
										<img src="${QRUrl}" width="90" height="90" />
								    </div>
								</c:if>    
							</div>
						</c:if>
					</div>
					<div class="row">
						<form method="post" action="/login-process" class="form col-md-offset-2 col-md-12 center-block" role="form">
							<div class="form-group">
		 						<label for="id">로그인 ID</label>
								<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디" autofocus="autofocus"/>
							</div>
							<div class="form-group">
 								<label for="pwd">Password</label>
								<input type="password" class="form-control" name="password" id="password" placeholder="비밀번호" autocomplete="off" />
							</div>	
							<div class="form-group">
 								<label for="otpNumber">OTP 인증코드</label>
								<input type="number" class="form-control" name="otpNumber" id="otpNumber" maxlength="6" placeholder="OTP 인증코드 6자리" autocomplete="off">
							</div>	
							<div class="form-group">
								<div class="col-md-6">
									<button class="btn btn-lg btn-primary btn-block" type="submit">로그인</button>
								</div>
								<div class="col-md-6">
									<button class="btn btn-lg btn-warning btn-block" type="reset">초기화</button>
								</div>		
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
