package com.springboot.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", 
		       joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario"),
		       inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role"))
	private Set<Role> roles = new HashSet<>();
	
	public Usuario() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
