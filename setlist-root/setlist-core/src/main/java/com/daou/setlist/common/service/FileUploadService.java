package com.daou.setlist.common.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daou.setlist.common.constant.Const;
import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.common.exception.EmsXmlException;
import com.daou.setlist.common.model.ProductFile;
import com.daou.setlist.common.util.FileUploadUtil;

@Service
public class FileUploadService {

	private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

	@Autowired
	private Environment env;

	public ProductFile uploadFileUserPicture(HttpServletRequest request, String subPath) {
		
		int fileSeqNo = Const.IMAGE_SIZE_CD.USER.getIntValue();
		long maxFileSize = env.getProperty("FILE_SIZE_MAX_USER", Long.class);	//파일 합친 최대용량
		String saveDirPath = env.getProperty("FILE_ROOT_PATH") + subPath;
		String[] exts = new String[] { "jpg", "jpeg", "gif", "png" };
		
		return this.uploadFileAndCheck(request, saveDirPath, fileSeqNo, maxFileSize, exts);
	}
	
	private ProductFile uploadFileAndCheck(HttpServletRequest request, String saveDirPath, int fileSeqNo, long maxFileSize, String... exts) {
		
		// 날짜로 폴더 분리
		String fullPath = saveDirPath + "/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
			
		// 파일확장자체크 
		if (!FileUploadUtil.checkFileExt(request, exts)) {
			log.warn("이미지 파일만 등록할 수 있습니다.");
			throw new EmsXmlException("51", "이미지 파일만 등록할 수 있습니다."); 
		}

		// 파일업로드
		List<ProductFile> productFileList = FileUploadUtil.uploadFileTmp(request, fullPath, fileSeqNo);	// 원본파일저장
		
		// 파일업로드 성공여부 체크
		if (productFileList.isEmpty()) {
			log.warn("파일이 저장되지 않았습니다.");
			throw new EmsXmlException("50", "파일이 저장되지 않았습니다.\n다시 시도해 주세요."); 
		}
		
		ProductFile productFile = productFileList.get(0);
		
		// 파일사이즈 체크
		if (productFile.getFileSize() > maxFileSize) {
			log.warn("파일 용량을 초과하였습니다.");
			throw new EmsXmlException("52", "파일 용량을 초과하였습니다.\n다시 시도해 주세요."); 
		}
		
		return productFile;
	}

	/**
	 * 이미지 파일 업로드 (1건)
	 * @param multipartFile
	 * @param saveFilePath
	 * @return 저장된 파일 객체
	 */
	public File uploadImageFile(MultipartFile multipartFile, String saveFilePath) {
		
		log.debug("saveFilePath={}", saveFilePath);
		
		if (multipartFile == null || multipartFile.isEmpty()) {
			log.warn("업로드 파일이 존재하지 않습니다.");
			throw new EmsJsonException("파일이 존재하지 않습니다.");
		}
			
		// 파일확장자체크 
		String fileExt = StringUtils.lowerCase(StringUtils.substringAfterLast(saveFilePath, "."));
		log.debug("originalFilename={}", multipartFile.getOriginalFilename());
		log.debug("fileExt={}", fileExt);
		
		if (StringUtils.indexOfAny(fileExt, new String[] { "jpg", "jpeg", "gif", "png" }) == -1) {
			log.warn("이미지 파일만 등록할 수 있습니다.");
			throw new EmsJsonException("이미지 파일만 등록할 수 있습니다.");
		}

		long maxFileSize = env.getProperty("FILE_SIZE_MAX_DEFAULT", Long.class);	//파일 합친 최대용량
		
		// 파일사이즈 체크
		if (multipartFile.getSize() > maxFileSize) {
			log.warn("파일 용량을 초과하였습니다.");
			throw new EmsJsonException("파일 용량을 초과하였습니다."); 
		}
		
		// 파일 디렉토리 생성
		File saveDirPath = new File(StringUtils.substringBeforeLast(saveFilePath, "/"));
		log.debug("saveDirPath.exists()={}", saveDirPath.exists());
		if (!saveDirPath.exists()) {
			saveDirPath.mkdirs();
		}
		
		// 파일 저장
		File savedFile = new File(saveFilePath);
		try {
			multipartFile.transferTo(savedFile);
		} catch (IllegalStateException | IOException e) {
			log.error("파일 저장이 실패하였습니다.", e);
			throw new EmsJsonException("파일 저장이 실패하였습니다."); 
		} 

		// 파일업로드 성공여부 체크
		if (!savedFile.exists()) {
			log.warn("파일이 저장되지 않았습니다.");
			throw new EmsJsonException("파일이 저장되지 않았습니다."); 
		}
		
		return savedFile;
	}
}
