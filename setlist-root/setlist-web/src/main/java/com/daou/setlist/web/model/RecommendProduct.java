package com.daou.setlist.web.model;

import java.util.List;

import com.daou.setlist.common.model.Category;
import com.daou.setlist.common.model.Product;

/**
 * 검색 결과가 없을 경우 나오는 페이지에 추천 컨텐츠로 사용
 * @author suzhy
 *
 */
public class RecommendProduct {
	private Category category;
	private List<Product> productList;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public RecommendProduct(Category category, List<Product> productList) {
		this.category = category;
		this.productList = productList;
	}
}
