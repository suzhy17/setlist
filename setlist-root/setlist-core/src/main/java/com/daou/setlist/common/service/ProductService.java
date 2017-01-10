package com.daou.setlist.common.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.daou.setlist.common.constant.Const;
import com.daou.setlist.common.dao.CommDao;
import com.daou.setlist.common.exception.EmsJsonException;
import com.daou.setlist.common.model.Category;
import com.daou.setlist.common.model.Product;
import com.daou.setlist.common.model.ProductBest;
import com.daou.setlist.common.model.ProductFile;
import com.daou.setlist.common.model.Search;
import com.google.common.base.CaseFormat;

@Service
public class ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private Environment env;

	@Autowired
	private CommDao commDao;

	/**
	 * 카테고리에 해당하는 이모티콘 조회
	 * @param categoryCds
	 * @return
	 */
	public Integer countProductListByCategoryCds(List<String> categoryCds) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryCds", categoryCds);
		return commDao.selectOne("PRODUCT.countProductListByCategoryCds", params);
	}

	public List<Category> selectCategoryList(String categoryTypeCd) {
		return this.selectCategoryList(categoryTypeCd, null);
	}

	public List<Category> selectCategoryList(String categoryTypeCd, String categoryGroupCd) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryTypeCd", categoryTypeCd);
		params.put("categoryGroupCd", categoryGroupCd);
		return commDao.selectList("PRODUCT.selectCategoryList", params);
	}
	
	public boolean existCategory(String categoryTypeCd, String categoryCd) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryTypeCd", categoryTypeCd);
		params.put("categoryCd", categoryCd);
		List<Category> categoryList = commDao.selectList("PRODUCT.selectCategoryList", params);
		return categoryList != null;
	}

	/**
	 * 사용자가 만든 이모티콘 리스트 조회
	 * @param userNo
	 * @param pageable
	 * @return
	 */
	public Page<Product> selectProductListByUserMade(Integer userNo, Pageable pageable) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("imageSize" , Const.IMAGE_SIZE_CD.NORMAL.getValue());
		params.put("userMadeYn", "Y");
		params.put("userNo"    , userNo);
		params.put("pageable"  , pageable);
		return this.selectProductListForEditor(params);
	}
	
	/**
	 * 포토 이모티콘 리스트 검색 
	 * @param productTypeCd 타입 (template, icon, texticon)
	 * @param categoryCd 카테고리 코드
	 * @param imageSize 이미지 사이즈 (3: 워터마크 ..)
	 * @param searchType 검색 타입
	 * @param searchKeyword 검색어
	 * @param pageable
	 * @return
	 */
	public Page<Product> selectProductListForEditor(String productTypeCd, String categoryCd, String imageSize, String searchType, String searchKeyword, Pageable pageable) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("productTypeCd", productTypeCd);
		params.put("categoryCd"   , categoryCd);
		params.put("imageSize"    , imageSize);
		params.put("searchType"   , searchType);
		params.put("searchKeyword", searchKeyword);
		params.put("pageable"     , pageable);
		
		return this.selectProductListForEditor(params);
	}
	
	/**
	 * 포토 이모티콘 리스트 검색 
	 * @param productTypeCd 타입 (template, icon, texticon)
	 * @param categoryCd 카테고리 코드
	 * @param imageSize 이미지 사이즈 (3: 워터마크 ..)
	 * @param search 검색조건
	 * @param pageable 페이
	 * @return
	 */
	public Page<Product> selectProductList(String productTypeCd, String categoryCd, String imageSize, Search search, Pageable pageable) {		
		
		Map<String, Object> params = new HashMap<>();
		params.put("productTypeCd", productTypeCd);
		params.put("categoryCd"   , categoryCd);
		params.put("imageSize"    , imageSize);
		params.put("search"       , search);
		params.put("pageable"     , pageable);

		Sort sort = pageable.getSort();
		if (sort != null) {
			Iterator<Order> iter = sort.iterator();
			if (iter.hasNext()) {
				Order order = iter.next();
				params.put("sort", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, order.getProperty())  + " " + order.getDirection());
			}
		}
		
		return this.selectProductList(params);
	}

	/**
	 * 포토에디터에서 이모티콘 리스트 조회
	 * @param params
	 * @return
	 */
	public Page<Product> selectProductListForEditor(Map<String, Object> params) {

		Long totalCnt = commDao.selectOne("PRODUCT.countProductList", params);
		log.debug("totalCnt={}", totalCnt);
		
		List<Product> resultList = new ArrayList<>();
		if (totalCnt > 0) {
			resultList = commDao.selectList("PRODUCT.selectProductList", params);
			if (resultList != null) {
				// 베스트 상태 값 세팅
				resultList = resultList.stream()
						.peek(product -> {
							// 베스트 아님
							if (product.getBestDt() == null) {
								product.setBestStat(Const.BEST_STAT_CD.NO.getValue());	
							}
							// 베스트 (현재 노출)
							else if (product.getBestDt().isBefore(LocalDateTime.now())) {
								product.setBestStat(Const.BEST_STAT_CD.YES.getValue());
							}
							// 베스트 (예약 상태)
							else {
								product.setBestStat(Const.BEST_STAT_CD.RESERVATION.getValue());
							}
							
							product.setImgPath(StringUtils.replace(product.getImgPath(), "PRIVATE", "PUBLIC"));
						})
						.collect(Collectors.toList());
			}
		}
		
		Pageable pageable = (Pageable) params.get("pageable");
		
		return new PageImpl<>(resultList, pageable, totalCnt);
	}

	public Page<Product> selectProductList(Map<String, Object> params) {

		Long totalCnt = commDao.selectOne("PRODUCT.countProductList", params);
		log.debug("totalCnt={}", totalCnt);
		
		List<Product> resultList = new ArrayList<>();
		if (totalCnt > 0) {
			resultList = commDao.selectList("PRODUCT.selectProductList", params);
			if (resultList != null) {
				// 베스트 상태 값 세팅
				resultList = resultList.stream()
						.peek(product -> {
							// 베스트 아님
							if (product.getBestDt() == null) {
								product.setBestStat(Const.BEST_STAT_CD.NO.getValue());	
							}
							// 베스트 (현재 노출)
							else if (product.getBestDt().isBefore(LocalDateTime.now())) {
								product.setBestStat(Const.BEST_STAT_CD.YES.getValue());
							}
							// 베스트 (예약 상태)
							else {
								product.setBestStat(Const.BEST_STAT_CD.RESERVATION.getValue());
							}
						})
						.collect(Collectors.toList());
			}
		}
		
		Pageable pageable = (Pageable) params.get("pageable");
		
		return new PageImpl<>(resultList, pageable, totalCnt);
	}

	
	/**
	 * 포토 이모티콘 리스트 검색 
	 * @param productTypeCd 타입 (template, icon, texticon)
	 * @param categoryCd 카테고리 코드
	 * @param imageSize 이미지 사이즈 (3: 워터마크 ..)
	 * @param search 검색조건
	 * @param pageable 페이
	 * @return
	 */
	public Page<Product> selectProductListForAdmin(String productTypeCd, String categoryCd, String imageSize, Search search, Pageable pageable) {		
		
		Map<String, Object> params = new HashMap<>();
		params.put("productTypeCd", productTypeCd);
		params.put("categoryCd"   , categoryCd);
		params.put("imageSize"    , imageSize);
		params.put("search"       , search);
		params.put("pageable"     , pageable);

		Sort sort = pageable.getSort();
		if (sort != null) {
			Iterator<Order> iter = sort.iterator();
			if (iter.hasNext()) {
				Order order = iter.next();
				params.put("sort", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, order.getProperty())  + " " + order.getDirection());
			}
		}
		
		Long totalCnt = commDao.selectOne("PRODUCT.countProductListForAdmin", params);
		log.debug("totalCnt={}", totalCnt);
		
		List<Product> resultList = new ArrayList<>();
		if (totalCnt > 0) {
			resultList = commDao.selectList("PRODUCT.selectProductListForAdmin", params);
			if (resultList != null) {
				// 베스트 상태 값 세팅
				resultList = resultList.stream()
						.peek(product -> {
							// 베스트 아님
							if (product.getBestDt() == null) {
								product.setBestStat(Const.BEST_STAT_CD.NO.getValue());	
							}
							// 베스트 (현재 노출)
							else if (product.getBestDt().isBefore(LocalDateTime.now())) {
								product.setBestStat(Const.BEST_STAT_CD.YES.getValue());
							}
							// 베스트 (예약 상태)
							else {
								product.setBestStat(Const.BEST_STAT_CD.RESERVATION.getValue());
							}
						})
						.collect(Collectors.toList());
			}
		}
		
		return new PageImpl<>(resultList, pageable, totalCnt);
	}

	public Product selectProductDetail(Integer productNo) {
		return commDao.selectOne("PRODUCT.selectProductDetail", productNo);
	}
	
	public List<ProductBest> selectProductBest(String categoryTypeCd, List<Integer> productNos) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryTypeCd", categoryTypeCd);
		params.put("productNos"   , productNos);
		return commDao.selectList("PRODUCT.selectProductBestList", params);
	}
		
	/**
	 * 이모티콘 템플릿 등록
	 * <p>템플릿정보, 배경이미지 파일정보, 썸네일 파일 정보 등록
	 * <p>추가이미지 파일 정보의 파일키 업데이트 
	 * @param product
	 * @param productFile
	 * @param thumbProductFile
	 * @param fileNos
	 * @param tagNms
	 * @return
	 */
	@Transactional
	public Product registerProductTemplate(Product product, ProductFile productFile, ProductFile thumbProductFile, String[] fileNos, String[] tagNms) {
		
		// 템플릿 정보 등록
		commDao.insert("PRODUCT.insertProduct", product);
		product.setFileKey(product.getProductNo());

		// 파일키 업데이트
		commDao.update("PRODUCT.updateProductForFileKey", product);
		
		// 배경이미지 파일 정보 등록
		productFile.setFileKey(product.getProductNo());
		productFile.setRegUserNo(product.getRegUserNo());
		commDao.insert("PRODUCT.insertProductFile", productFile);
		
		// 썸네일 파일 정보 등록
		thumbProductFile.setFileKey(product.getProductNo());
		thumbProductFile.setRegUserNo(product.getRegUserNo());
		commDao.insert("PRODUCT.insertProductFile", thumbProductFile);

		// 추가이미지의 파일 키 업데이트
		if (fileNos != null && fileNos.length > 0) {
			Map<String, Object> params = new HashMap<>();
			params.put("fileNos", fileNos);
			params.put("fileKey", product.getFileKey());
			commDao.update("PRODUCT.updateProductFileForFileKey", params);
		}

		// 태그 등록
		this.registerTag(product.getProductNo(), tagNms);

		return product;
	}
	
	/**
	 * 이모티콘 템플릿 업데이트
	 * <p>템플릿정보, 배경이미지 파일정보, 썸네일 파일 정보 등록
	 * @param product
	 * @param productFile
	 * @param thumbProductFile
	 * @param tagNms
	 * @return
	 */
	@Transactional
	public Product modifyProductTemplate(Product product, ProductFile productFile, ProductFile thumbProductFile, String[] tagNms) {
		
		// 템플릿 정보 등록
		commDao.update("PRODUCT.updateProduct", product);
		
		// 배경이미지 파일 정보 등록
		if (productFile != null) {
			productFile.setFileKey(product.getFileKey());
			productFile.setRegUserNo(product.getRegUserNo());
			commDao.insert("PRODUCT.insertProductFile", productFile);
		}
		
		// 썸네일 파일 정보 등록
		if (thumbProductFile != null) {
			thumbProductFile.setFileKey(product.getFileKey());
			thumbProductFile.setRegUserNo(product.getRegUserNo());
			commDao.insert("PRODUCT.insertProductFile", thumbProductFile);
		}
		
		// 태그 등록
		this.registerTag(product.getProductNo(), tagNms);
		
		return product;
	}
	
	/**
	 * 이모티콘 아이콘 등록
	 * @param product
	 * @param productFile
	 * @param tagNms
	 * @return
	 */
	@Transactional
	public Product registerProductIcon(Product product, ProductFile productFile, String[] tagNms) {
		
		// 템플릿 정보 등록
		commDao.insert("PRODUCT.insertProduct", product);
		product.setFileKey(product.getProductNo());
	
		// 파일키 업데이트
		commDao.update("PRODUCT.updateProductForFileKey", product);
		
		// 파일 정보 등록
		productFile.setFileKey(product.getProductNo());
		productFile.setRegUserNo(product.getRegUserNo());
		commDao.insert("PRODUCT.insertProductFile", productFile);
		
		// 태그 등록
		this.registerTag(product.getProductNo(), tagNms);
	
		return product;
	}
	
	private void registerTag(Integer productNo, String[] tagNms) {
		if (tagNms != null && tagNms.length > 0) {

			// 중복제거
			tagNms = new HashSet<String>(Arrays.asList(tagNms)).toArray(new String[0]);

			Map<String, Object> params = new HashMap<>();
			params.put("productNo", productNo);
			
			// 기존 태그 삭제
			commDao.delete("PRODUCT.deleteProductTagMap", params);
			
			// 태그, 매핑 등록 (중복 무시)
			for (String tagNm : tagNms) {
				params.put("tagNm", StringUtils.trim(tagNm));
				commDao.insert("PRODUCT.insertTag", params);
				commDao.insert("PRODUCT.insertProductTagMap", params);
			}
		}
	}
	
	/**
	 * 파일 등록
	 * @param productFile
	 */
	public void insertProductFile(ProductFile productFile) {
		commDao.insert("PRODUCT.insertProductFile", productFile);
	}
	
	/**
	 * 파일 삭제
	 * @param fileNo
	 */
	@Transactional
	public void deleteProductFile(Integer fileNo) {
		
		ProductFile productFile = this.selectProductFile(fileNo);
		
		// DB 데이터 삭제
		commDao.delete("PRODUCT.deleteProductFile", productFile.getFileNo());
		
		// 파일 삭제
		String rootPath = env.getProperty("FILE_ROOT_PATH");
		File file = new File(rootPath + productFile.getImgPath());
		if (!file.exists() || !file.isFile()) {
			throw new EmsJsonException("파일이 존재하지 않습니다.");
		}
		file.delete();
	}
	
	/**
	 * 파일 정보 조회
	 * @param fileNo
	 * @return
	 */
	public ProductFile selectProductFile(Integer fileNo) {
		return commDao.selectOne("PRODUCT.selectProductFile", fileNo);
	}
	
	/**
	 * 파일 정보 리스트 조회
	 * @param fileNo
	 * @return
	 */
	public List<ProductFile> selectProductFileListByFileKey(Integer fileKey) {
		Map<String, Object> params = new HashMap<>();
		params.put("fileKey", fileKey);
		return commDao.selectList("PRODUCT.selectProductFileList", params);
	}
	
	/**
	 * 베스트 등록
	 * 중복은 피해서 등록한다.
	 * @param categoryTypeCd
	 * @param productNos
	 * @param bestDt 예약시간
	 * @return 
	 */
	public void registerProductBest(String categoryTypeCd, List<Integer> productNos, String bestDt) {
		// 베스트 중복 등록을 막기위해 기존 베스트 등록 내역 조회
		List<ProductBest> bestList = this.selectProductBest(categoryTypeCd, productNos);
		Set<Integer> alreadyProductNos = new HashSet<>();
		bestList.stream().forEach(b -> alreadyProductNos.add(b.getProductNo()));
		
		ProductBest productBest = new ProductBest();
		productBest.setCategoryTypeCd(categoryTypeCd);
		if (StringUtils.isNotBlank(bestDt)) {
			LocalDateTime ldtBestDt = LocalDateTime.parse(bestDt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			productBest.setBestDt(ldtBestDt);
		} else {
			productBest.setBestDt(LocalDateTime.now());
		}
		productBest.setRegDt(LocalDateTime.now());
		for (Integer productNo : productNos) {
			// 등록 안된것만 등록
			if (!alreadyProductNos.contains(productNo)) {
				productBest.setProductNo(productNo);
				commDao.insert("PRODUCT.insertProductBest", productBest);
			}
		}
	}
	
	/**
	 * 베스트 앞으로보내기
	 * @param categoryTypeCd
	 * @param bestNo
	 * @return 
	 */
	public void forwarProductBest(String categoryTypeCd, Integer bestNo) {
		ProductBest productBest = new ProductBest();
		productBest.setBestNo(bestNo);
		productBest.setCategoryTypeCd(categoryTypeCd);
		productBest.setBestDt(LocalDateTime.now());
		commDao.update("PRODUCT.updateProductBest", productBest);
	}
	
	/**
	 * 베스트 삭제
	 * @param bestNos
	 * @return 
	 */
	public void cancelProductBest(List<Integer> bestNos) {
		Map<String, Object> params = new HashMap<>();
		params.put("bestNos", bestNos);
		commDao.delete("PRODUCT.deleteProductBest", params);
	}
	
	/**
	 * 이모티콘 삭제
	 * @param productNos
	 */
	public void deleteProduct(List<Integer> productNos) {
		Map<String, Object> params = new HashMap<>();
		params.put("productNos", productNos);
		commDao.delete("PRODUCT.deleteProduct", params);
	}
	
	/**
	 * 카테고리에 속한 이모티콘 삭제
	 * @param categoryCd
	 */
	public void deleteProductByCategoryCd(String categoryCd) {
		Map<String, Object> params = new HashMap<>();
		params.put("categoryCd", categoryCd);
		List<Integer> productNos = commDao.selectList("PRODUCT.selectProductNoListByCategoryCd", params);
		this.deleteProduct(productNos);
	}
	
	/**
	 * 이모티콘 카테고리 이동
	 * @param productNos
	 * @param categoryCd
	 */
	public void moveProduct(List<Integer> productNos, String categoryCd) {
		Map<String, Object> params = new HashMap<>();
		params.put("productNos", productNos);
		params.put("categoryCd" , categoryCd);
		commDao.update("PRODUCT.updateProductForCategoryMove", params);
	}
	
	/**
	 * 사용자 템플릿 저장
	 * @param productNo
	 */
	public void updateProductForUserMadeSave(Integer productNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("productNo", productNo);
		commDao.update("PRODUCT.updateProductForUserMadeSave", params);
	}
	
	/**
	 * 사용자 템플릿 삭제 (1건, 실 삭제는 배치에서 처리한다.)
	 * @param productNo
	 */
	public void deleteProductForUserMade(Integer productNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("productNo", productNo);
		commDao.update("PRODUCT.updateProductForUserMadeDelete", params);
	}
	
	/**
	 * 사용자 템플릿 삭제 (실 삭제는 배치에서 처리한다.)
	 * @param userNo
	 * @param productNos
	 */
	public void deleteProductForUserMade(Integer userNo, List<Integer> productNos) {
		Map<String, Object> params = new HashMap<>();
		params.put("userNo" , userNo);
		params.put("productNos", productNos);
		commDao.update("PRODUCT.updateProductForUserMadeDeleteMulti", params);
	}

	/**
	 * 이모티콘 조회
	 * @param productNo
	 * @param fileSize
	 * @return
	 */
	public Product selectProduct(Integer productNo, Integer fileSize) {

		if (productNo == 0) { // 컨텐츠번호가 없는 경우
			return null;
		}
		
		// 템플릿데이터		
		Map<String, Object> params = new HashMap<>();
		params.put("productNo", productNo);
		params.put("fileSize" , fileSize);
		return commDao.selectOne("PRODUCT.selectProduct", params);
	}
	
	/**
	 * 템플릿 파일 키 조회
	 * @param productNo
	 * @return
	 */
	public Integer selectProductFileKey(Integer productNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("productNo", productNo);
		return commDao.selectOne("PRODUCT.selectProductFileKey", params);
	}
	
	@Transactional
	public Product registerUserTemplate(
			Product product,
			ProductFile orgProductFile,
			ProductFile drawProductFile,
			String privateFullPath,
			String publicPostPath,
			String drawingCd) throws Exception {

		//----------------------------------------
		// 템플릿 정보 등록
		//----------------------------------------
		
		log.debug("새로운 컨텐츠로 등록");
		commDao.insert("PRODUCT.insertProduct", product);
		product.setFileKey(product.getProductNo());
		
		// 앞으로는 productNo와 fileKey를 맞춘다. (mysql에서는 seq를 직접가져올수 없기 때문)
		commDao.update("PRODUCT.updateProductForFileKey", product);

		
		//===========================================
		// 파일 정보 등록
		//===========================================
		// 1. 원본저장
		// 2. 썸네일저장
		// 3. 그리기 저장

		log.debug("orgProductFile={}", orgProductFile);

		// 사용자업로드 파일이 있는 경우 기존 파일키
		Integer fileKey = product.getFileKey();
		
		//-----------------------------
		// 1. 원본저장
		//-----------------------------
		orgProductFile.setFileKey(fileKey);

		log.debug("원본저장");
		commDao.insert("PRODUCT.insertProductFile", orgProductFile);
		
		// FILE_KEY 추출을 위해 가져온다.
		orgProductFile = commDao.selectOne("PRODUCT.selectProductFile", orgProductFile.getFileNo());
		
		product.setFileKey(orgProductFile.getFileKey());
		product.setImgPath(orgProductFile.getImgPath());

		
		//-----------------------------
		// 워터마크 이미지 생성 & DB저장
		//-----------------------------
		// 워터마크 이미지 만들기			
		String srcImgPath = env.getProperty("FILE_ROOT_PATH") + orgProductFile.getImgPath();
		String waterDestImgPath = srcImgPath.replaceFirst("PRIVATE", "PUBLIC");
		String waterMarkImgPath = env.getProperty("FILE_ROOT_PATH") + env.getProperty("WATER_FILE_PATH");

		log.debug("워터마크 이미지 만들기");
		log.debug("srcImgPath={}", srcImgPath);
		log.debug("waterDestImgPath={}", waterDestImgPath);
		log.debug("waterMarkImgPath={}", waterMarkImgPath);
		// ImageConverter.waterMark(srcImgPath, waterDestImgPath, waterMarkImgPath);

		File saveDir = new File(StringUtils.substringBeforeLast(waterDestImgPath, "/"));

		// 디렉토리 생성
		if (!saveDir.exists() || saveDir.isFile()) {
			saveDir.mkdirs();
		}

		FileCopyUtils.copy(new File(srcImgPath), new File(waterDestImgPath));	// 워터마크를 안쓰기 때문에 단순히 복사만 한다.
		
		
		//-----------------------------
		// 썸네일 이미지 생성 & DB저장
		//-----------------------------
		String thumbFilePath = env.getProperty("FILE_ROOT_PATH") + publicPostPath + "/" + StringUtils.substringBeforeLast(orgProductFile.getSavedFileNm(), ".") + "_thumb." + orgProductFile.getFileExt();

		// 썸네일저장
		ProductFile thumbProductFile = new ProductFile();
		thumbProductFile.setFileKey(fileKey);
		thumbProductFile.setImgPath(thumbFilePath);
		thumbProductFile.setOriginFileNm(thumbProductFile.getSavedFileNm());
		thumbProductFile.setSeqNo(Const.IMAGE_SIZE_CD.THUMBNAIL.getIntValue());
		thumbProductFile.setRegUserNo(orgProductFile.getRegUserNo());

		log.debug("썸네일저장");
		commDao.insert("PRODUCT.insertProductFile", thumbProductFile);
		
		
		//-----------------------------
		// 그리기 DB저장
		//-----------------------------
		if (drawProductFile != null) {
			drawProductFile.setFileKey(fileKey);

			log.debug("그리기 저장");
			commDao.insert("PRODUCT.insertProductFile", drawProductFile);
		}	
		
		return product;
	}
	
	/**
	 * 사용자 사진 등록
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Product registerUserPicture(Product product, ProductFile productFile) throws Exception {

		//----------------------------------------
		// 템플릿 정보 등록
		//----------------------------------------

		log.debug("템플릿 등록");
		commDao.insert("PRODUCT.insertProduct", product);
		product.setFileKey(product.getProductNo());
		
		// 앞으로는 productNo와 fileKey를 맞춘다. (mysql에서는 seq를 직접가져올수 없기 때문)
		commDao.update("PRODUCT.updateProductForFileKey", product);
		

		//----------------------------------------
		// 파일 정보 등록
		//----------------------------------------

		productFile.setFileKey(product.getProductNo());
		productFile.setRegUserNo(product.getRegUserNo());
		productFile.setSeqNo(Const.IMAGE_SIZE_CD.USER.getIntValue());

		log.debug("파일 정보 등록");
		commDao.insert("PRODUCT.insertProductFile", productFile);

		return product;
	}

	/**
	 * 원본 템플릿의 발송 건수/회수 업데이트
	 * @param productNo
	 * @param totSendCnt
	 */
	public void addSendCnt(Integer productNo, Integer totSendCnt) {

		//---------------------------------------
		// 원본 productNo 찾기
		//---------------------------------------
		// originProductNo가 0이면 원본 템플릿으로 판단
		// originProductNo가 0이 나올때까지 반복 조회 (판매용 템플릿
		
		Integer originProductNo = 0;
		Integer parentProductNo = productNo;
		
		do {
			originProductNo = parentProductNo;
			parentProductNo = commDao.selectOne("PRODUCT.selectProductForOriginProductNoByProductNo", parentProductNo);
			log.debug("parentProductNo={}", parentProductNo);
			
		} while (parentProductNo != null && parentProductNo.intValue() > 0);
		log.debug("productNo={}, originProductNo={}", productNo, originProductNo);

		//---------------------------------------
		// 건수 업데이트
		//---------------------------------------
		
		Map<String, Object> params = new HashMap<>();
		params.put("productNo" , originProductNo);
		params.put("totSendCnt", totSendCnt);
		commDao.update("PRODUCT.updateProductForSendCnt", params);
	}
}
