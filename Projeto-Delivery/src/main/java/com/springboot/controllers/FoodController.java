package com.springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entities.Food;
import com.springboot.services.FoodService;

@Controller
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	@GetMapping(value = "/deleteFood/{id_food}")
	public String deleteFood(@PathVariable Long id_food) {
		foodService.deleteById(id_food);
		return "redirect:/listFood";
	}
	
	@GetMapping(value = "/getFood")
	public ResponseEntity<Food> getFood(Long id_food) {
		Food food = foodService.findById(id_food);
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveFood")
	public String saveFood(Food food, MultipartFile fileImage) {
		foodService.save(food, fileImage);
		return "redirect:/listFood";
	}
	
	@GetMapping(value = "/searchFood")
	public ModelAndView searchFood(@RequestParam String food, @RequestParam Optional<Integer> page) {
		ModelAndView mav = new ModelAndView("user/search");
		
		int currentPage = page.orElse(1);
		
		Page<Food> foodPage = foodService.search(PageRequest.of(currentPage - 1, 5), food);
		List<Integer> pageNumbers = foodService.totalPages(foodPage);
		
		mav.addObject("foodPage", foodPage);
		mav.addObject("foodSearch", food);
		mav.addObject("pageNumbers", pageNumbers);
		return mav;
	}
	
	@GetMapping(value = "/showImage/{id_food}")
	@ResponseBody
	public byte[] showImage(@PathVariable Long id_food) {
		return foodService.findById(id_food).getImage();
	}
	
}
