package com.daou.setlist.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.daou.setlist.common.model.ProductFile;

public class FileUploadUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUploadUtil.class);

	/**
	 * 파일확장자 체크 (공통)
	 * @param request
	 * @param exts 허용된 확장자명. 콤마(,)로 구분
	 * @return
	 */
	public static boolean checkFileExt(HttpServletRequest request, String ...exts) {
		return checkFileExt((MultipartHttpServletRequest) request, exts);
	}
	
	/**
	 * 파일확장자 체크 (공통)
	 * @param multiRequest
	 * @param exts 허용된 확장자명. 콤마(,)로 구분
	 * @return true: 허용, false: 불허용
	 */
	public static boolean checkFileExt(MultipartHttpServletRequest multiRequest, String ...exts) {
		log.info("checkFileExt Start!");

		final Map<String, MultipartFile> files = multiRequest.getFileMap();

    	log.debug("files="+files);
    	log.debug("files.size()="+files.size());

		for (Entry<String, MultipartFile> entry : files.entrySet()) {
			log.debug("  for (Entry<String, MultipartFile> entry : files.entrySet())");
    		MultipartFile multiFile = entry.getValue();
    		long fileSize = multiFile.getSize();
    		log.debug("    multiFile.getSize()="+multiFile.getSize());
    		log.debug("    multiFile.getName()="+multiFile.getName());
    		log.debug("    multiFile.getOriginalFilename()="+multiFile.getOriginalFilename());
			if (fileSize > 0) {
				String originalFilename = multiFile.getOriginalFilename();
				String fileExt = StringUtils.lowerCase(StringUtils.substringAfterLast(originalFilename, "."));
				log.debug("      fileExt="+fileExt);
				if (StringUtils.indexOfAny(fileExt, exts) == -1) {
					return false;
				}
			}
		}

		return true;
    }

	/**
	 * 파일 제한 용량 체크 (용량 초과시 업로드 파일 삭제)
	 * @param fileList
	 * @param filePath
	 * @param maxFileSize
	 * @return true: 통과, false: 초과
	 */
	public static boolean checkMaxFileSize(List<ProductFile> fileList, String filePath, Long maxFileSize) {

		long totalFileSize = fileList.stream().mapToLong(f -> f.getFileSize()).sum();

		// 사이즈 초과시 업로드된 전체 파일 삭제
		if (maxFileSize < totalFileSize) {
			fileList.parallelStream().forEach(f -> {
				try {
					Files.deleteIfExists(Paths.get(filePath + "/" + f.getSavedFileNm()));
				} catch (IOException e) {
					log.error("사이즈 초과 파일 삭제 중 에러", e);
				}
			});
			return false;
		}
		return true;
	}

	/**
	 * 파일업로드(원본, 썸네일)
	 * 
	 * @param fileList 파일목록
	 * @param filePath 업로드 파일 패스
	 * @return List
	 */
	public static List<ProductFile> uploadFile(HttpServletRequest request, String filePath, Integer fileSeqNo) {
		String tmpFile = Long.toString(System.currentTimeMillis());

		final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

		/*
		 * extract files
		 */
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		/*
		 * process files
		 */
		File saveFolder = new File(filePath);

		// 디렉토리 생성
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		List<ProductFile> uploadList = new ArrayList<>();

		int k = 1;
		while (k <= fileSeqNo) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				MultipartFile multiFile = entry.getValue();
				long fileSize = multiFile.getSize();

				if (fileSize > 0) {
					if (("file" + k).equals(multiFile.getName())) {
						String originalFilename = multiFile.getOriginalFilename();
						String fileExt = originalFilename
								.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length())
								.toLowerCase();
						String fileName = "ori_" + tmpFile + "_" + k + "." + fileExt;

						ProductFile fileDto = new ProductFile();
						fileDto.setSavedFileNm(fileName.replace("ori_", ""));
						fileDto.setOriginFileNm(originalFilename);
						fileDto.setFileSize(fileSize);
						fileDto.setFileExt(fileExt);

						// 원본파일
						fileDto.setSeqNo(k);
						uploadList.add(fileDto);

						InputStream stream;

						try {
							stream = multiFile.getInputStream();
							String file = filePath + "/" + fileName;

							OutputStream bos = new FileOutputStream(file);
							int bytesRead = 0;
							byte[] buffer = new byte[4096];
							while ((bytesRead = stream.read(buffer, 0, 4096)) != -1) {
								bos.write(buffer, 0, bytesRead);
							}
							bos.close();
							stream.close();
							log.debug("file write ok : {}", file);

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			k++;
		}

		return uploadList;
	}

	public static List<ProductFile> uploadFileTmp(HttpServletRequest multiRequest, String filePath, Integer fileSeqNo) {
		
		final MultipartHttpServletRequest multiRequest1 = (MultipartHttpServletRequest) multiRequest;

		/*
		 * extract files
		 */
		final Map<String, MultipartFile> files = multiRequest1.getFileMap();

		/*
		 * process files
		 */
		File saveFolder = new File(filePath);

		// 디렉토리 생성
		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

    	MultipartFile multiFile;
    	List<ProductFile> uploadList = new ArrayList<>();
    	
    	int k = 1;
		while (k <= fileSeqNo) {
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				multiFile = entry.getValue();
				long fileSize = multiFile.getSize();

				if (fileSize > 0) {
					if (("file" + k).equals(multiFile.getName())) {

						String originalFilename = multiFile.getOriginalFilename();
						String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1,originalFilename.length()).toLowerCase();
						String fileName = Long.toString(System.currentTimeMillis()) + "_" + k + "." + fileExt;	

						//원본파일
						ProductFile fileDto = new ProductFile();
						fileDto.setImgPath(filePath + "/" + fileName);
						fileDto.setOriginFileNm(originalFilename);
						fileDto.setFileSize(fileSize);
						fileDto.setFileExt(fileExt);
						fileDto.setSeqNo(k);
						uploadList.add(fileDto);	
						
						InputStream stream;	
						
				        try {
				            stream = multiFile.getInputStream(); 
				            String file = filePath + "/" + fileName;

				            OutputStream bos = new FileOutputStream(file);
				            int bytesRead = 0;
				            byte[] buffer = new byte[4096];
				            while ((bytesRead = stream.read(buffer, 0, 4096)) != -1) {
				                bos.write(buffer, 0, bytesRead);
				            }
				            bos.close();
				            stream.close();
	
				        } catch (FileNotFoundException e) {
				        	log.error("사용자 파일 업로드 에러", e);
				        } catch (IOException e) {
				        	log.error("사용자 파일 업로드 에러", e);
				        }
					}	
				}
			}  
	    	k++;
   	 	}
	    	
		return uploadList;
    }


	private static final int BUFFER = 2048;

	/**
	 * unzip2 ( 해당 zip파일 경로, ,  )
	 * @param file
	 * @param filePath 압축풀 경로
	 * @param fileName 압축풀 파일명 (압축메소드에서 이미지명 최종 생성)
	 */
	@SuppressWarnings("resource")
	public static Map<String, String> unzip(File file, String filePath, String fileName) {
		Map<String, String> entryMap = new HashMap<>();
		
		BufferedInputStream source = null;
		BufferedOutputStream dest = null;
		try {
			ZipFile zipfile = new ZipFile(file);
			Enumeration<?> e = zipfile.entries();
			while (e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				String entryFilePath = filePath + fileName + "_" + entry.getName();
				
				log.debug("entry [{},{}]", entry, entryFilePath);
				entryMap.put(entry.getName(), entryFilePath);
				
				source = new BufferedInputStream(zipfile.getInputStream(entry));
				dest = new BufferedOutputStream(new FileOutputStream(entryFilePath), BUFFER);
				int count;
				byte data[] = new byte[BUFFER];
				while ((count = source.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				source.close();
			}
		} catch (IOException e) {
			log.error("압축해제 에러", e);
		} finally {
			try {
				if (dest != null) {
					dest.flush();
					dest.close();
				}
				if (source != null) {
					source.close();
				}
			} catch (IOException e) {}
		}
		
		return entryMap;
	}

}
