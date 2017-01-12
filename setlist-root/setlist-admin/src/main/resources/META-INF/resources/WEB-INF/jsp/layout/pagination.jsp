<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:importAttribute name="page" toName="page" ignore="true"/>
<tiles:importAttribute name="parameter" toName="parameter" ignore="true"/>

<c:if test="${not empty page}">
    
    <jsp:useBean id="pagination" class="com.daou.ems.admin.domain.Pagination"/> 
    <jsp:setProperty property="page" name="pagination" value="${page}"/>
    
    <c:if test="${pagination.listCount > 0  }">
	    <div class="text-center clear-both">
			<ul class="pagination pagination-sm">
		        <li><a class="glyphicon glyphicon-backward" href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.firstPageNum-1}${parameter}"></a></li>
			    <li><a class="glyphicon glyphicon-chevron-left" href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.prevPageNaviNum-1}${parameter}"></a></li>
			    <c:forEach var="pageNum" begin="${pagination.currentFirstPageNum }" end="${pagination.currentLastPageNum }">
		            <li class="${pagination.pageNum eq pageNum ? 'active' : '' }">
		                <a href="${requestScope['javax.servlet.forward.request_uri']}?page=${pageNum-1 }${parameter}" title="Page ${pageNum}">
		                    ${pageNum }
		                    <c:if test="${pagination.pageNum eq pageNum }">
		                        <span class="sr-only">(current)</span>
		                    </c:if>
		                </a>
		            </li>
				</c:forEach>
			    <li><a class="glyphicon glyphicon-chevron-right" href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.nextPageNaviNum-1}${parameter}"></a></li>
			    <li><a class="glyphicon glyphicon-forward" href="${requestScope['javax.servlet.forward.request_uri']}?page=${pagination.lastPageNum-1}${parameter}"></a></li>
			</ul>
		</div>
	</c:if>
</c:if>
