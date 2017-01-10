<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container" id="body">
	<h3>아티스트 등록</h3>
	<div class="panel panel-default">
		<div class="panel-heading"><b>아티스트 등록</b></div>
		<div class="panel-body">
			<form id="addFrm" class="form-inline">
				<div class="form-group">
					<label for="artistId">아티스트 ID</label>
					<input type="text" class="form-control" name="artistId" id="artistId" maxlength="50" placeholder="아티스트 ID" />
				</div>
				<div class="form-group">
					<label for="artistName">아티스트 명</label>
					<input type="text" class="form-control" name="artistName" id="artistName" maxlength="50" placeholder="아티스트 명" />
				</div>
				<div class="form-group">
					<label for="nationality">국적</label>
					<input type="text" class="form-control" name="nationality" id="nationality" maxlength="100" placeholder="국적" />
				</div>
				<button type="submit" class="btn btn-primary">추가</button>
			</form>
		</div>
	</div>
	
</div>
	