package com.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.services.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@GetMapping(value = "/cart")
	public ModelAndView cart() {
		ModelAndView mav = new ModelAndView("user/cart");
		mav.addObject("items", cartService.getItems());
		mav.addObject("totalValue", cartService.getTotalValue());
		return mav;
	}
	
	@GetMapping(value = "/addInCart/{id_food}")
	public String addInCart(@PathVariable Long id_food) {
		cartService.add(id_food);
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/deleteItem/{id_food}")
	public String deleteItem(@PathVariable Long id_food) {
		cartService.delete(id_food);
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/updateQuantity/{id_food}/{action}")
	public String updateQuantity(@PathVariable Long id_food, @PathVariable Integer action) {
		cartService.updateQuantity(id_food, action);
		return "redirect:/cart";
	}
	
}
