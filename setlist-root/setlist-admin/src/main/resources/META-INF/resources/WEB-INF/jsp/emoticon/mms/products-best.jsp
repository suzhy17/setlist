<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>

<c:set var="currUrl" value="/emoticon/mms/products?type=${param.type}&categoryCd=${param.categoryCd}" />
<input type="hidden" id="currUrl" value="${currUrl}" />
<input type="hidden" id="type" value="${param.type}" />
<div class="container" id="body">
	<h3>포토 이모티콘 관리</h3>
	<br>
	<div id="categoryType" class="btn-group btn-group-justified" role="group" aria-label="이모티콘 종류 선택">
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?type=template&categoryCd=BEST" class="btn ${param.type == 'template' ? 'btn-info' : 'btn-default'}">템플릿</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?type=icon&categoryCd=ALL" class="btn ${param.type == 'icon' ? 'btn-info' : 'btn-default'}">아이콘</a>
		</div>
		<div class="btn-group" role="group">
			<a href="/emoticon/mms/products?type=texticon&categoryCd=ALL" class="btn ${param.type == 'texticon' ? 'btn-info' : 'btn-default'}">그림문자</a>
		</div>
	</div>
	<br>
	<div class="panel panel-default">
		<div class="panel-heading"><b>카테고리</b></div>
		<div class="panel-body" id="category">
			<div class="btn-group btn-group-md" role="group" aria-label="카테고리">
				<c:if test="${param.type == 'template'}">
					<button type="button" class="btn btn-success ${param.categoryCd == 'BEST' ? 'active' : ''}" value="BEST">BEST</button>
				</c:if>
				<button type="button" class="btn btn-success ${param.categoryCd == 'ALL' ? 'active' : ''}" value="ALL">전체보기</button>
			</div>
			<div class="btn-group btn-group-md" role="group" aria-label="카테고리">
				<c:forEach var="item" items="${usedCategoryList}">
					<button type="button" class="btn btn-default ${param.categoryCd == item.categoryCd ? 'active' : ''}" value="${item.categoryCd}">${item.categoryNm}</button>
				</c:forEach>
			</div>
			<div class="btn-group btn-group-md" role="group" aria-label="카테고리">
				<c:forEach var="item" items="${unusedCategoryList}">
					<button type="button" class="btn btn-warning ${param.categoryCd == item.categoryCd ? 'active' : ''}" value="${item.categoryCd}">${item.categoryNm}</button>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<br>
	
	<div class="row">
		<div class="col-md-2">
			<b>${categoryNm}(${productPageResult.totalElements})</b>
		</div>
		<div class="col-md-2">
			<input type="checkbox" id="chkAll" value="" />
  			<label for="chkAll">전체선택</label>
		</div>
		<div class="col-md-2">
		</div>
		<div class="col-md-6 text-right">
			<div class="btn-group btn-group-sm" role="group" aria-label="정렬">
				<a href="${currUrl}&sort=regDt,desc" class="btn ${empty param.sort or param.sort == 'regDt,desc' ? 'btn-info active' : 'btn-default'}">등록순</a>
				<a href="${currUrl}&sort=sendCnt,desc" class="btn ${param.sort == 'sendCnt,desc' ? 'btn-info active' : 'btn-default'}">발송 횟수순</a>
				<a href="${currUrl}&sort=totSendCnt,desc" class="btn ${param.sort == 'totSendCnt,desc' ? 'btn-info active' : 'btn-default'}">발송 건수순</a>
			</div>
		</div>
	</div>

	<br>
	
	<div class="row">
		<c:forEach var="item" items="${productPageResult.content}" varStatus="status">
		<div class="col-sm-3 col-md-2">
			<div class="thumbnail text-center">
				<input type="checkbox" id="chk${status.index}" name="chk" value="${item.bestNo}" /> <label for="chk${status.index}"><b>${item.contentNm}</b></label>
				<input type="hidden" name="categoryNm" value="${item.categoryNm}" />
				<c:if test="${not empty item.imgPath}">
				<div style="height: 140px;">
					<img src="${item.imgPath}" alt="없음" style="max-height: 140px;max-width:155px;">
				</div>
				</c:if>
				<c:if test="${empty item.imgPath}">
				<p>없음</p>
				</c:if>
				<div class="caption">
 					<p><button class="btn btn-primary btn-xs" name="forward" value="${item.bestNo}">앞으로 보내기</button></p>
					<p>${item.sendCnt} / ${item.totSendCnt}</p>
				</div>
			</div>
		</div>
		</c:forEach>
	</div>
    <ems:paramHandler skipParams="page" suffix="&" var="parameter"/>
    <tiles:insertDefinition name="pageNavigation">
        <tiles:putAttribute name="page" value="${productPageResult }"/>
        <tiles:putAttribute name="parameter" value="${parameter}"/>
    </tiles:insertDefinition>
    
    <div class="row">
		<div class="col-md-12 text-center">
			<button type="button" id="btnBestCancel" class="btn btn-primary">BEST 해제하기</button>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		// 전체 선택
		$('#chkAll').click(function(event) {
			$('input[name="chk"]').prop('checked', $(this).is(':checked'));
		});

		// 카테고리 선택
		$('#category button').click(function () {
			var categoryCd = $(this).val(),
				type = $('#type').val(),
				currentUrl = location.href.split("?")[0];
			location.href = currentUrl + '?type=' + type + '&categoryCd='+categoryCd;
		});
		
		// 앞으로 보내기
		$('button[name="forward"]').click(function () {
			var bestNo = $(this).val(),
				type = $('#type').val();
			
			$.ajax({
				method: 'PUT',
				url: '/emoticon/mms/products/best-forward',
				data: {
					type: type,
					bestNo: bestNo
				} 
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
		
		// 베스트 해제하기
		$('#btnBestCancel').click(function () {
			
			if (!confirm("해제하시겠습니까?")) {
				return;
			}

			var $chk = $('input[name="chk"]:checked');

			var bestNos = [];
			$chk.each(function(index) {
				bestNos[index] = $(this).val();
			});
			
			$.ajax({
				method: 'PUT',
				url: '/emoticon/mms/products/best-delete',
				data: {
					bestNos: bestNos
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
