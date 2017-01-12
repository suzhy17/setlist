package com.daou.setlist.admin.domain;

import org.springframework.data.domain.Page;

public class Pagination {
	private Page<?> page;
	private Long pageNaviCount = 10L;
	private Long rowNumCorrection = 0L;

	public Pagination() {
	}
	
	public Pagination(Page<?> page) {
		this.page = page;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public Long getPageSize() {
		return Integer.valueOf(page.getSize()).longValue();
	}

	public Long getPageNum() {
		return Integer.valueOf(page.getNumber()).longValue() + 1;
	}

	public Long getStartRowNum() {
		return (getPageNum() - 1) * getPageSize() + 1 + getRowNumCorrection();
	}

	public Long getStartRecordNum() {
		return getStartRowNum() - 1;
	}

	public Long getEndRowNum() {
		return this.getStartRowNum() + getPageSize() - 1;
	}

	public Long getListCount() {
		return page.getTotalElements();
	}

	public Long getPageNaviCount() {
		return pageNaviCount;
	}

	public Long getPageListOffset() {
		return this.getPageNum() * this.getPageSize();
	}

	public Long getCurrentFirstPageNum() {
		return (((this.getPageNum() / this.getPageNaviCount())
				+ (Math.floorMod(this.getPageNum(), this.getPageNaviCount()) > 0 ? 1 : 0)) - 1)
				* this.getPageNaviCount() + 1;
	}

	public Long getCurrentLastPageNum() {
		if (this.getCurrentFirstPageNum() + this.getPageNaviCount() - 1 > this.getLastPageNum()) {
			return this.getLastPageNum();
		}
		return this.getCurrentFirstPageNum() + this.getPageNaviCount() - 1;
	}

	public Long getFirstPageNum() {
		return 1L;
	}

	public Long getLastPageNum() {
		return getListCount() / getPageSize() + (Math.floorMod(getListCount(), getPageSize()) > 0 ? 1 : 0);
	}

	public Long getPrevPageNaviNum() {
		if (this.getCurrentFirstPageNum().intValue() == this.getFirstPageNum().intValue()) {
			return getFirstPageNum();
		}

		return this.getCurrentFirstPageNum() - 1;
	}

	public Long getNextPageNaviNum() {
		if (this.getCurrentLastPageNum().intValue() == this.getLastPageNum().intValue()) {
			return this.getLastPageNum();
		}
		return this.getCurrentLastPageNum() + 1;
	}

	public Long getRowNumCorrection() {
		return rowNumCorrection;
	}

	public void setRowNumCorrection(Long rowNumCorrection) {
		this.rowNumCorrection = rowNumCorrection;
	}

}
