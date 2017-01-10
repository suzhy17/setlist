<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>
<div id="wrap">
	<form name="recvForm" id="tost_list" class="bgn" method="" action="">
		<h3 class="fl">
			<img src="/ems-static/images/ppurio/subtitlt_mms_cart.jpg" alt="MMS+보관함" /> 
		</h3>
		<div class="fr mt3">
			<a href="#" id="chkAll" title="전체선택"><img src="/ems-static/images/ppurio/btn_allcheck.jpg" alt="전체선택" /></a>
			<a href="#" id="delete" title="삭제"><img src="/ems-static/images/ppurio/btn_del.jpg" alt="삭제" /></a>
		</div>
	
		<ul class="list">
			<c:forEach var="item" items="${productPageResult.content}">
			<li>
				<span class="chk"><input type="checkbox" name="chk" value="${item.productNo}" /></span>
				<a href="#">
					<input type="hidden" name="productNo" value="${item.productNo}" />
					<span class="photo">
						<img src="${item.imgPath}" alt="${item.contentNm}" title="${item.contentNm} 편집" class="photo" style="max-width: 133px; max-height: 199px;" />
					</span>
					<span class="frame"></span>
				</a>
			</li>
			</c:forEach>
			<li class="clearboth"></li>
		</ul>
	</form>
	
    <ems:paramHandler skipParams="page" suffix="&" var="parameter" />
    <tiles:insertDefinition name="pageNavigation">
        <tiles:putAttribute name="page" value="${productPageResult }"/>
        <tiles:putAttribute name="parameter" value="${parameter}"/>
    </tiles:insertDefinition>
</div>

<script src="/webjars/jquery/3.1.1/dist/jquery.min.js"></script>
<script src="/ems-static/js/comm.js?ver=4"></script>
<script type="text/javascript">
$(document).ready(function() {
	"use strict";
	
	// 템플릿 클릭 (에디터 팝업)
	$('ul.list li a').click(function(event) {
		event.preventDefault();
		var productNo = $(this).find('input[name="productNo"]').val();
		//openImageEditor(productNo, '3');
		parent.mmsplusImoticonClick(productNo, '3');
	});

	// 전체 선택
	var isChkAll = false;
	$('#chkAll').click(function(event) {
		event.preventDefault();
		
		isChkAll = !isChkAll;
		
		$('input[name="chk"]').prop('checked', isChkAll);
	});
	
	$('#delete').click(function(event) {
		event.preventDefault();
		
		var $chk = $('input[name="chk"]:checked');
		
		if (!$chk || $chk.length === 0) {
			alert("삭제할 컨텐츠를 선택하세요.")
			return;
		}
		
		if (!confirm("선택한 컨텐츠를 삭제하시겠습니까?")) {
			return;
		}
		
		var productNos = [];
		$chk.each(function(index) {
			productNos[index] = $(this).val();
		});
		
		$.ajax({
			method: 'POST',
			url: '/emoticon/mms/products/storage/delete',
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
		});
	});
});
</script>
