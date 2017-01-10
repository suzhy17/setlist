package com.daou.setlist.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureUtil {

	private static final Logger log = LoggerFactory.getLogger(SecureUtil.class);

	/**
	 * MD5 변환
	 * @param hkey
	 * @return
	 * @throws Exception
	 */
	public static String encodeMD5(String text, String hkey) {
		String data = text + hkey;
		try {
			byte[] digest = MessageDigest.getInstance("MD5").digest(data.getBytes());
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < digest.length; i++) {
				sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
				sb.append(Integer.toString(digest[i] & 0x0f, 16));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("convertMD5 fail ", e);
		}
		
		return "";
	}

}
