package com.daou.setlist.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

    /**
     * 반복적인 패스워드인지 체크 
     * @param password
     * @return true : 적합, false : 부적합
     */
    public static boolean isStrengthPassword(String password) {
    	int limit = 2;
    	int reptCnt = 0;
    	int consCnt = 0;

    	char[] passwordChars = password.toCharArray();
    	
    	for (int i = 0; i < passwordChars.length; i++) {
    		
    		if (passwordChars.length == i+1) {
    			break;
    		}
    		
    		int currChar = passwordChars[i];
    		int nextChar = passwordChars[i+1];

    		// 중복 문자 확인
    		if (reptCnt < limit) {
    			if (currChar == nextChar) {
    				reptCnt++;
    			} else {
    				reptCnt = 0;
    			}
    		}

    		// 연속문자 확인
    		if (consCnt < limit) {
    			if (currChar + 1 == nextChar) {
    				consCnt++;
    			} else {
    				consCnt = 0;
    			}
    		}
    	}
    	
    	if (reptCnt == limit || consCnt == limit) {
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * 패스워드 복잡도 체크 (숫자,영문자 조합 10자리 이상)
     * @param password
     * @return true : 적합, false : 부적합
     */
    public static boolean isComplexPassword(String password) {
    	if (StringUtils.isBlank(password)) {
    		return false;
    	}
    	
		// 숫자,영문자 조합 10자리 이상
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{10,20}$");
		Matcher match = pattern.matcher(password);
		
		return match.find();
    }

    /**
     * Unescape
     * @param str 문자열
     * @return
     */
	public static String unescape(String str) {
		StringBuffer rtnStr = new StringBuffer();
		char[] arrInp = str.toCharArray();
		int i;
		for (i = 0; i < arrInp.length; i++) {
			if (arrInp[i] == '%') {
				String hex;
				if (arrInp[i + 1] == 'u') { // 유니코드.
					hex = str.substring(i + 2, i + 6);
					i += 5;
				} else { // ascii
					hex = str.substring(i + 1, i + 3);
					i += 2;
				}
				try {
					rtnStr.append(Character.toChars(Integer.parseInt(hex, 16)));
				} catch (NumberFormatException e) {
					rtnStr.append("%");
					i -= (hex.length() > 2 ? 5 : 2);
				}
			} else {
				rtnStr.append(arrInp[i]);
			}
		}

		return rtnStr.toString();
	 }
	
	public static String getDomain(HttpServletRequest request) {
		StringBuffer uriPath = new StringBuffer();
		uriPath
			.append(request.getScheme()).append("://")		// http://
			.append(request.getServerName()).append(":")	// myhost:
			.append(request.getServerPort());				// 8080
		return uriPath.toString();
	}
}
