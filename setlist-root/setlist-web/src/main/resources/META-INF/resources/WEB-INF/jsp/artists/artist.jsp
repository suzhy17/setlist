<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

아티스트 ID : ${artist.artistId}<br>
아티스트 명 : ${artist.artistName}<br>
국적 : ${artist.nationality}<br>
등록일 : ${artist.regDate}<br>

<div class="panel panel-default">
	<div class="panel-heading">${artist.artistName}</div>
	<div class="panel-body">
		<p>국적 : ${artist.nationality}</p>
	</div>

	<table class="table">
		<thead>
			<tr>
				<th>#</th>
				<th>투어명</th>
				<th>지역</th>
				<th>공연일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" varStatus="status" items="${artist.setlists}">
			<tr>
				<td>${item.setlistNo}</td>
				<td>${item.tour.tourName}</td>
				<td>${item.tour.venue}</td>
				<td>${item.eventDate}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
