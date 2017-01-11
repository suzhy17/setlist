<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row marketing">
	<div class="panel panel-default">
		<div class="panel-heading"><h4>${artist.artistName}</h4></div>
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
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" varStatus="status" items="${artist.setlists}">
				<tr>
					<td>${item.setlistNo}</td>
					<td>${item.tour.tourName}</td>
					<td>${item.tour.venue}</td>
					<td>${item.eventDate}</td>
					<td><button type="button" name="tracksGo" class="btn btn-default btn-sm" data-artist-id="${artist.artistId}" data-setlist-no="${item.setlistNo}">Setlist</button></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		$('button[name="tracksGo"]').on('click', function() {
			location.href = '/artists/' + $(this).data('artist-id') + '/' + $(this).data('setlist-no');
		});
	});
</script>