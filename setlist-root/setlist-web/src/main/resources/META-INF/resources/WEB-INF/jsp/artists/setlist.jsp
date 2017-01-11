<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row marketing">
	<div class="panel panel-default">
		<div class="panel-heading"><h4>${artist.artistName} Setlist</h4></div>
		<div class="panel-body">
			<p>Venue: ${setlist.tour.venue}</p>
			<p>Tour: ${setlist.tour.tourName}</p>
			<p>Event Date: ${setlist.eventDate}</p>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th><h4>#</h4></th>
					<th><h4>곡명</h4></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" varStatus="status" items="${songs}">
				<tr>
					<td><h4>${item.songId.trackNo}</h4></td>
					<td><h4>${item.subject} ${item.remark}</h4></td>
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