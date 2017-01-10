<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>
<div id="wrap">
	<!--
	<div id="search_result">
		<p>
			<strong>'${param.searchKeyword}'</strong>에 대한
			<c:if test="${productPageResult.totalElements > 0}">
				<b>검색결과입니다.</b>
			</c:if>
			<c:if test="${productPageResult.totalElements == 0}">
				<b>검색결과가 없습니다.</b>
			</c:if>
			<br />
			원하시는 컨텐츠가 없으면 다른 검색어를 입력해주세요.
		</p>
		<form id="searchFrm" method="get" accept-charset="UTF-8">
			<input type="hidden" name="companyNo" value="${param.companyNo}" />
			<input type="hidden" name="userId" value="${param.userId}" />
			<input type="hidden" name="h" value="${param.h}" />
			<input type="hidden" name="searchType" value="tagNm" />
			<input type="text" name="searchKeyword" class="search_box" maxlength="20" value="${param.searchKeyword}" />
			<input type="image" src="/ems-static/images/ppurio/btn_search_submit.gif" alt="검색" />
		</form>
	</div>
	-->

	<div id="tost_list">
		<c:if test="${productPageResult.totalElements > 0}">
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
		</c:if>
		<c:if test="${productPageResult.totalElements == 0}">
			<c:forEach var="recommend" items="${recommendProductList}">
			<h3>
				${recommend.category.categoryNm}
				<a href="/emoticon/mms/products?companyNo=${param.companyNo}&userId=${param.userId}&h=${param.h}&categoryCd=${recommend.category.categoryCd}" title="${recommend.category.categoryNm} 결과 더보기" class="more">더보기</a>
			</h3>
			<ul class="recommend">
				<c:forEach var="item" items="${recommend.productList}">
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
			</c:forEach>
		</c:if>
	</div>
	
    <ems:paramHandler skipParams="page" suffix="&" var="parameter" />
    <tiles:insertDefinition name="pageNavigation">
        <tiles:putAttribute name="page" value="${productPageResult }"/>
        <tiles:putAttribute name="parameter" value="${parameter}"/>
    </tiles:insertDefinition>
</div>

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
		
		// 템플릿 클릭 (에디터 팝업)
		$('ul.list li a').click(function(event) {
			event.preventDefault();
			var productNo = $(this).find('input[name="productNo"]').val();
			//openImageEditor(productNo, '3');
			parent.mmsplusImoticonClick(productNo, '3');
		});
	});
</script>
