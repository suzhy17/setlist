<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title>가이드</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
	#guide {width:550px;}
	#guide div.category {height:14px; padding:11px 0 9px 24px; background:#000;}
	#guide div.category a {float:left; margin-right:13px; padding-right:13px; border-right:1px solid #404040;}
	#guide div.category a.end {margin:0; padding:0; border:none;}
	#guide div.con {clear:both;}
</style>
</head>
<body onload="window.resizeTo(550,680)">
<div id="guide">
	<div class="category">
		<a href="javascript:;" onclick="convert_guide(1)"><img id="guide_tab_1" src="/ems-static/images/ppurio/tab_guide_sero_01_on.jpg" alt="꾸미기?" /></a>
		<a href="javascript:;" onclick="convert_guide(2)"><img id="guide_tab_2" src="/ems-static/images/ppurio/tab_guide_sero_02_off.jpg" alt="텍스트 추가" /></a>
		<a href="javascript:;" onclick="convert_guide(3)"><img id="guide_tab_3" src="/ems-static/images/ppurio/tab_guide_sero_03_off.jpg" alt="그림문자 만들기" /></a>
		<a href="javascript:;" onclick="convert_guide(4)"><img id="guide_tab_4" src="/ems-static/images/ppurio/tab_guide_sero_04_off.jpg" alt="사진올리기" /></a>
		<a href="javascript:;" onclick="convert_guide(5)"><img id="guide_tab_5" src="/ems-static/images/ppurio/tab_guide_sero_05_off.jpg" alt="캡쳐올리기" /></a>
		<a href="javascript:;" onclick="convert_guide(6)" class="end"><img id="guide_tab_6" src="/ems-static/images/ppurio/tab_guide_sero_06_off.jpg" alt="미리보기" /></a>
	</div>
	
	<div class="con">
		<p id="guide_1">
			<img src="/ems-static/images/ppurio/img_guide_sero_01.jpg" />
		</p>
		<p id="guide_2" class="displayno">
			<img src="/ems-static/images/ppurio/img_guide_sero_02.jpg" />
		</p>
		<p id="guide_3" class="displayno">
			<img src="/ems-static/images/ppurio/img_guide_sero_03.jpg" />
		</p>
		<p id="guide_4" class="displayno">
			<img src="/ems-static/images/ppurio/img_guide_sero_04.jpg" />
		</p>
		<p id="guide_5" class="displayno">
			<img src="/ems-static/images/ppurio/img_guide_sero_05.jpg" />
		</p>
		<p id="guide_6" class="displayno">
			<img src="/ems-static/images/ppurio/img_guide_sero_06.jpg" />
		</p>
	</div>
</div>

<script type="text/javascript">
	function convert_guide(num) {
		for(i=1; i<=6; i++) {
			document.getElementById('guide_tab_'+i).src = document.getElementById('guide_tab_'+i).src.replace('_on', '_off');
			document.getElementById('guide_'+i).style.display = 'none';
		}
		document.getElementById('guide_tab_'+num).src = document.getElementById('guide_tab_'+num).src.replace('_off', '_on');
		document.getElementById('guide_'+num).style.display = 'block';
	}
</script>

</body>
</html>