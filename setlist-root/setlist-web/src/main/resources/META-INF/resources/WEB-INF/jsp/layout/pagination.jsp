<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="imgServerUrl" value="http://mplus.ppurio.com" />

<tiles:importAttribute name="page" toName="page" ignore="true"/>
<tiles:importAttribute name="parameter" toName="parameter" ignore="true"/>

<c:if test="${not empty page}">
	
	<jsp:useBean id="pagination" class="com.daou.ems.api.domain.Pagination"/> 
	<jsp:setProperty property="page" name="pagination" value="${page}"/>
	
	<c:if test="${pagination.listCount > 0  }">
		<div id="paging">
			<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.firstPageNum-1}${parameter}"><img src="/ems-static/images/ppurio/btn_paging_first.jpg" /></a>
			<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.prevPageNaviNum-1}${parameter}"><img src="/ems-static/images/ppurio/btn_paging_before.jpg" alt="이전" /></a>
			&nbsp;
			<c:forEach var="pageNum" begin="${pagination.currentFirstPageNum }" end="${pagination.currentLastPageNum }">
				<c:if test="${pagination.pageNum eq pageNum }">
					<span class="cur">${pageNum}</span>
				</c:if>
				<c:if test="${pagination.pageNum != pageNum }">
				<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageNum-1 }${parameter}"><span>${pageNum}</span></a>
				</c:if>
			</c:forEach>
			&nbsp;
			<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.nextPageNaviNum-1}${parameter}"><img src="/ems-static/images/ppurio/btn_paging_next.jpg" alt="다음" /></a>
			<a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.lastPageNum-1}${parameter}"><img src="/ems-static/images/ppurio/btn_paging_last.jpg" alt="끝" /></a>
		</div>
	</c:if>
</c:if>
