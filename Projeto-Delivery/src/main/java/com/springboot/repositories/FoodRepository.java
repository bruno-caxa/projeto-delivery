package com.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Food;

@Repository
@Transactional
public interface FoodRepository extends JpaRepository<Food, Long>{

	@Query("select f from Food f where f.category.id = 1")
	public List<Food> findAllEsfihas();
	
	@Query("select f from Food f where f.category.id = 2")
	public List<Food> findAllSnacks();
	
	@Query("select f from Food f where f.category.id = 3")
	public List<Food> findAllDrinks();
	
	@Query("select f from Food f where lower(f.name) like lower(concat('%', :food, '%'))")
	public List<Food> search(@Param("food") String food);
}
