<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="initial-scale=0.7, user-scalable=yes, maximum-scale=1" />
	<meta name="description" content="" />
	<meta name="author" content="" />

	<title>EMS :: 통합 이모티콘 관리 서비스</title>
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- Bootstrap Theme CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap-theme.css" />

	<!-- Font Awesome -->
	<link rel="stylesheet" href="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />

	<!-- jQuery-UI -->
	<link rel="stylesheet" href="/webjars/jquery-ui/1.12.1/jquery-ui.min.css" />
	
	<!-- jQuery-timepicker-addon -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.3/jquery-ui-timepicker-addon.min.css" />

	<!-- Custom styles for this template -->
	<link rel="stylesheet" href="/static/css/bootstrap/style.css?dt=1" />

	<link rel="stylesheet" href="/webjars/jquery-file-upload/9.12.6/css/jquery.fileupload.css" />
</head>

<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<sec:authorize access="hasAuthority('ROLE_USER')">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/" title="메인으로"><i class="glyphicon glyphicon-phone"></i> 통합 이모티콘 관리</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">포토 이모티콘<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/emoticon/mms/categories?type=template">카테고리 관리</a></li>
							<li><a href="/emoticon/mms/products?type=template&categoryCd=BEST">이모티콘 관리</a></li>
						</ul>
					</li>
					<sec:authorize access="hasAuthority('ROLE_ADMIN')">
					<li><a href="/admins" title="운영자 관리">운영자 관리 </a></li>
					</sec:authorize>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/admins/<sec:authentication property="principal.username" />"><span class="glyphicon glyphicon-user"></span><span class="sr-only"><sec:authentication property="principal.username" /></span> <sec:authentication property="principal.username" /> </a></li>
					<li><a href="/admins/password" title="비밀번호 변경"><i class="fa fa-key" aria-hidden="true"></i></a></li>
					<li><a href="/logout" title="로그아웃"><span class="glyphicon glyphicon-log-out"></span></a></li>
				</ul>
			</div>
			</sec:authorize>
		</div>
	</div>

	<script src="/webjars/jquery/3.1.1/dist/jquery.min.js"></script>
	<script src="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>

	<tiles:insertAttribute name="content" />

	<script src="/webjars/jquery-file-upload/9.12.6/js/vendor/jquery.ui.widget.js"></script>
	<script src="/webjars/blueimp-load-image/2.6.1/js/load-image.all.min.js"></script>
	<script src="/webjars/blueimp-canvas-to-blob/2.1.1/js/canvas-to-blob.min.js"></script>
	<script src="/webjars/jquery-file-upload/9.12.6/js/jquery.fileupload.js"></script>
	<script src="/webjars/jquery-file-upload/9.12.6/js/jquery.fileupload-process.js"></script>
	<script src="/webjars/jquery-file-upload/9.12.6/js/jquery.fileupload-image.js"></script>
	<script src="/webjars/jquery-file-upload/9.12.6/js/jquery.fileupload-validate.js"></script>
	<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/webjars/jquery-form/3.51/jquery.form.js"></script>
	<script src="/webjars/jquery-blockui/2.65/jquery.blockUI.js"></script>

	<!-- http://trentrichardson.com/examples/timepicker/ -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ui-timepicker-addon/1.6.3/jquery-ui-timepicker-addon.min.js"></script>

	<script src="/static/js/jq.init.js"></script>
	<script src="/static/js/common.js"></script>
</body>
</html>
