<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<title>세트리스트.kr</title>
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

	<!-- Bootstrap Theme CSS -->
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.7/css/bootstrap-theme.css" />

	<!-- Font Awesome -->
	<link rel="stylesheet" href="/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />

	<!-- jQuery-UI -->
	<link rel="stylesheet" href="/webjars/jquery-ui/1.12.1/jquery-ui.min.css" />
	<link rel="stylesheet" href="/static/css/jumbotron-narrow.css" />

	<script src="/webjars/jquery/1.12.0/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<div class="header clearfix">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li role="presentation" class="active"><a href="/">Home</a></li>
				<li role="presentation"><a href="/artists">Artists</a></li>
				<li role="presentation"><a href="/setlist?write"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Setlist</a></li>
				<li role="presentation"><a href="/member?write"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Join</a></li>
			</ul>
		</nav>
		<h3 class="text-muted">세트리스트.kr</h3>
	</div>

	<div class="row">
		<div class="col-lg-12">
			<form action="/search">
				<div class="input-group">
					<input type="text" name="keyword" class="form-control" placeholder="아티스트 검색" value="${param.keyword}" />
					<span class="input-group-btn">
						<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
					</span>
				</div>
			</form>
		</div>
	</div>

	<tiles:insertAttribute name="content" />

	<footer class="footer">
		<p>&copy; 2016 Company, Inc.</p>
	</footer>
</div>
</body>
</html>
