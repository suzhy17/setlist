(function($) {
    $.fn.placeholder = function() {
    	var msg = $(this).prop('placeholder');
    	if (!$(this).val()) {
            $(this).val(msg);
    	}
        $(this).bind({
            focus: function() {
                if( $(this).val() === msg ) {
                    $(this).val('');
                }
            },
            blur: function() {
                if(!$(this).val()) {
                    $(this).val(msg);
                }
            }
        });
    };
})(jQuery);

//숫자 체크
function checkNum(NUM){
    var val = parseInt(NUM);
    return (isNaN(val)) ? false : true;
}

//----------------------------- 이미지 사진 확대(레이어) --------------------------
function ImageOn(n,top,left){										
	document.all[n].style.pixelLeft=event.clientX+document.body.scrollLeft + left 
	document.all[n].style.pixelTop=event.clientY+document.body.scrollTop + top 
	document.all[n].style.visibility="visible"
}


function ImageOut(n){ 
	document.all[n].style.visibility="hidden"
} 

//----------------------------- 이미지 축소및 링크 --------------------------
function resizeImg() {
	var max_width = 800;	
	
	for(var i=0;i<document.all.length;i++) {
		var imgTag = document.all[i];
		if(imgTag.tagName.toLowerCase() == "img") {
			var imgSrc = imgTag.src;
			var imgWidth = imgTag.width;
			var imgHeight = imgTag.height;
			var chgWidth = imgWidth;
			var chgHeight = imgHeight;
			
			if(imgWidth > max_width) {
				chgWidth = max_width;
				chgHeight = imgHeight * max_width / imgWidth;
				
				imgTag.width = chgWidth;
				imgTag.height = chgHeight;
				
				imgTag.style.cursor = "hand";
				imgTag.alt = "View All Images";
				imgTag.onclick = new Function("viewImg('" + imgSrc + "', " + imgWidth + "," + imgHeight + ");"); 
				
			}
			
			
		}
	}
}

function viewImg(img_src, temp3, temp2) {

	var winWidth = temp3+62;
	var winHeight = temp2+120;
	var moveWidth = 0;
	var moveHeight = 0;
	var boundedWidth = parseInt(((screen.width) * 2 ) / 3);
	var boundedHeight = parseInt(((screen.height) * 2 ) / 3);
	
	if(boundedWidth < winWidth || boundedHeight < winHeight){
		if(boundedWidth < winWidth){
			winWidth = boundedWidth;  
		}else{
			winWidth = winWidth
		}
		if(boundedHeight < winHeight){
			winHeight = boundedHeight;  
		}else{
			winHeight = winHeight;  
		}
		//window.resizeTo(winWidth,winHeight);
	}
	moveWidth = parseInt((parseInt(screen.width) - winWidth)/2);
	moveHeight = parseInt((parseInt(screen.height) - winHeight)/2);
	//window.moveTo(moveWidth,moveHeight);
	

	var imgWin = window.open("", "viewImg", "width=" + winWidth + ",height=" + winHeight + ",left=" + moveWidth + ",top=" + moveHeight + ",scrollbars=yes");
	imgWin.document.write("<img src='" + img_src + "' alt='클릭하면 창이 닫힙니다.' onClick='window.close()' style='cursor:hand'>");
}

//--------------------------------- trim() -----------------------------------------
function trim (strSource) {
	re = /^\s+|\s+$/g;

	return strSource.replace(re, '');
}

//-------------------------------프레임 리사이즈 -----------------------------------
function resizeFrame(FN) {

	var FrameName = eval(FN);
	var HT = FrameName.document.body.scrollHeight;
	if (HT > 0) {
		document.all[FN].style.height = HT;
	}
}

//-------------------------------  이미지 체크  -----------------------------------
function chkIMG(File){

	var ext = File.substring(File.lastIndexOf(".") + 1);
	if(ext.toLowerCase().match(/(jpg|gif|png)/)){
		return true;
	}else{
		return false;
	}
}

function scView(URL,W,H){

	if(W && H){
		window.open(URL,"","width=" + W + ",height=" + H + ",scrollbars=yes")
	}else{
		window.open(URL,"","width=660,height=780,scrollbars=yes")
	}

}

function openWindow(popupWidth, popupHeight, url)
{

	var popupX = (screen.width-popupWidth) / 2;
	var popupY = (screen.height-popupHeight) / 2;
	var status = "toolbar=no, location=no, directories=no, status=no, "; 
	status +=	"menubar=no, resizable=no, scrollbars=no, width="+popupWidth+", height="+popupHeight+", left="+popupX+", top="+popupY
	
	window.open(url, "", status);  
}

function openWindowEditor(popupWidth, popupHeight, url)
{
	popupWidth = 920;
	popupHeight = 615;
	var popupX = (screen.width-popupWidth) / 2;
	var popupY = (screen.height-popupHeight) / 2;
	var status = "toolbar=no, location=no, directories=no, status=no, "; 
	status +=	"menubar=no, resizable=no, scrollbars=no, width="+popupWidth+", height="+popupHeight+", left="+popupX+", top="+popupY
	
	window.open(url, "winEditor", status);  
}


function openWindowPopup(popupWidth, popupHeight, url)
{
	var popupX = (screen.width-popupWidth) / 2;
	var popupY = (screen.height-popupHeight) / 2;
	var status = "toolbar=no, location=no, directories=no, status=no, "; 
	status +=	"menubar=no, resizable=no, scrollbars=1, width="+popupWidth+", height="+popupHeight+", left="+popupX+", top="+popupY
	
	window.open(url, "", status);  
}

//-------------------------------  게시판 리스트  -----------------------------------
function sendForm(page){
	if( trim(document.recvForm.searchString.value) ){
		searchPost(page);
	}
}
function readPost(brdSrno,page){
	document.recvForm.brdSrno.value = brdSrno;
	document.recvForm.action = page + ".do";
	document.recvForm.submit();
}
function readEventPost(evtSrno,brdSrno,page){
	document.recvForm.evtSrno.value = evtSrno;
	document.recvForm.brdSrno.value = brdSrno;
	document.recvForm.action = page + ".do";
	document.recvForm.submit();
}	
function writePost(page){
	document.recvForm.action = page + ".do";
	document.recvForm.submit();
}
function searchPost(page){
	document.recvForm.p.value = 1;
	document.recvForm.action = page + ".do";
	document.recvForm.submit();
}
function pagePost(p,page){
	document.recvForm.p.value = p;
	document.recvForm.action = page + ".do";
	document.recvForm.submit();
}

function downLoad(N){
	document.location.href = "/downLoad.do?filSrno=" + N;
}

//-------------------------------  입력 데이타 체크  -----------------------------------

// 영문 한글 문자열 길이
function calBytes(str)
{
  var tcount = 0;

  var tmpStr = new String(str);
  var temp = tmpStr.length;

  var onechar;
  for ( k = 0; k < temp; k++ )
  {
    onechar = tmpStr.charAt(k);
    if (escape(onechar).length > 4)
    {
      tcount += 2;
    }
    else
    {
      tcount += 1;
    }
  }

  return tcount;
}

//한글이 있을경우 true
function isInKorean(str)
{
	var tmpStr = new String(str);
	var temp = tmpStr.length;
	var result = false;
	var onechar;
	for ( k = 0; k < temp; k++ )
	{
	    onechar = tmpStr.charAt(k);
	    
	    if (escape(onechar).length > 4)
	    {
	    	return true;
	    }
	}

	return result;
}

// 한글이외의 캐릭터가 있을경우 false
// 한자나 숫자 영문의 경우 false 
function checkKoreanOnly( koreanChar ) 
{

	if ( koreanChar == null ) 
		return false ;

	for(var i=0; i < koreanChar.length; i++)
	{ 

		var c=koreanChar.charCodeAt(i); 

		//( 0xAC00 <= c && c <= 0xD7A3 ) 초중종성이 모인 한글자 
		//( 0x3131 <= c && c <= 0x318E ) 자음 모음 

		if( !( ( 0xAC00 <= c && c <= 0xD7A3 ) || ( 0x3131 <= c && c <= 0x318E ) ) ) 
		{      
			return false ; 
		}
	}  
	return true ;
}


// 영문 이외의 캐릭터가 있을경우 false 
function checkEnglishOnly( englishChar ) 
{  
	if ( englishChar == null ) 
		return false ;
    
	for( var i=0; i < englishChar.length;i++)
	{          
		var c=englishChar.charCodeAt(i);       
		
		if( !( (  0x61 <= c && c <= 0x7A ) || ( 0x41 <= c && c <= 0x5A ) ) ) 
		{         
			return false ;       
		}
	}      
	return true ;
} 


// 숫자 이외의 캐릭터가 있을경우 false 
function checkDigitOnly( digitChar ) 
{  
	if ( digitChar == null ) 
		return false ;
    
	for(var i=0; i < digitChar.length; i++)
	{          
		var c=digitChar.charCodeAt(i);       
		if( !(  0x30 <= c && c <= 0x39 ) ) 
		{         
			return false ;       
		}
	}      
	return true ;
}

// 숫자 이외의 캐릭터가 있을경우 초기화
function fSetNumType(obj)
{
 // 사용예 : <input type="text" name="text" onKeyUp="javascript:numOnly(this);"> 
	if (event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39) 
		return; 
	
	var returnValue = ""; 
	
	for (var i = 0; i < obj.value.length; i++)
	{ 
		if (obj.value.charAt(i) >= "0" && obj.value.charAt(i) <= "9")
		{ 
			returnValue += obj.value.charAt(i); 
		}
		else
		{ 
			returnValue += ""; 
		} 
	}
	obj.focus(); 
	obj.value = returnValue; 
} 



function checkEmail(email)
{
	 if(email.indexOf("@") < 0)
	 {
		 return false;
	 }
	 
	 if(email.indexOf(".") < 0)
	 {
		 return false;
	 }
	 
	 return true;
}

//이메일 셀렉트박스로 선택
function changeEmail()
{
    var frm = document.regform;
    if(frm.emailService1.value == "")
    {
    	frm.email2.value = "";
        frm.email2.disabled = false;
        frm.email2.focus();
    }else{
		frm.email2.value = frm.emailService1.value;
		frm.email2.disabled = true;
	}
}

//우편번호 찾아서 값 넣어주기.
function setZipCode(zip, addr)
{
	var zipCode = document.getElementById("zipCode");
	var userAddress1 = document.getElementById("userAddress1");
		
	zipCode.value = zip;
	userAddress1.value = addr;
}

//아이디 값 넣어주기.
function setId(id)
{
	var userId = document.getElementById("userId");
	var idCheck = document.getElementById("idCheck");
		
	userId.value = id;
	idCheck.value = "Y";
	userId.disabled = true;
}

function check_jumin() {
    var chk =0;
    var frm = document.regform;
    var yy = frm.userResno1.value.substring(0,2);
    var mm = frm.userResno1.value.substring(2,4);
    var dd = frm.userResno1.value.substring(4,6);
    var sex = frm.userResno2.value.substring(0,1);

    // 주민등록번호를 자리수에 맞게 입력했는지 체크
    if (frm.userResno2.value.split(" ").join("") == "") {
        alert ('주민등록번호를 입력하십시오.');
        frm.userResno1.focus();
        return false;
    }
    if (frm.userResno1.value.length!=6) {
        alert ('주민등록번호 앞자리를 입력하십시오');
        frm.userResno1.focus();
        return false;
    }
    if (frm.userResno2.value.length != 7 ) {
        alert ('주민등록번호 뒷자리를 입력하십시오.');
        frm.userResno2.focus();
        return false;
    }
    if (isNaN(frm.userResno1.value) || isNaN(frm.userResno2.value)) {
        frm.userResno1.value = ""
        frm.userResno2.value = ""
        alert('주민등록번호는 숫자만 가능합니다.');
        return false;


    }
    if ((frm.userResno1.value.length!=6)||(mm <1||mm>12||dd<1)){
        frm.userResno1.value = ""
        alert ('주민등록번호 앞자리가 잘못되었습니다.');
        frm.userResno1.focus();
        return false;
    }
    if ((sex != 1 && sex !=2 )||(frm.userResno2.value.length != 7 )){
        frm.userResno2.value = ""
        alert ('주민등록번호 뒷자리가 잘못되었습니다.');
        frm.userResno2.focus();
        return false;
    }

    for (var i = 0; i <=5 ; i++) {
        chk = chk + ((i%8+2) * parseInt(frm.userResno1.value.substring(i,i+1)))
    }
    for (var i = 6; i <=11 ; i++) {
        chk = chk + ((i%8+2) * parseInt(frm.userResno2.value.substring(i-6,i-5)))
    }

    chk = 11 - (chk %11)
    chk = chk % 10

    if (chk != frm.userResno2.value.substring(6,7)) {
        frm.userResno1.value = "";
        frm.userResno2.value = "";
        alert ('맞지 않는 주민등록번호입니다.');
        frm.userResno1.focus();
        return false;
    }

    return true;
}

//사업자번호 체크
function checkBizcode(num) {
    var reg = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/;
    if (!reg.test(num)) return false;
    num = RegExp.$1 + RegExp.$2 + RegExp.$3;
    var cVal = 0;
    for (var i=0; i<8; i++) {
        var cKeyNum = parseInt(((_tmp = i % 3) == 0) ? 1 : ( _tmp  == 1 ) ? 3 : 7);
        cVal += (parseFloat(num.substring(i,i+1)) * cKeyNum) % 10;
    }
    var li_temp = parseFloat(num.substring(i,i+1)) * 5 + '0';
    cVal += parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2));
    return (parseInt(num.substring(9,10)) == 10-(cVal % 10)%10);
}

//사업자 형태 구분
//틀릴수도 있음 --;
function getBiztype(num) {
    var reg = /([0-9]{3})-?([0-9]{2})-?([0-9]{5})/;
    if (!reg.test(num)) return false;
    num = RegExp.$2;

    if(num<80) return '개인사업자';
    else if(num>=90) return '면세사업자';
    else if(num==81 || num==86 || num==87) return '법인본점';
    else if(num==83) return '국가';
    else if(num== 85) return '지점법인';
    else return false;
}

//법인번호 체크
function checkCorpcode(num) {
    var reg = /([0-9]{6})-?([0-9]{7})/;
    if (!reg.test(num)) return false;
    num = RegExp.$1 + RegExp.$2;
    var cVal = 0;
    for (var i=0; i<12; i++) {
        var cKeyNum = parseInt(((_tmp = i % 2) == 0) ? 1 : 2);
        cVal += parseFloat(num.substring(i,i+1)) * cKeyNum;
    }
    cVal = '0' + cVal;

    var prty = (cVal.length==3) ? parseInt(10- cVal.substring(2, 3)) : parseInt(10- cVal.substring(3, 4));
    return (parseInt(num.substring(12,13))==prty);
}

//실예제
function checkBiz(){
     var frm = document.regform;
    var num = frm.officeResno1.value + frm.officeResno2.value + frm.officeResno3.value;
    var istype='';
    if(num && checkBizcode(num) && getBiztype(num)) ;
    else alert(num+' : 사업자 번호가 잘못되었습니다.');
}
function checkCorp(){
    var num = fn.corpcode.value;
    if(!num || !checkCorpcode(num)) alert(num+' : 법인번호가 잘못되었습니다.');
}
function checkIdChar( englishChar ) 
{  

	if ( englishChar == null ) 
		return false ;
    
	for( var i=0; i < englishChar.length;i++)
	{          
		var c=englishChar.charCodeAt(i);       
		
		if( !( (  0x61 <= c && c <= 0x7A ) || 0x30 <= c && c <= 0x39) ) 
		{         
			return false ;       
		}
	}
	
	return true ;
} 

	//로그인체크
	function isLogin(userNo)
	{
		if(userNo == "")
		{
			if(confirm("로그인이 필요한 페이지입니다.\n\n로그인 페이지로 이동하시겠습니까 ?"))
				openWindow('500','330','https://www.mtost.net/login/loginForm.do');
			else
				return;
		}
	}
	
	
	/*
*	입력되는 문자 길이 체크
*/
function checkLen(maxlen,field)
{
	var temp; //들어오는 문자값...
	var msglen;
	msglen = maxlen*2;
	var value= field.value;
	
	l =  field.value.length; 
	tmpstr = "" ;
	
	if (l == 0)
	{
	 value = maxlen*2;
	}
	else 
	{
	    for(k=0;k<l;k++)
	    {
			temp =value.charAt(k);
			
			if (escape(temp).length > 4)
				msglen -= 2;
			else
				msglen--;
			if(msglen < 0) 
			{
				alert("총 영문 "+(maxlen*2)+"자 한글 " + maxlen + "자 까지 등록하실수 있습니다.");
				field.value= tmpstr;
				break;
			}
			else 
			{
				tmpstr += temp;
			}
		}
	}
}	

function textCounter(frm, theField, theCharCounter, maxChars)
{
    var strCharCounter = 0;
    var intLength = 0;
 
    if(theField != null)
    	intLength = theField.value.length;
    	
	for (var i = 0; i < intLength; i++)
	{
	    var charCode = theField.value.charCodeAt(i);
		//한글일 경우
	    if (charCode > 128)        {
	            strCharCounter += 2;
	    } else {
	            strCharCounter++;
	    }
	
	    if(strCharCounter < (maxChars+1)) {
	            theCharCounter.value = strCharCounter;
	    } else {
	            eval("alert('한글" + maxChars/2 + ", 영문" + maxChars+ "자 제한입니다. 초과된 문자는 잘립니다.')");
	            if(!cutStr(theField, i, theCharCounter, maxChars)) {
	                    alert("문자열 커트 함수가 작동되지 않습니다.");
	            }
	            break;
	    }
	}
}
	
// input value control
function vContr2(obj) {
	document.getElementById(obj).className='text2 imp';
}	

function commLink(linkUrl){

	if(linkUrl == '0'){
		opener.document.location.href= "/tostshop/tostCompanyDetailList.do?menuCd=30401000&c=1";
	}else if(linkUrl == '1'){
		opener.document.location.href= "/tostshop/tostGenenalDetailList.do?menuCd=30402000&c=1";
	}else if(linkUrl == '2'){
		opener.document.location.href= "/tostshop/tostLogoList.do";
	}
}


function slidMenu(num, obj) {
	var id_num = 'subCategory_'+num;
	/*for(i=1; i<15; i++) {
		if(!document.getElementById('subCategory_'+i)) break;
		document.getElementById('subCategory_'+i).style.display = "none";
		obj.className = '';
	}*/
	if(document.getElementById(id_num).style.display != 'block') {
		document.getElementById(id_num).style.display = "block";
		obj.className = 'on'; 
	} else {
		document.getElementById(id_num).style.display = "none";
		obj.className = ''; 
	}
}

function openImageEditor(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','630', '/ppurio/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorXroshotApp(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	external.OpenImageEditor('http://xroshot.mtost.com/xroshot/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//external.OpenImageEditor('http://asptest.mtost.com/xroshot/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//openWindowEditor('800','600', '/xroshot/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq);
}
function openImageEditorXroshot(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	//external.OpenImageEditor('/xroshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq, 920, 615)
	openWindowEditor('800','600', '/xroshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorXroshotSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	//external.OpenImageEditor('/xroshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto, 920, 615)
	openWindowEditor('800','600', '/xroshot/editor/tostEditor.do?productNo=&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorUplus(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/uplus/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorEnfax(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/enfax/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorpostman(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/postman/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorpostman(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/postman/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorpostmanSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/postman/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditormunjacafe(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/munjacafe/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormfly(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/mfly/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditoreasysms(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('800','600', '/easysms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditoreasysmsSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	//external.OpenImageEditor('/xroshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq, 800, 600)
	openWindowEditor('800','600', '/easysms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function highlight(obj, stat, num) {
		if(stat == 'on') {
			obj.className = 'on';
			if(num) document.getElementById('ebtn_'+num).src = document.getElementById('ebtn_'+num).src.replace('_off', '_on');
		} else {
			obj.className = '';
			if(num) document.getElementById('ebtn_'+num).src = document.getElementById('ebtn_'+num).src.replace('_on', '_off');
		}
	}
	

function openImageEditorxonda(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/xonda/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorxonda(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/xonda/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorxondaSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/xonda/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}


function openImageEditormunza(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munza/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}
   
function openImageEditormunzaSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munza/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}
function openImageEditorbcube(product,seq, actDomain) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/bcube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&actDomain='+actDomain);
}
function openImageEditorbcubePopup(product,seq, actDomain) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/bcube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&actDomain='+actDomain+'&popup=A');
}


function openImageEditorbcubeSetPhoto(product,seq,myPhoto, actDomain) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/bcube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&actDomain='+actDomain);
}
function openImageEditorbcubeSetPhotoPopup(product,seq,myPhoto, actDomain) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/bcube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&actDomain='+actDomain+'&popup=A');
}

function openImageEditorsurem(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/surem/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsuremSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/surem/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditornetian(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/netian/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}
function openImageEditornetianMake(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/netian/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&type=make');
}

function openImageEditornetianSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/netian/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorarreo(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/arreo/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}
function openImageEditorarreoMake(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/arreo/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&type=make');
}

function openImageEditorarreoSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/arreo/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorubitube(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/ubitube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorubitubeSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/ubitube/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorhotsms(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/hotsms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorhotsmsSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/hotsms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorsms17(product,seq, lcd) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindow('565','430', '/sms17/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+ '&lcd='+lcd);
}

function openImageEditorsms17Sub(product,seq, lcd) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindow('565','430', '/sms17/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&type=sub&locationCd='+lcd);
}

function openImageEditorsms17SetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindow('565','430', '/sms17/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditormessageplus(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/messageplus/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormessageplusSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/messageplus/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}


function openImageEditormunjamadang(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munjamadang/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormunjamadangApp(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	external.OpenImageEditor('/munjamadang/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//external.OpenImageEditor('http://mplus.munjamadang.co.kr/munjamadang/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//openWindowEditor('920','615', '/munjamadang/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq+'&flow_path=app');
}

function openImageEditormunjamadangSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munjamadang/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditormoashot(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/moashot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormoashotSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/moashot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditormzigi(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mzigi/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormzigiSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mzigi/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditormunjanara(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munjanara/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditormunjanara(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munjanara/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormunjanaraSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/munjanara/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditornudesms(product,seq, lcd) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/nudesms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+ '&lcd='+lcd);
}

function openImageEditornudesmsSub(product,seq, lcd) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/nudesms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&type=sub&locationCd='+lcd);
}

function openImageEditornudesmsSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/nudesms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorsms16(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms16/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorsms16(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms16/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsms16SetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms16/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorskysms(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/skysms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorskysms(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/skysms/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorskysmsSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/skysms/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorlineshot(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/lineshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorlineshotSetPhoto(product,seq,myPhoto) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/lineshot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditorsmshana(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smshana/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorsmshana(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smshana/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsmshanaSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smshana/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorsmsmania(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smsmania/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorsmsmania(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smsmania/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsmsmaniaSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smsmania/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorsmstime(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smstime/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorsmstime(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smstime/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsmstimeSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/smstime/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorokmobi(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/okmobi/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorokmobi(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/okmobi/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorokmobiSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/okmobi/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditorsms9(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms9/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditorsms9(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms9/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorsms9SetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/sms9/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditoruhaknet(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/uhaknet/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&pageID='+pageID);
}

function openMapEditoruhaknet(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/uhaknet/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditoruhaknetSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/uhaknet/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}
function openImageEditorevershot(product,seq, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/evershot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}
function openImageEditorevershotApp(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	external.OpenImageEditor('/evershot/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//external.OpenImageEditor('http://mplus.munjamadang.co.kr/munjamadang/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq, 920, 615);
	//openWindowEditor('920','615', '/munjamadang/editor/tostAppEditor.do?productNo='+product+'&fileSize='+seq+'&flow_path=app');
}
function openMapEditorevershot(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/evershot/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditorevershotSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/evershot/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto);
}

function openImageEditormmsjoa(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsjoa/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openMapEditormmsjoa(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsjoa/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormmsjoaSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsjoa/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

function openImageEditormmsbox(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsbox/editor/tostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openMapEditormmsbox(product,seq) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsbox/editor/mapTostEditor.do?productNo='+product+'&fileSize='+seq);
}

function openImageEditormmsboxSetPhoto(product,seq,myPhoto, pageID) 
{
	if (seq=='240x320') seq = 2; 
	else if (seq=='176x144') seq = 1;
	else if (seq=='391x320') seq = 3;
	openWindowEditor('920','615', '/mmsbox/editor/tostEditor.do?productNo='+product+'&fileSize='+seq+'&myPhoto='+myPhoto+'&pageID='+pageID);
}

//lgu login check
function displayLoginAlert()
{
	window.open( "http://www.uplus.co.kr/cpi/main/main/RetrieveCpProdLogin.hpi", "", "width=400, height=260" );
}

//lgu login check
function uplusLoginAlert()
{
	alert("로그인 해 주세요.");
	return;
}

//postman login check
function postmanLoginAlert()
{
	alert("로그인 해 주세요.");
	return;
}

//munjacafe login check
function munjacafeLoginAlert()
{
	alert("로그인 해 주세요.");
	parent.location.href = "http://www.munjacafe.com/login.php";
	return;
}

//mflyLoginAlert login check
function mflyLoginAlert()
{ 
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function easysmsLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function xondaLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function munzaLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function bcubeLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function suremLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function netianLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function ubitubeLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function hotsmsLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function sms17LoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function munjamadangLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}
function messageplusLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function moashotLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function mzigiLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function munjanaraLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}

function nudesmsLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function sms16LoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function skysmsLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function lineshotLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function smshanaLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function smsmaniaLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}

function smstimeLoginAlert()
{
	alert("로그인 하시길 바랍니다.");
	return;
}
function okmobiLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}
function sms9LoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}
function uhaknetLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}
function evershotLoginAlert()
{
	alert("로그인 후 이용하실 수 있습니다.");
	return;
}
function mmsjoaLoginAlert()
{
	alert("회원전용입니다. 로그인 후 이용하세요.");
	return;
}
function mmsboxLoginAlert()
{
	alert("회원전용입니다. 로그인 후 이용하세요.");
	return;
}
function goProduct_js(no) { //간편편집기로 보내기
	document.dataMessenger.goProduct(no);
}

function goProduct_js_type2(no) { //간편편집기로 보내기
	document.dataMessenger.goProduct_type2(no);
}

function goMakeList_js(company,pageID) { //보관함 가기
	document.location.href = "/"+company+"/tostshop/makeTostList.do?pageID="+pageID;
}

function inputCheckSpecial(obj){
	var strobj = obj;
	re = /[~!@\#$%^&*\()\=+|\\/:;?"<>']/gi;
	if(re.test(strobj.value)){
		strobj.value = strobj.value.replace(re,"");
	}
}