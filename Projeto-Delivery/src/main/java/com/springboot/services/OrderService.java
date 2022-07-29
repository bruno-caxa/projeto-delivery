package com.springboot.services;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.controllers.PageableUtil;
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
	
	@Autowired
	private PageableUtil<Order> pageableUtil;
	
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
	
	public Page<Order> findByStatus(Pageable pageable, OrderStatus status) {
		List<Order> orders = orderRepository.findAll()
											.stream()
											.filter(o -> o.getStatus() == status)
											.sorted(Comparator.comparing(Order::getDate))
											.collect(Collectors.toList());;
		
		return pageableUtil.pagination(pageable, orders);
	}

	public Map<Date, Double> montlhyProfits(String month) {
		List<Tuple> orders = orderRepository.montlhyProfits(month);
		return orders.stream().collect(Collectors.toMap(o -> o.get(0, Date.class),
														o -> o.get(1, Double.class)));
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
	
	public List<Integer> totalPages(Page<Order> page) {
		return pageableUtil.totalPages(page);
	}
	
}
