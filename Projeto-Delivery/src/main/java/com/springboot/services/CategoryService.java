package com.springboot.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Category;
import com.springboot.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();
		categories.sort(Comparator.comparing(Category::getName));
		return categories;
	}
	
	public Category findById(Long id) {
		return categoryRepository.findById(id).get();
	}
}
