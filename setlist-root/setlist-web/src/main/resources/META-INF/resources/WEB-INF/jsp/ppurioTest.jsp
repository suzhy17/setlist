<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ASP-뿌리오</title>
</head>
<body>
<script type="text/javascript">
	function mmsplusSetImage(productNo, url) {
		alert('mmsplusSetImage('+productNo + ', ' + url + ')');
	}

	function mmsplusSaveImage(productNo, url) {
		alert('mmsplusSaveImage('+productNo + ', ' + url + ')');
	}
	
	function mmsplusImoticonClick(productNo, url) {
		openImageEditor(productNo, '3');
	}
</script>	
<div style="float:left; margin-right:10px;">
	<a href="/ppurio/tostshop/tostMainBestList.do?locationCd=7002008PPU" target="tost_page">메인 추천 토스트</a>
	<br><br>
	<a href="/emoticon/mms/products/best?companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" target="tost_page">추천 토스트</a>
	<br><br>
	<a href="/emoticon/mms/products?categoryGroupCd=COMPANY&companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" target="tost_page">기업 토스트</a>
	<br><br>
	<a href="/emoticon/mms/products?categoryGroupCd=PICTURE&companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" target="tost_page">그림 토스트 </a>
	<br><br>
	<a href="/emoticon/mms/products?companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" target="tost_page">일반 토스트 </a>
	<br><br>
	<a href="/emoticon/mms/products/storage?companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" target="tost_page">만든 토스트 </a>
	<br><br>
	<a href="/emoticon/mms/guide" target="tost_page">가이드 </a>
	<br>
</div>
<div style="float:left; text-align:left;">
	<iframe name="tost_page" id="tost_page" src="/emoticon/mms/products/best?companyNo=100089&userId=suzhy&h=bc588dc1cae54899d205f11ccf15ea27" width="760" height="1300" style="border:1px solid #000;"></iframe>
</div>
<script src="/webjars/jquery/3.1.1/dist/jquery.min.js"></script>

<script type="text/javascript">
	$(function(){
		/*
		$.ajax({
			method: 'POST',
			url: 'http://devmplus.ppurio.com:10080/api/storage/delete',
			data: {
				productNo: '19000001'
			}
		}).done(function(data) {
			if (data.resCd === 'SUCCESS') {
		        alert('삭제되었습니다.');
		        location.reload();
			} else {
		        alert(data.resMsg);
			}
		}).fail(function() {
	        alert('실패되었습니다.');
		});
		*/
	});
</script>
</body>
</html>