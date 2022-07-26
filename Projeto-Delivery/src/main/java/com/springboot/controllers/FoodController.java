package com.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ModelAndView deleteFood(@PathVariable Long id_food) {
		ModelAndView mav = new ModelAndView("index");
		foodService.deleteById(id_food);
		mav.addObject("msg","Alimento deletado com sucesso!");
		return mav;
	}
	
	@GetMapping(value = "/getFood")
	public ResponseEntity<Food> getFood(Long id_food) {
		Food food = foodService.findById(id_food);
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveFood")
	public ModelAndView saveFood(Food food, MultipartFile fileImage) {
		ModelAndView mav = new ModelAndView("index");
		foodService.save(food, fileImage);
		mav.addObject("msg","Alimento salvo com sucesso!");
		return mav;
	}
	
	@PostMapping(value = "**/searchFood")
	public ModelAndView searchFood(String foodSearch) {
		ModelAndView mav = new ModelAndView("user/search");
		mav.addObject("foodSearch", foodSearch);
		mav.addObject("foods", foodService.search(foodSearch));
		return mav;
	}
	
	@GetMapping(value = "/showImage/{id_food}")
	@ResponseBody
	public byte[] showImage(@PathVariable Long id_food) {
		return foodService.findById(id_food).getImage();
	}
	
}
