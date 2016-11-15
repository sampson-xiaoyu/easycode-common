package com.easycode.commons.pagination;

public class Pagination {

	private int pageNum = 1;
	private int pageSize = 10;
	private int total;
	private int fromRecord;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFromRecord() {
		return fromRecord;
	}

	public void setFromRecord(int fromRecord) {
		this.fromRecord = fromRecord;
	}

	public void compute() {
		if (pageSize < 1) {
			pageSize = 10;
		}
		if (pageNum < 1) {
			fromRecord = total;
		} else {
			int totalPage = (total % pageSize == 0) ? (total / pageSize) : ((total / pageSize) + 1);
			if (pageNum <= totalPage) {
				fromRecord = (pageNum - 1) * pageSize;
			} else {
				fromRecord = total;
			}
		}
	}
	
	public static int start(int pageNum, int pageSize, int total) {
		int start = 0;
		if (pageSize < 1) {
			pageSize = 12;
		}
		if (pageNum < 1) {
			start = total;
		} else {
			int totalPage = (total % pageSize == 0) ? (total / pageSize) : ((total / pageSize) + 1);
			if (pageNum <= totalPage) {
				start = (pageNum - 1) * pageSize;
			} else {
				start = total;
			}
		}
		return start;
	}
	
	public static int start(int pageNum, int pageSize) {
		if(pageNum <= 0) {
			return 0;
		}
		if(pageSize == 0) {
			pageSize = 12;
		}
		return (pageNum - 1) * pageSize;
	}

}
