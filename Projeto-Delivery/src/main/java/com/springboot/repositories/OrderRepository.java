package com.springboot.repositories;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Order;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select o.date, sum(o.totalValue) from Order o where TO_CHAR(o.date, 'MM') = ?1 group by 1 order by o.date")
	public List<Tuple> montlhyProfits(String month);
	
}
