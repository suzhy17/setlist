package com.daou.setlist.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <code>LogUtil.java</code>
 * @Description : 로그 관리 유틸 클래스
 * @author : redkarisma
 * @modify : 
 * @since 2015. 3. 3.
 * @version 1.0
 */
public class LogUtil {

	private static final Log logger = LogFactory.getLog(LogUtil.class);
	
	/**
	 * @Description : 관리자 로그 기록
	 * @author : 이창화
	 * @param logPath 로그 패스
	 * @param svcNm 서비스명
	 * @param logType account:권한생성,변경,삭제 / access:접속 및 접근로그
	 * @param remoteAddr 접속지 IP Address
	 * @param accessId 개인정보 취급자 식별정보 : 관리자ID
	 * @param requestUri 요청 URI
	 * @param actTask 수행업무
	 * @param targetInfo 정보주체 식별정보
	 */
	public static void adminLog(String logPath, String svcNm, String logType, String remoteAddr, String accessId, String requestUri, String actTask, String targetInfo) {

		LocalDateTime dateTime = LocalDateTime.now();
		
		String curDate = dateTime.format(DateTimeFormatter.ofPattern("yyMMddHH"));
		String curTime = dateTime.format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"));
		
		File updir = new File(logPath);
		if (!updir.exists())
			updir.mkdirs();

		logPath += getHost() + "_" + svcNm + "_" + logType + "_" + curDate + ".log";
		String logMsg = "[" + curTime + "] " + remoteAddr + " | " + accessId + " | " + requestUri + " | " + actTask + " | " + targetInfo;
		
		try {
			File file = new File(logPath);
			if (!file.exists()) {
				file.createNewFile();
			}

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			pw.println(logMsg);
			pw.close();

		} catch (IOException e) {
			logger.error(e.toString());
		}
	}
	
	/**
	 * @Description : 서버에 설정된 HOST 추출
	 * @author : redkarisma
	 * @return
	 */
	private static String getHost() {
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return host;
	}
	
}
