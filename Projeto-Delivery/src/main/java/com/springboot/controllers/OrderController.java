package com.springboot.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entities.Order;
import com.springboot.services.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping(value = "/deleteOrder/{id_order}")
	public String deleteOrder(@PathVariable Long id_order) {
		orderService.deleteById(id_order);
		return "redirect:/orders?status=1";
	}
	
	@PostMapping(value = "/finalizingOrder")
	public ModelAndView finalizingOrder(Order order) {
		ModelAndView mav = new ModelAndView("index");
		orderService.save(order);
		mav.addObject("msg","Pedido realizado com sucesso. Aguarde até a entrega!");
		return mav;
	}
	
	@GetMapping(value = "/montlhyProfits")
	public ResponseEntity<Map<Date, Double>> montlhyProfits(String month) {
		return new ResponseEntity<Map<Date,Double>>(orderService.montlhyProfits(month), HttpStatus.OK);
	}
	
	@GetMapping(value = "/orderDelivered/{id_order}")
	public String orderDelivered(@PathVariable Long id_order) {
		Order order = orderService.findById(id_order);
		orderService.delivered(order);
		return "redirect:/orders?status=0";
	}
}
