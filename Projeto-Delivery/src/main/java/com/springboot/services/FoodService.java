package com.springboot.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Food;
import com.springboot.repositories.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
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
	
	public void save(Food food) {
		foodRepository.save(food);
	}
	
}
