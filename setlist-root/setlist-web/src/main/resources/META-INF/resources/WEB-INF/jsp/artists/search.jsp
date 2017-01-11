<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<div class="row marketing">
	<div class="list-group">
	<c:forEach var="item" varStatus="status" items="${artists}">
		<button type="button" name="artistGo" class="list-group-item" value="${item.artistId}">
			${item.artistName}, ${item.nationality}
		</button>
	</c:forEach>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		$('#idxNmArea > button').on('click', function() {
			location.href = '/artists?idxNm=' + $(this).val();
		});
		
		$('button[name="artistGo"]').on('click', function() {
			location.href = '/artists/' + $(this).val();
		});
	});
</script>