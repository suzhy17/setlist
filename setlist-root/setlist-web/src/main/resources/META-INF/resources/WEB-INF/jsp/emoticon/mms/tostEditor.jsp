<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>뿌리오 포토 에디터</title>
<script src="/ems-static/js/AC_RunActiveContent.js"></script>
</head>

<body style="background-color: white; margin: 0px; padding: 0px; overflow-x:hidden; overflow-y:hidden;">
<script type="text/javascript">
	AC_FL_RunContent(
		'codebase', 'http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0',
		'width', '920',
		'height', '630',
		'src', '/tosteditor/ImageEditer_cs4_ppurio?product_no=${tost.productNo}&image_size=${tost.imageSize}&logo_no=${tost.logoNo}&contentsKey=&token=&expires=&flow_path=${tost.flowPath}&content_nm=${tost.contentNm}&tost_type=${tost.tostType}',
		'quality', 'high',
		'pluginspage', 'http://www.adobe.com/go/getflashplayer',
		'align', 'middle',
		'play', 'true',
		'loop', 'true',
		'scale', 'showall',
		'wmode', 'window',
		'devicefont', 'false',
		'id', 'ImageEditer_cs4',
		'bgcolor', '#ffffff',
		'name', 'ImageEditer_cs4',
		'menu', 'true',
		'allowFullScreen', 'false',
		'allowScriptAccess', 'always',
		'movie', '/tosteditor/ImageEditer_cs4_ppurio?product_no=${tost.productNo}&image_size=${tost.imageSize}&logo_no=${tost.logoNo}&contentsKey=&token=&expires=&flow_path=${tost.flowPath}&content_nm=${tost.contentNm}&tost_type=${tost.tostType}',
		'salign', ''
	);

	function closeUrl(method, url) {

		var params = url.split('|');

		if (method == "SAVE") {
			opener.parent.mmsplusSaveImage(params[0], params[1]);
		} else {
			opener.parent.mmsplusSetImage(params[0], params[1]);
		}
		self.close();
	}
</script>
<noscript>
	<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0" width="920" height="630" id="ImageEditer_cs4" align="middle">
		<param name="allowScriptAccess" value="always" />
		<param name="allowFullScreen" value="false" />
		<param name="movie" value="/tosteditor/ImageEditer_cs4_ppurio.swf?product_no=${tost.productNo}&image_size=${tost.imageSize}&logo_no=${tost.logoNo}&contentsKey=&token=&expires=&flow_path=${tost.flowPath}&content_nm=${tost.contentNm}&tost_type=${tost.tostType}" />
		<param name="quality" value="high" />
		<param name="bgcolor" value="#ffffff" />
		<embed src="/tosteditor/ImageEditer_cs4_ppurio.swf?product_no=${tost.productNo}&image_size=${tost.imageSize}&logo_no=${tost.logoNo}&contentsKey=&token=&expires=&flow_path=${tost.flowPath}&content_nm=${tost.contentNm}&tost_type=${tost.tostType}"
			quality="high"
			bgcolor="#ffffff"
			width="920"
			height="630"
			name="ImageEditer_cs4"
			align="middle"
			allowScriptAccess="sameDomain"
			allowFullScreen="false"
			type="application/x-shockwave-flash"
			pluginspage="http://www.adobe.com/go/getflashplayer" />
	</object>
</noscript>
<div id="activeXarea"></div>
</body>
</html>
