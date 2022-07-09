package com.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Usuario;
import com.springboot.repositories.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userRepository.findByUsername(username);

		if(user == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		return new User(user.getUsername(),
					    user.getPassword(),
					    true, true, true, true,
					    user.getAuthorities());
	}
	
}
