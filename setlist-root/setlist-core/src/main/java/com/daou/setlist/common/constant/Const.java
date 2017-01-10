package com.daou.setlist.common.constant;

import org.apache.commons.lang3.StringUtils;

public class Const {
	/**
	 * /common/Image.do
	 */
	public static final String AUTH_IMAGE_PATH = "/common/Image.do";

	/**
	 * /common/Image.do?fileName=
	 */
	public static final String AUTH_IMAGE_PATH_URL = AUTH_IMAGE_PATH + "?fileName=";

	/**
	 * 이미지 압축률
	 */
	public static final float IMAGE_COMPRESS_RATE = 0.94f;

//	public static final Integer PP_DEFAULT_WIDTH_SIZE = 480;				// 이미지넓이값
//	public static final Integer PP_DEFAULT_HEIGHT_SIZE = 720;				// 이미지높이값
	
	public static final int ADMIN_USER_NO = 20;
	
	/**
	 * 상품(이모티콘) 타입 코드
	 */
	public enum PRODUCT_TYPE_CD {
		
		/** 템플릿 [3001T] */
		TEMPLATE("3001T"),
		/** 아이콘 [3001I] */
		ICON("3001I"),
		/** 그림문자 [3001F] */
		TEXTICON("3001F"),
		/** FRAME [3001G]*/
		FRAME("3001G"),
		/** MMS [3001M] */
		MMS("3001M");
		
		private String value;

		PRODUCT_TYPE_CD(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 카테고리 타입 코드
	 */
	public enum CATEGORY_TYPE_CD {
		
		/** 템플릿 [2000T] */
		TEMPLATE("2000T", "template"),
		/** 아이콘 [2000I] */
		ICON("2000I", "icon"),
		/** 그림문자 [2000F] */
		TEXTICON("2000F", "texticon");
		
		private String value;
		private String name;

		CATEGORY_TYPE_CD(String value, String name) {
			this.value = value;
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * 제작자 코드
	 */
	public enum MAKER_CD {
		
		/** 사용자 [2004P] */
		USER("2004P"),
		/** 관리자 [2004S] */
		SELLER("2004S");
		
		private String value;

		MAKER_CD(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 이미지 사이즈 코드
	 */
	public enum IMAGE_SIZE_CD {
		
		/** 일반,워터마크 [3] */
		NORMAL("3", 3),
		/** 썸네일 [6] */
		THUMBNAIL("6", 6),
		/** 기타이미지 [7] */
		ETC("7", 7),
		/** 사용자업로드 이미지 [10] */
		USER("10", 10);
		
		private String value;
		private Integer intValue;

		IMAGE_SIZE_CD(String value, Integer intValue) {
			this.value = value;
			this.intValue = intValue;
		}

		public String getValue() {
			return value;
		}

		public Integer getIntValue() {
			return intValue;
		}
	}
	
	/**
	 * 베스트 상태 코드
	 */
	public enum BEST_STAT_CD {
		
		/** 노출 중 [3] */
		YES("Y"),
		/** 예약 중 [6] */
		RESERVATION("R"),
		/** 비노출 [7] */
		NO("N");
		
		private String value;
		
		BEST_STAT_CD(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * type 값을 상품 타입 코드로 변환
	 * @param type 타입 (template, icon, texticon)
	 * @return
	 */
	public static String getProductTypeCd(String type) {
		if (StringUtils.equals(type, "template")) {
			return Const.PRODUCT_TYPE_CD.TEMPLATE.getValue();
		} else if (StringUtils.equals(type, "icon")) {
			return Const.PRODUCT_TYPE_CD.ICON.getValue();
		} else if (StringUtils.equals(type, "texticon")) {
			return Const.PRODUCT_TYPE_CD.TEXTICON.getValue();
		} else {
			throw new RuntimeException("타입 입력 에러");
		}
	}

	/**
	 * type 값을 카테고리 타입 코드로 변환
	 * @param type 타입 (template, icon, texticon)
	 * @return
	 */
	public static String getCategoryTypeCd(String type) {

		if (StringUtils.equals(type, "template")) {
			return Const.CATEGORY_TYPE_CD.TEMPLATE.getValue();
		} else if (StringUtils.equals(type, "icon")) {
			return Const.CATEGORY_TYPE_CD.ICON.getValue();
		} else if (StringUtils.equals(type, "texticon")) {
			return Const.CATEGORY_TYPE_CD.TEXTICON.getValue();
		} else {
			throw new RuntimeException("타입 입력 에러");
		}
	}
}
