package com.springboot.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entities.Food;
import com.springboot.entities.FoodOrder;

@Service
public class CartService {
	
	@Autowired
	private FoodService foodService;

	private List<FoodOrder> items = new ArrayList<>();
	
	public void add(Long id_food) {
		Food food = foodService.findById(id_food);
		
		int control = 0;
		for(FoodOrder it : items) {
			if(it.getFood().getId().equals(food.getId())) {
				it.setQtde(it.getQtde() + 1);
				it.setTotalValue(it.getQtde() * it.getFood().getPrice()); 
				control++;
				break;
			} 
		}
	
		if(control == 0) {
			FoodOrder fo = new FoodOrder();
			fo.setFood(food);
			fo.setQtde(1);
			fo.setTotalValue(food.getPrice());
			items.add(fo);
		}
	}
	
	public void delete(Long id_food) {
		for(FoodOrder it : items) {
			if(it.getFood().getId().equals(id_food)) {
				items.remove(it);
				break;
			}
		}
	}
	
	public void emptyCart() {
		items.clear();
	}
	
	public List<FoodOrder> getItems() {
		return items;
	}
	
	public Double getTotalValue() {
		Double total = 0d;
		for(FoodOrder it : items) {
			total += it.getTotalValue();
		}
		return total;
	}
	
	public void updateQuantity(Long id_food, Integer action) {
		for(FoodOrder it : items) {
			if(it.getFood().getId().equals(id_food)) {
				if(action.equals(0)) {
					if(it.getQtde() > 1) {
						it.setQtde(it.getQtde() - 1);
					} else {
						items.remove(it);
					}
				} else {
					it.setQtde(it.getQtde() + 1);
				}
				it.setTotalValue(it.getQtde() * it.getFood().getPrice());
				break;
			}
		}
	}
}
