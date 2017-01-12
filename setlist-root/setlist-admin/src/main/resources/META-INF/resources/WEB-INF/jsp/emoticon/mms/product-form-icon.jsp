<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:set var="typeNm" value="${param.type == 'icon' ? '아이콘' : '그림문자'}" />
<div class="container" id="body">
	<h3>포토 이모티콘 등록</h3>
	<br>
	<div id="categoryType" class="btn-group btn-group-justified" role="group" aria-label="이모티콘 종류 선택">
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=template" class="btn ${param.type == 'template' ? 'btn-info' : 'btn-default'}">템플릿</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=icon" class="btn ${param.type == 'icon' ? 'btn-info' : 'btn-default'}">아이콘</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?write&type=texticon" class="btn ${param.type == 'texticon' ? 'btn-info' : 'btn-default'}">그림문자</a>
		</div>
	</div>
	<br>

	<form id="product-form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="productTypeCd" value="${productTypeCd}" />
		<table class="table table-bordered">
			<tbody>
				<tr>
					<th class="active text-center">${typeNm}명</th>
					<td>
						<div class="col-md-4">
							<input type="text" name="contentNm" class="form-control" maxlength="20" placeholder="템플릿명" />
						</div>
						<span class="slim">(20byte 이내)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">카테고리</th>
					<td>
						<div class="col-md-4">
							<select name="categoryCd" class="form-control">
								<option value="">[카테고리 선택]</option>
								<c:forEach var="item" items="${usedCategoryList}">
									<option value="${item.categoryCd}">${item.categoryNm}</option>
								</c:forEach>
								<c:forEach var="item" items="${unusedCategoryList}">
									<option value="${item.categoryCd}">${item.categoryNm} (미사용)</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<th class="active text-center">${typeNm}이미지</th>
					<td>
						<div class="col-md-4">
							<input type="file" name="file" class="form-control" />
						</div>
						<span class="slim">(png파일)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">태그</th>
					<td>
						<div class="col-md-4">
							<input type="text" name="tagNm" class="form-control" maxlength="20" placeholder="태그" />
						</div>
						<span class="slim">(,로 구분)</span>
					</td>
				</tr>
				<tr>
					<th class="active text-center">판매여부</th>
					<td>
						<label class="radio-inline">
							<input type="radio" name="saleYn" value="Y" checked="checked" /> 예
						</label>
						<label class="radio-inline">
							<input type="radio" name="saleYn" value="N" /> 아니오
						</label>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="text-center">
			<button type="submit" class="btn btn-primary">등록하기</button>
			<a href="/emoticon/mms/products?type=${param.type}&categoryCd=ALL" class="btn btn-default">목록으로</a>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var maxThumbSize = 51200; // 50Kb

		// 이미지 체크
		$('input[name="file"]').change(function() {
			// 파일 확장자 체크
			var fileName = $(this).val();
			var fileExt = fileName.slice(fileName.indexOf('.') + 1).toLowerCase();
			if (fileExt !== 'png') {
				alert('png 파일만 선택 가능합니다.');
				this.value = null;
				return false;
			}
		});

		// 폼 전송 (파일 포함)
		var $frm = $('#product-form');
		$frm.ajaxForm({
			dataType: 'json',
			beforeSubmit: function(formData, jqForm, options) {
				if (!confirm('아이콘(그림문자)을 등록하시겠습니까?')) {
					return false;
				}

				$.blockUI({
					message: '파일 업로드중 입니다.<br/>잠시만 기다려주세요.'
				});
				
				var $contentNm  = $frm.find('input[name="contentNm"]'),
					$tagNm      = $frm.find('input[name="tagNm"]'),
					$categoryCd = $frm.find('select[name="categoryCd"]'),
					$file       = $frm.find('input[name="file"]');
	
				if (!$contentNm.val()) {
					alert('아이콘(그림문자)명을 입력해 주세요.');
					$.unblockUI();
					$contentNm.focus();
					return false;
				}
	
				if (!$categoryCd.val()) {
					alert('카테고리를 입력해 주세요.');
					$.unblockUI();
					$categoryCd.focus();
					return false;
				}
	
				if (!$tagNm.val()) {
					alert('태그를 입력해 주세요.');
					$.unblockUI();
					$tagNm.focus();
					return false;
				}
	
				if (!$file.val()) {
					alert('이미지 파일을 선택해 주세요.');
					$.unblockUI();
					$file.focus();
					return false;
				}
			},
			success: function(data) {
				alert(data.resMsg);
				location.href = '/emoticon/mms/products?type=${param.type}&categoryCd=ALL';
			},
			complete: function(xhr) {
				$.unblockUI();
			}
		});
	});
</script>