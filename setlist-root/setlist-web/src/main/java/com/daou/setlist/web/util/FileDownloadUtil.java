package com.daou.setlist.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public class FileDownloadUtil {

	/** 다운로드 버퍼 크기 */
	private static final int BUFFER_SIZE = 8192; // 8kb

	/** 문자 인코딩 */
	private static final String CHARSET = "utf-8";

	/**
	 * 생성자 - 객체 생성 불가
	 */
	private FileDownloadUtil() {
		// do nothing;
	}

	/**
	 * 지정된 파일을 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param file 다운로드할 파일
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, File file)
			throws ServletException, IOException {

		String mimetype = "image/jpeg";
		if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
			throw new IOException("파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리이다.");
		}

		InputStream is = null;

		try {
			is = new FileInputStream(file);
			download(request, response, is, file.getName(), file.length(), mimetype);
		} finally {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 해당 입력 스트림으로부터 오는 데이터를 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param is 입력 스트림
	 * @param filename 파일 이름
	 * @param filesize 파일 크기
	 * @param mimetype MIME 타입 지정
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, InputStream is,
			String filename, long filesize, String mimetype) throws ServletException, IOException {
		String mime = mimetype;

		if (mimetype == null || mimetype.length() == 0) {
			mime = "application/octet-stream;";
		}

		byte[] buffer = new byte[BUFFER_SIZE];

		response.setContentType(mime + " charset=" + CHARSET);
		response.setHeader("Content-Disposition", "inline; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ";");
		// 파일 사이즈가 정확하지 않을때는 아예 지정하지 않는다.
		if (filesize > 0) {
			response.setHeader("Content-Length", "" + filesize);
		}

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;

		try {
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;

			while ((read = fin.read(buffer)) != -1) {
				outs.write(buffer, 0, read);
			}
		} finally {
			try {
				outs.close();
			} catch (Exception ex1) {
			}

			try {
				fin.close();
			} catch (Exception ex2) {

			}
		}
	}
}
