package com.springboot.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableUtil<C> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Page<C> pagination(Pageable pageable, List<C> listPageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		
		List<C> list;
		
		if(listPageable.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, listPageable.size());
			list = listPageable.subList(startItem, toIndex);
		}
		
		Page<C> page = new PageImpl<C>(list, PageRequest.of(currentPage, pageSize), listPageable.size());
		
		return page;
	}
	
	public List<Integer> totalPages(Page<C> page) {
		int totalPages = page.getTotalPages();
		List<Integer> pageNumbers = new ArrayList<>();
		
		if(totalPages > 0) {
			pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
		}
		
		return pageNumbers;
	}
}
