<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>

<c:set var="currUrl" value="/emoticon/mms/products?type=${param.type}&categoryCd=${param.categoryCd}" />
<input type="hidden" id="currUrl" value="${currUrl}" />
<input type="hidden" id="type" value="${param.type}" />
<input type="hidden" id="previewServerUrl" value="${previewServerUrl}" />

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
			<br />
			<div class="btn-group btn-group-md" role="group" aria-label="카테고리">
				<c:forEach var="item" items="${usedCategoryList}">
					<button type="button" class="btn btn-default ${param.categoryCd == item.categoryCd ? 'active' : ''}" value="${item.categoryCd}">${item.categoryNm}</button>
				</c:forEach>
			</div>
			<br />
			<div class="btn-group btn-group-md" role="group" aria-label="카테고리">
				<c:forEach var="item" items="${unusedCategoryList}">
					<button type="button" class="btn btn-warning ${param.categoryCd == item.categoryCd ? 'active' : ''}" value="${item.categoryCd}">${item.categoryNm}</button>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<div class="row">
    	<div class="col-md-6">
			<form name="searchForm" class="form-inline">
				<div class="form-group">
					<input type="text" name="searchStDt" class="form-control ui-datepicker-trigger" placeholder="검색 시작 일자" value="${param.searchStDt}" />
				</div>
				<div class="form-group">
					<input type="text" name="searchEnDt" class="form-control ui-datepicker-trigger" placeholder="검색 끝 일자" value="${param.searchEnDt}" />
				</div>
				<div class="input-group">
					<input type="text" name="searchKeyword" class="form-control" placeholder="검색어(제목,태그)" value="${param.searchKeyword}" />
					<span class="input-group-btn">
						<button type="submit" class="btn btn-primary">검색</button>
					</span>
				</div>
			</form>
		</div>
		<div class="col-md-2">
			<form name="reservationForm">
			<div class="input-group">
				<input type="text" name="bestDt" class="form-control" placeholder="베스트 예약하기" value="<javatime:format value="${defaultDateTime}" pattern="yyyy-MM-dd HH:mm" />">
				<span class="input-group-btn">
					<button type="submit" class="btn btn-danger">예약</button>
				</span>
			</div>
			</form>
		</div>
		<div class="col-md-4 text-right">
			<button type="button" class="btn btn-primary" onclick="location.href='/emoticon/mms/products?write&type=${param.type}';">이모티콘 등록</button>
			<div class="btn-group" role="group">
				<button type="button" name="btnCategoryMove" class="btn btn-default">이동</button>
				<button type="button" name="btnProductDelete" class="btn btn-default">삭제</button>
				<button type="button" name="btnBestRegister" class="btn btn-default">BEST</button>
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
		<div class="col-md-8 text-right">
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
				<input type="checkbox" id="chk${status.index}" name="chk" value="${item.productNo}" /> <label for="chk${status.index}"><strong class="${item.saleYn == 'N' ? 'text-danger' : '' }">${item.contentNm}</strong></label>
				<input type="hidden" name="categoryNm" value="${item.categoryNm}" />
				<c:if test="${not empty item.imgPath}">
				<div style="height: 140px;">
					<a href="#" class="preview"
						data-product-no="${item.productNo}"
						data-user-id="${editorAuth.userId}"
						data-company-no="${editorAuth.companyNo}"
						data-h="${editorAuth.h}">
						<img src="${item.imgPath}" alt="없음" style="max-height: 140px;max-width:155px;">
					</a>
				</div>
				</c:if>
				<c:if test="${empty item.imgPath}">
				<p>없음</p>
				</c:if>
				<div class="caption">
					<p><javatime:format value="${item.regDt}" pattern="yyMMdd HH:mm" /></p>
					<p>
						<a href="/emoticon/mms/products/${item.productNo}?type=template" class="btn btn-primary btn-xs">수정</a>
						<c:if test="${item.bestStat == 'Y'}">
							<button type="button" name="bestOff" class="btn btn-danger btn-xs" role="button" value="${item.bestNo}">ON</button>
						</c:if>
						<c:if test="${item.bestStat == 'R'}">
							<button type="button" name="bestOff" class="btn btn-primary btn-xs" role="button" value="${item.bestNo}">ON</button>
						</c:if>
						<c:if test="${item.bestStat == 'N'}">
							<button type="button" name="bestOn" class="btn btn-default btn-xs" role="button" value="${item.productNo}">OFF</button>
						</c:if>
					</p>
					<p>${item.sendCnt} / ${item.totSendCnt}</p>
 					<!--<p><button class="btn btn-primary btn-sm" role="button" value="${item.productNo}">앞으로 보내기</button></p> -->
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
    	<div class="col-md-8">
			<form name="searchForm" class="form-inline">
				<div class="form-group">
					<input type="text" name="searchStDt" class="form-control ui-datepicker-trigger" placeholder="검색 시작 일자" value="${param.searchStDt}" />
				</div>
				<div class="form-group">
					<input type="text" name="searchEnDt" class="form-control ui-datepicker-trigger" placeholder="검색 끝 일자" value="${param.searchEnDt}" />
				</div>
				<div class="input-group">
					<input type="text" name="searchKeyword" class="form-control" placeholder="검색어(제목,태그)" value="${param.searchKeyword}" />
					<span class="input-group-btn">
						<button type="submit" class="btn btn-primary">검색</button>
					</span>
				</div>
			</form>
		</div>
		<div class="col-md-4 text-right">
			<button type="button" class="btn btn-primary" onclick="location.href='/emoticon/mms/products?write&type=${param.type}';">이모티콘 등록</button>
			<div class="btn-group" role="group">
				<button type="button" name="btnCategoryMove" class="btn btn-default">이동</button>
				<button type="button" name="btnProductDelete" class="btn btn-default">삭제</button>
				<button type="button" name="btnBestRegister" class="btn btn-default">BEST</button>
			</div>
		</div>
	</div>
	
	<br />
	
	<div class="panel panel-info">
		<div class="panel-heading"><b>예약내역</b></div>
		<table class="table table-hover table-condensed table-bordered">
			<thead>
				<tr>
					<th class="text-center active">날짜</th>
					<th class="text-center active">예약시간</th>
					<th class="text-center active">내용</th>
					<th class="text-center active">진행상황</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${bestPageResult.content}" varStatus="status">
				<tr class="${item.bestStat != 'Y' ? 'danger' : ''}">
					<td class="text-center"><javatime:format value="${item.bestDt}" pattern="yyyy-MM-dd" /></td>
					<td class="text-center"><javatime:format value="${item.bestDt}" pattern="HH:mm" /></td>
					<td>${item.contentNm}</td>
					<td class="text-center">
						<c:if test="${item.bestStat == 'Y'}">
							예약
						</c:if>
						<c:if test="${item.bestStat == 'R'}">
							대기
						</c:if>
						<button type="button" name="bestOff" class="btn btn-default btn-xs" value="${item.bestNo}">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<!--===============-->
<!--     Modal     -->
<!--===============-->
<form id="categoryMoveForm" onsubmit="return false;">
	<div class="modal fade" id="categoryMoveModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel"><i class="glyphicon glyphicon-move"></i> <b>카테고리 이동</b></h4>
				</div>
				<div class="modal-body">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th class="active text-center">기존 카테고리</th>
								<td><span id="oldCategoryNm"></span></td>
							</tr>
							<tr>
								<th class="active text-center">이동 카테고리</th>
								<td>
									<select name="categoryCd" class="form-control">
										<c:forEach var="item" items="${usedCategoryList}">
											<option value="${item.categoryCd}">${item.categoryNm}</option>
										</c:forEach>
										<c:forEach var="item" items="${unusedCategoryList}">
											<option value="${item.categoryCd}">${item.categoryNm} (미사용)</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">저장</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
</form>
<!--===============-->
<!--//   Modal     -->
<!--===============-->

<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		// 베스트 예약 날짜 선택 달력
		$('input[name="bestDt"]').datetimepicker({
			controlType: 'select',
			stepMinute: 30,
			oneLine: true,
			dateFormat: 'yy-mm-dd',
			timeFormat: 'HH:mm',
			beforeShow: function() {
		        setTimeout(function(){
		            $('.ui-datepicker').css('z-index', 99999999999999);
		        }, 0);
		    }
		});
		
		// 베스트 예약 
		$('form[name="reservationForm"]').submit(function(event) {
			var $chk = $('input[name="chk"]:checked'),
				type = $('#type').val();
			
			if (!$chk || $chk.length === 0) {
				alert("예약할 이모티콘을 선택하세요.")
				return false;
			}
			
			// 지난 일시 여부 체크
			var bestDt = $(this).find('input[name="bestDt"]').val(),
				sBestDt = bestDt.replace(/[-,:, ]/g, '');
			
			var now = new Date(),
				sNow = [now.getFullYear(),
					lpad(now.getMonth()+1, 2, '0'),
					lpad(now.getDate(), 2, '0'),
					lpad(now.getHours(), 2, '0'),
					lpad(now.getMinutes(), 2, '0')
					].join('');

			console.log('lpad now.getHours()='+lpad(now.getHours(), 2, '0'));
			console.log('sBestDt='+sBestDt);
			console.log('sNow   ='+sNow);
			
			if (sBestDt <= sNow) {
				alert('과거 시점으로 예약할 수 없습니다.');
				return false;
			}

			var productNos = [];
			$chk.each(function(index) {
				productNos[index] = $(this).val();
			});

			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/products/best-reserve',
				data: {
					type: type,
					bestDt: bestDt,
					productNos: productNos
				}
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});

			return false;
		});
		
		// 미리보기
		$('a.preview').click(function(event) {
			event.preventDefault();
//			alert('준비중입니다.');
//			return false;
			
			var $this = $(this),
				productNo = $this.data('product-no'),
				userId    = $this.data('user-id'),
				companyNo = $this.data('company-no'),
				h         = $this.data('h'),
				previewServerUrl = $('#previewServerUrl').val();
			
			var popupWidth = 920,
				popupHeight = 615,
				popupX = (screen.width-popupWidth) / 2,
				popupY = (screen.height-popupHeight) / 2;
			var url = previewServerUrl+'/ppurio/editor/tostEditor.do?productNo='+productNo+'&fileSize=3&userId='+userId+'&companyNo='+companyNo+'&h='+h;
			var status = "toolbar=no, location=no, directories=no, status=no, "; 
			status +=	"menubar=no, resizable=no, scrollbars=no, width="+popupWidth+", height="+popupHeight+", left="+popupX+", top="+popupY
			
			window.open(url, 'winEditor', status);  
		});

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
		
		// 이모티콘 삭제
		$('button[name="btnProductDelete"]').click(function () {
			var $chk = $('input[name="chk"]:checked');
			
			if (!$chk || $chk.length === 0) {
				alert("삭제할 이모티콘을 선택하세요.")
				return;
			}
			
			if (!confirm("선택한 이모티콘을 삭제하시겠습니까?")) {
				return;
			}
			
			if (!confirm("삭제 후 복구되지 않습니다. 정말 삭제하시겠습니까?")) {
				return;
			}
			
			var productNos = [];
			$chk.each(function(index) {
				productNos[index] = $(this).val();
			});
			
			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/products/delete',
				data: {
					productNos: productNos
				}
			}).done(function(data) {
				if (data.resCd === 'SUCCESS') {
			        alert('삭제되었습니다.');
			        location.reload();
				} else {
			        alert(data.resMsg);
				}
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
		
		// 카테고리 이동  모달 오픈
		$('button[name="btnCategoryMove"]').click(function () {
			var $chk = $('input[name="chk"]:checked'),
				$categoryNm = $chk.siblings('input[name="categoryNm"]');
			
			if (!$chk || $chk.length === 0) {
				alert("이동할 이모티콘을 선택하세요.")
				return;
			}

			var $modal = $('#categoryMoveModal'),
				$oldCategoryNm = $modal.find('#oldCategoryNm');


			// 카테고리명 중복 제거 후 표시
			var categoryNms = [];
			$categoryNm.each(function(index) {
				categoryNms[index] = $(this).val();
			});
			var categoryNms = categoryNms.reduce(function(a, b) {
				if (a.indexOf(b) < 0 ) a.push(b);
				return a;
			},[]);
			
			$oldCategoryNm.html(categoryNms.join(', '));

			// 모달 오픈
			$modal.modal('show');
		});
		
		// 카테고리 이동 처리
		$('#categoryMoveForm').submit(function () {
			
			if (!confirm("이동하시겠습니까?")) {
				return;
			}

			var $chk = $('input[name="chk"]:checked'),
				$categoryCd = $(this).find('select[name="categoryCd"]');

			var productNos = [];
			$chk.each(function(index) {
				productNos[index] = $(this).val();
			});
			
			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/products/category-move',
				data: {
					productNos: productNos,
					categoryCd: $categoryCd.val()
				}
			}).done(function(data) {
				if (data.resCd === 'SUCCESS') {
			        alert('이동되었습니다.');
			        location.reload();
				} else {
			        alert(data.resMsg);
				}
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});

			return false;
		});
		
		// 베스트 ON (여러개)
		$('button[name="btnBestRegister"]').click(function () {
			
			if (!confirm("BEST로 설정하시겠습니까?")) {
				return;
			}

			var $chk = $('input[name="chk"]:checked'),
				type = $('#type').val();

			var productNos = [];
			$chk.each(function(index) {
				productNos[index] = $(this).val();
			});
			
			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/products/best-register',
				data: {
					type: type,
					productNos: productNos
				}
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});

		// 베스트 ON
		$('button[name="bestOn"]').click(function () {
			var $productNo = $(this),
				type = $('#type').val();
			
			$.ajax({
				method: 'POST',
				url: '/emoticon/mms/products/' + $productNo.val() + '/best',
				data: {
					type: type
				}
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
		
		// 베스트 OFF
		$('button[name="bestOff"]').click(function () {
			var $productNo = $(this),
				type = $('#type').val();
			
			$.ajax({
				method: 'DELETE',
				url: '/emoticon/mms/products/' + $productNo.val() + '/best',
				data: {
					type: type
				}
			}).done(function(data) {
				alert(data.resMsg);
				location.reload();
			}).fail(function() {
				alert('알수 없는 오류 입니다. 시스템 관리자에게 문의 바랍니다.');
			});
		});
		
		// 검색
		$('form[name="searchForm"]').submit(function () {
			var $searchKeyword = $(this).find('input[name="searchKeyword"]'),
				$searchStDt = $(this).find('input[name="searchStDt"]'),
				$searchEnDt = $(this).find('input[name="searchEnDt"]');
			
			if (!$searchKeyword.val() && !$searchStDt.val() && !$searchEnDt.val()) {
				alert('검색어나 검색일자를 입력하고 검색하세요.');
				$searchKeyword.focus();
				return false;
			}
			
			var url = $('#currUrl').val();
			
			if ($searchKeyword.val()) {
				url += '&searchType=tagNm&searchKeyword=' + $searchKeyword.val();
			}
			if ($searchStDt.val()) {
				url += '&searchStDt=' + $searchStDt.val();
			}
			if ($searchEnDt.val()) {
				url += '&searchEnDt=' + $searchEnDt.val();
			}
			
			location.href = url;
			return false;
		});
	});
</script>
