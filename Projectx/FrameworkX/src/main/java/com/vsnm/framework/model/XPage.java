package com.vsnm.framework.model;

public class XPage<E> {

	E filter;
	int pageNumber = 0;
	int size = 0;
	String orderBy;
	String order;
	Iterable<E> content;
	long totalElements;
	int totalPages;
	int numberOfElements;

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Iterable<E> getContent() {
		return content;
	}

	public void setContent(Iterable<E> content) {
		this.content = content;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public E getFilter() {
		return filter;
	}

	public void setFilter(E filter) {
		this.filter = filter;
	}

}
