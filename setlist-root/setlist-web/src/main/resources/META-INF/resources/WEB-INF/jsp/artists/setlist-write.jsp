<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row marketing">
	<div class="panel panel-default">
		<div class="panel-heading"><b>세트리스트 등록</b></div>
		<div class="panel-body">
			<form id="addFrm" class="form-horizontal">
				<div class="form-group">
					<label for="artistId" class="col-sm-2 control-label">아티스트 ID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="artistId" id="artistId" maxlength="50" placeholder="아티스트 ID" />
					</div>
				</div>
				<div class="form-group">
					<label for="tourName" class="col-sm-2 control-label">투어명</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="tourName" id="tourName" maxlength="50" placeholder="투어명" />
					</div>
				</div>
				<div class="form-group">
					<label for="venue" class="col-sm-2 control-label">지역</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="venue" id="venue" maxlength="50" placeholder="지역" />
					</div>
				</div>
				<div class="form-group">
					<label for="eventDate" class="col-sm-2 control-label">공연일자</label>
					<div class="col-sm-10">
						<input type="date" class="form-control" name="eventDate" id="eventDate" maxlength="50" placeholder="지역" />
					</div>
				</div>
				<div>
					<table class="table">
						<thead>
							<tr>
								<th><h4>#</h4></th>
								<th><h4>곡명</h4></th>
								<th><h4>비고</h4></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>2</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>3</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>4</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>5</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>6</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>7</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>8</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>9</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
							<tr>
								<td>10</td>
								<td><input type="text" name="subject" class="form-control" /></td>
								<td><input type="text" name="remark" class="form-control" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-10 col-sm-2">
						<button type="submit" class="btn btn-primary btn-block">저장</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		$('#addFrm').submit(function() {
			
			if (!confirm('세트리스트를 등록하시겠습니까?')) {
				return false;
			}	
			
			var $artistId = $(this).find('input[name="artistId"]'),
				$tourName = $(this).find('input[name="tourName"]'),
				$venue = $(this).find('input[name="venue"]'),
				$eventDate = $(this).find('input[name="eventDate"]');
			
			if (!$artistId.val()) {
				alert('아티스트ID를 입력하세요.');
				$artistId.focus();
				return false;
			}
			
			if (!$tourName.val()) {
				alert('아티스트명을 입력하세요.');
				$tourName.focus();
				return false;
			}
			
			if (!$venue.val()) {
				alert('지역을 입력하세요.');
				$venue.focus();
				return false;
			}
			
			if (!$eventDate.val()) {
				alert('공연일자를 입력하세요.');
				$eventDate.focus();
				return false;
			}
			
			$.ajax({
				method: 'POST',
				url: '/setlist',
				data: $(this).serialize()
			}).done(function(data) {
		        alert(data.resMsg);
				if (data.resCd === 'SUCCESS') {
					history.back();
				}
			});
			
			return false;

		});
	});
</script>