package com.springboot.services;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.FoodOrder;
import com.springboot.entities.Order;
import com.springboot.entities.OrderStatus;
import com.springboot.repositories.FoodOrderRepository;
import com.springboot.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private FoodOrderRepository foodOrderRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	public void delivered(Order order) {
		order.setStatus(OrderStatus.DELIVERED);
		orderRepository.save(order);
	}
	
	public List<Order> findAllPending() {
		return orderRepository.findAll()
							  .stream()
							  .filter(o -> o.getStatus() == OrderStatus.PENDING)
							  .collect(Collectors.toList());
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}
	
	public void save(Order order) {
		for(FoodOrder it : cartService.getItems()) {
			foodOrderRepository.save(it);
		}
		order.setFoodOrder(cartService.getItems());
		order.setDate(Calendar.getInstance().getTime());
		order.setStatus(OrderStatus.PENDING);
		orderRepository.save(order);
		cartService.emptyCart();
	}
}
