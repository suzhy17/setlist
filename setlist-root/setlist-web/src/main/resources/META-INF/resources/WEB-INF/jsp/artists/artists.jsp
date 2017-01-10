<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="item" varStatus="status" items="${artists}">
	아티스트 ID : ${item.artistId}<br>
	아티스트 명 : ${item.artistName}<br>
	국적 : ${item.nationality}<br>
	등록일 : ${item.regDt}<br>
	<hr/>
</c:forEach>