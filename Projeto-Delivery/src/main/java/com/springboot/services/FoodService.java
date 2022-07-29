package com.springboot.services;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.controllers.PageableUtil;
import com.springboot.entities.Food;
import com.springboot.repositories.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private PageableUtil<Food> pageableUtil;
	
	public void deleteById(Long id) {
		foodRepository.deleteById(id);
	}
	
	public List<Food> findAll() {
		List<Food> foods = foodRepository.findAll();
		foods.sort(Comparator.comparing(Food::getName));
		return foods;
	}
	
	public List<Food> findAllDrinks() {
		List<Food> drinks = foodRepository.findAllDrinks();
		drinks.sort(Comparator.comparing(Food::getName));
		return drinks;
	}
	
	public List<Food> findAllEsfihas() {
		List<Food> esfihas = foodRepository.findAllEsfihas();
		esfihas.sort(Comparator.comparing(Food::getName));
		return esfihas;
	}
	
	public List<Food> findAllSnacks() {
		List<Food> snacks = foodRepository.findAllSnacks();
		snacks.sort(Comparator.comparing(Food::getName));
		return snacks;
	}
	
	public Food findById(Long id) {
		return foodRepository.findById(id).get();
	}
	
	public void save(Food food, MultipartFile image) {
		try {
			if(!image.isEmpty()) {
				food.setImage(image.getBytes());
			} else {
				if(food.getId() != null) {
					byte[] picture = findById(food.getId()).getImage();
					food.setImage(picture);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		foodRepository.save(food);
	}
	
	public Page<Food> search(Pageable pageable, String food) {
		List<Food> foods = foodRepository.search(food);
		foods.sort(Comparator.comparing(Food::getName));

		return pageableUtil.pagination(pageable, foods);
	}
	
	public List<Integer> totalPages(Page<Food> page) {
		return pageableUtil.totalPages(page);
	}
	
}
