<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://daou.com/ems" prefix="ems" %>
<div id="wrap">
	<div id="tost_list" class="bgn">
		<fieldset class="fl">
			<a href="#" id="blankImageEditor" title="내 PC 사진 첨부">
				<img src="/ems-static/images/ppurio/img_recommend_banner.gif" />
			</a>
		</fieldset>
	
		<form id="searchFrm" method="get" action="/emoticon/mms/products/search">
			<input type="hidden" name="companyNo" value="${param.companyNo}" />
			<input type="hidden" name="userId" value="${param.userId}" />
			<input type="hidden" name="h" value="${param.h}" />
			<fieldset class="fr">
				<input type="hidden" name="searchType" value="tagNm" />
				<input type="text" name="searchKeyword" class="common_box" style="" maxlength="20" placeholder="검색어를 입력하세요."
					 /><button type="submit" style="border: 0; background: transparent">
					<img src="/ems-static/images/ppurio/btn_minisearch_submit.jpg" alt="검색" title="검색" />
				</button>
			</fieldset>
		</form>
	
		<div class="clearboth"></div>
	
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
