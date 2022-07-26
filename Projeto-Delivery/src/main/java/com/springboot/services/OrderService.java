package com.springboot.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}
	
	public void delivered(Order order) {
		order.setStatus(OrderStatus.DELIVERED);
		orderRepository.save(order);
	}
	
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}

	public List<Order> findByStatus(OrderStatus status) {
		return orderRepository.findAll()
							  .stream()
							  .filter(o -> o.getStatus() == status)
							  .collect(Collectors.toList());
	}
	
	public List<Order> montlhyProfits(int month) {
		List<Order> orders = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		
		for(Order order : orderRepository.findAll()) {
			calendar.setTime(order.getDate());
			int monthAux = calendar.get(Calendar.MONTH);
			
			if(monthAux == month) {
				orders.add(order);
			}
		}
		
		return orders;
	}
	
	public void save(Order order) {
		List<FoodOrder> items = cartService.getItems();
		foodOrderRepository.saveAll(items);
		order.setFoodOrder(items);
		order.setDate(Calendar.getInstance().getTime());
		order.setStatus(OrderStatus.PENDING);
		orderRepository.save(order);
		cartService.emptyCart();
	}

}
