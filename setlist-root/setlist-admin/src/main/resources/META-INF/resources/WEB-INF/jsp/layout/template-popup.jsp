<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="description" content="">
	<meta name="author" content="">
	<meta name="viewport" content="initial-scale=0.7, user-scalable=yes, maximum-scale=1">
	<title>콜믹스 통합관리</title>
	
	<!-- Bootstrap core CSS -->
	<link href="/resources/css/bootstrap.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="/resources/css/style.css" rel="stylesheet">
</head>

<body>
	<script src="/resources/js/jquery.js"></script>
	<script src="/resources/js/jquery.form.js"></script>
	<script src="/resources/js/require.js" data-main="/resources/js/"></script>
	<script src="/resources/js/common.js"></script>

	<tiles:insertAttribute name="content" />

	<script src="/resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		// 툴팁 활성화
		$('[data-toggle="tooltip"]').tooltip();
	</script>
</body>
</html>
