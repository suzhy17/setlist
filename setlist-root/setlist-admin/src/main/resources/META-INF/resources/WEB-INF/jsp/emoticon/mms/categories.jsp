<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container" id="body">
	<h3>포토 카테고리 관리</h3>
	<br>
	<div id="categoryType" class="btn-group btn-group-justified" role="group" aria-label="이모티콘 종류 선택">
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/categories?type=template" class="btn ${param.type == 'template' ? 'btn-info' : 'btn-default'}">템플릿</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/categories?type=icon" class="btn ${param.type == 'icon' ? 'btn-info' : 'btn-default'}">아이콘</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/categories?type=texticon" class="btn ${param.type == 'texticon' ? 'btn-info' : 'btn-default'}">그림문자</a>
		</div>
	</div>
	<br>
	<div>
		<div class="panel panel-default">
			<div class="panel-heading"><b>카테고리 추가</b></div>
			<div class="panel-body">
				<form id="addFrm" class="form-inline">
					<input type="hidden" name="categoryTypeCd" value="${categoryTypeCd}" />
					<div class="form-group">
						<label for="categoryNm">카테고리명</label>
						<input type="text" class="form-control" name="categoryNm" id="categoryNm" maxlength="20" placeholder="카테고리명" />
					</div>
					<div class="form-group">
						<label for="categoryCd">카테고리코드</label>
						<input type="text" class="form-control" name="categoryCd" id="categoryCd" maxlength="20" placeholder="카테고리코드(영문)" />
					</div>
					<button type="submit" class="btn btn-primary">추가</button>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><b>카테고리 노출 설정</b></div>
			<div class="panel-body">
				<div class="row">
					<div class="col-md-6">
						<p>※ 마우스 드래그 앤 드롭으로 노출여부 및 순서를 변경하세요.</p>
					</div>
					<div class="col-md-2 text-right">
						<button type="button" id="categorySave" class="btn btn-primary">변경내용 저장</button>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-md-4">
						<ul id="sortable-unused" class="list-group connectedSortable">
							<li data-id="" class="list-group-item active ui-state-disabled">숨김 카테고리</li>
							<li data-id="" class="list-group-item ui-state-disabled">미분류 (변경 불가)</li>
							<c:forEach var="item" items="${unusedCategoryList}">
								<c:if test="${item.categoryCd.indexOf('NO_CATE') < 0}">
									<li data-id="${item.categoryCd}" class="list-group-item list-group-item-danger">${item.categoryNm} <span class="badge">삭제</span></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="col-md-4">
						<ul id="sortable-used" class="list-group connectedSortable">
							<li data-id="" class="list-group-item active ui-state-disabled">노출 카테고리</li>
							<li data-id="" class="list-group-item ui-state-disabled">BEST (변경 불가)</li>
							<li data-id="" class="list-group-item ui-state-disabled">전체보기 (변경 불가)</li>
							<c:forEach var="item" items="${usedCategoryList}">
								<li data-id="${item.categoryCd}" class="list-group-item">${item.categoryNm} <span class="badge">삭제</span></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		// 카테고리 설정 드래그 앤 드롭 영역 설정
		var $sortable = $('#sortable-used, #sortable-unused');
		$sortable.sortable({
			items: 'li:not(.ui-state-disabled)',
			connectWith: '.connectedSortable'
	    }).disableSelection();
		
		// 카테고리 추가
		$('#addFrm').submit(function() {
			var $frm = $(this),
				$categoryCd = $(this).find('input[name="categoryCd"]'),
				$categoryNm = $(this).find('input[name="categoryNm"]');
			
			if (!$categoryCd.val()) {
				alert('카테고리코드를 입력하세요.');
				$categoryCd.focus();
				return false;
			}
			
			if (!$categoryNm.val()) {
				alert('카테고리명을 입력하세요.');
				$categoryNm.focus();
				return false;
			}
			
			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/categories',
				data: $(this).serialize()
			}).done(function(data) {
				if (data.resCd === 'SUCCESS') {
			        alert('추가되었습니다.');
			        
			        var li = '<li data-id="' + $categoryCd.val() + '" class="list-group-item list-group-item-danger">' + $categoryNm.val() + ' <span class="badge">0</span></li>';
			        $('#sortable-unused').append(li);
			        
			        $categoryCd.val('');
			        $categoryNm.val('');
				} else {
			        alert(data.resMsg);
				}
			});
			
			return false;
		});

		// 카테고리 삭제
		$sortable.find('span.badge').css('cursor', 'pointer').click(function() {
			
			if (!confirm('삭제하시겠습니까? 삭제하시면 등록되어있는 이모티콘이 모두 삭제됩니다.')) {
				return;
			}
			
			if (!confirm('삭제 후 복구되지 않습니다. 정말 삭제하시겠습니까?')) {
				return;
			}
			
			var $li = $(this).parent(),
				categoryCd = $li.data('id');
			
			$.ajax({
				method: 'DELETE',
				url: '/emoticon/mms/categories/'+categoryCd
			}).done(function(data) {
				alert('삭제되었습니다.');
				$li.remove();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});

		// 카테고리 변경 내용 저장
		$('#categorySave').click(function() {
			
			if (!confirm('카테고리 설정을 저장하시겠습니까?')) {
				return;
			}
			
			var $usedList = $('#sortable-used li:not(.ui-state-disabled)'),
				$unusedList = $('#sortable-unused li:not(.ui-state-disabled)');
			
			var usedCategoryCds = [];
			$usedList.each(function(index) {
				usedCategoryCds[index] = $(this).data('id');
			});
			
			var unusedCategoryCds = [];
			$unusedList.each(function(index) {
				unusedCategoryCds[index] = $(this).data('id');
			});
			
			$.ajax({
				method: 'PUT',
				url: '/emoticon/mms/categories',
				async: false,
				data: {
					usedCategoryCds: usedCategoryCds,
					unusedCategoryCds: unusedCategoryCds
				}
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
	});
</script>
