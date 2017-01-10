<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=0.7, user-scalable=yes, maximum-scale=1" />
	<meta name="description" content="" />
	<meta name="author" content="" />

	<title>세트리스트.kr</title>
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- Bootstrap Theme CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap-theme.css" />

	<!-- Font Awesome -->
	<link rel="stylesheet" href="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />

	<!-- jQuery-UI -->
	<link rel="stylesheet" href="/webjars/jquery-ui/1.12.1/jquery-ui.min.css" />

	<script src="/webjars/jquery/1.12.0/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<tiles:insertAttribute name="content" />
</body>
</html>
