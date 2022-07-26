package com.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entities.Order;
import com.springboot.entities.OrderStatus;
import com.springboot.services.CartService;
import com.springboot.services.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping(value = "/deleteOrder/{id_order}")
	public ModelAndView deleteOrder(@PathVariable Long id_order) {
		ModelAndView mav = new ModelAndView("admin/ordersDelivered");
		orderService.deleteById(id_order);
		mav.addObject("orders", orderService.findByStatus(OrderStatus.DELIVERED));
		return mav;
	}
	
	@PostMapping(value = "/finalizingOrder")
	public ModelAndView finalizingOrder(Order order) {
		ModelAndView mav = new ModelAndView("index");
		orderService.save(order);
		mav.addObject("msg","Pedido realizado com sucesso. Aguarde até a entrega!");
		return mav;
	}
	
	@GetMapping(value = "/finishOrder")
	public ModelAndView finishOrder() {
		ModelAndView mav = new ModelAndView("user/finishOrder");
		mav.addObject("items", cartService.getItems());
		mav.addObject("totalValue", cartService.getTotalValue());
		return mav;
	}
	
	@GetMapping(value = "/montlhyProfits")
	public ResponseEntity<List<Order>> montlhyProfits(int month) {
		return new ResponseEntity<List<Order>>(orderService.montlhyProfits(month), HttpStatus.OK);
	}
	
	@GetMapping(value = "/orderDelivered/{id_order}")
	public ModelAndView orderDelivered(@PathVariable Long id_order) {
		ModelAndView mav = new ModelAndView("admin/ordersPending");
		Order order = orderService.findById(id_order);
		orderService.delivered(order);
		mav.addObject("orders", orderService.findByStatus(OrderStatus.PENDING));
		return mav;
	}
}
