package com.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Usuario;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Usuario, Long>{

	@Query("select u from Usuario u where u.username = ?1")
	public Usuario findByUsername(String username);
}
