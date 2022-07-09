package com.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.services.CategoryService;
import com.springboot.services.FoodService;
import com.springboot.services.OrderService;

@Controller
public class WebController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private OrderService orderService;

	@GetMapping(value = {"/","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "/addFood")
	public ModelAndView addFood() {
		ModelAndView mav = new ModelAndView("admin/addFood");
		mav.addObject("categories", categoryService.findAll());
		return mav;
	}
	
	@GetMapping(value = "/listFood")
	public ModelAndView listFood() {
		ModelAndView mav = new ModelAndView("admin/listFood");
		mav.addObject("foods", foodService.findAll());
		return mav;
	}
	
	@GetMapping(value = "/orders")
	public ModelAndView orders() {
		ModelAndView mav = new ModelAndView("admin/orders");
		mav.addObject("orders", orderService.findAllPending());
		return mav;
	}
	
	@GetMapping(value = "/placeOrder")
	public ModelAndView placeOrder() {
		ModelAndView mav = new ModelAndView("user/placeOrder");
		mav.addObject("esfihas", foodService.findAllEsfihas());
		mav.addObject("snacks", foodService.findAllSnacks());
		mav.addObject("drinks", foodService.findAllDrinks());
		return mav;
	}
	
}
