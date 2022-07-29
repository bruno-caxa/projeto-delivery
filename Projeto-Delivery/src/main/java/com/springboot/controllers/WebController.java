package com.springboot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entities.Food;
import com.springboot.entities.Order;
import com.springboot.entities.OrderStatus;
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
		mav.addObject("food", new Food());
		mav.addObject("categories", categoryService.findAll());
		return mav;
	}
	
	@GetMapping(value = "/listFood")
	public ModelAndView listFood() {
		ModelAndView mav = new ModelAndView("admin/listFood");
		mav.addObject("food", new Food());
		mav.addObject("esfihas", foodService.findAllEsfihas());
		mav.addObject("snacks", foodService.findAllSnacks());
		mav.addObject("drinks", foodService.findAllDrinks());
		mav.addObject("categories", categoryService.findAll());
		return mav;
	}
	
	@GetMapping(value = "/orders")
	public ModelAndView orders(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> status) {
		int currentStatus = status.orElse(0);
		ModelAndView mav = new ModelAndView("admin/orders" + (currentStatus == 0 ? "Pending" : "Delivered"));
		
		int currentPage = page.orElse(1);
		
		Page<Order> orderPage = orderService.findByStatus(PageRequest.of(currentPage - 1, 5), currentStatus == 0 ? OrderStatus.PENDING : OrderStatus.DELIVERED);
		List<Integer> pageNumbers = orderService.totalPages(orderPage);
		
		mav.addObject("orderPage", orderPage);
		mav.addObject("pageNumbers", pageNumbers);
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
	
	@GetMapping(value = "/profits")
	public String profits() {
		return "admin/profits";
	}
	
}
