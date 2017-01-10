<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>
<div id="wrap">
	<div id="sub_category2">
		<ol>
			<c:forEach var="item" varStatus="status" items="${categoryList}">
				<li class="${item.categoryCd == selectedCategoryCd ? 'on' : ''}"> <a href="?categoryGroupCd=${param.categoryGroupCd}&categoryCd=${item.categoryCd}&companyNo=${param.companyNo}&userId=${param.userId}&h=${param.h}" title="${item.categoryNm}">${item.categoryNm}</a></li>
			</c:forEach>
		</ol>
	</div>
	<div id="tost_list">
		<div class="clearboth"></div>
	
		<form name="recvForm" method="get">
			<ul class="list">
				<c:forEach var="item" items="${productPageResult.content}">
				<li>
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
	</div>
	
    <ems:paramHandler skipParams="page" suffix="&" var="parameter" />
    <tiles:insertDefinition name="pageNavigation">
        <tiles:putAttribute name="page" value="${productPageResult }"/>
        <tiles:putAttribute name="parameter" value="${parameter}"/>
    </tiles:insertDefinition>
</div>

<script src="/webjars/jquery/3.1.1/dist/jquery.min.js"></script>
<script type="text/javascript" src="/ems-static/js/comm.js?ver=4"></script>
<script type="text/javascript">
	$(document).ready(function() {
		"use strict";
		
		// 하위 브라우저를 위한 placeholder 설정
		$('input[name="searchKeyword"]').placeholder();
		
		// 특수문자 입력 제한
		$('input[name="searchKeyword"]').keyup(function() {
			inputCheckSpecial($(this)[0]);
		});

		// 검색
		$('#searchFrm').submit(function() {
			var $searchKeyword = $(this).find('input[name="searchKeyword"]');

			if (!$searchKeyword.val() || $searchKeyword.val() === $searchKeyword.prop('placeholder')) {
				alert('검색어를 입력 해주세요.');
				$searchKeyword.focus();
				return false;
			}
		});
		
		// 내 PC 사진 첨부 클릭
		$('a#blankImageEditor').click(function(event) {
			openImageEditor('', '3');
		});
		
		// 템플릿 클릭 (에디터 팝업)
		$('ul.list li a').click(function(event) {
			event.preventDefault();
			var productNo = $(this).find('input[name="productNo"]').val();

			//openImageEditor(productNo, '3');
			parent.mmsplusImoticonClick(productNo, '3');
		});
	});
</script>
