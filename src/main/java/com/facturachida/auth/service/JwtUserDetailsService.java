package com.facturachida.auth.service;


import java.util.ArrayList;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.facturachida.auth.data.Authuser;
import com.facturachida.auth.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

		Authuser aUser = userRepository.findByUsername(username);

		if (aUser != null){
			
			User user = new User(aUser.getUsername(), aUser.getPassword(), aUser.isActive(), true, true, true, new ArrayList<>());
		
			return user;
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}



	public Authuser save(Authuser user){

		Consumer<Authuser> userConsumer = u -> u.setPassword(passwordEncoder.encode(u.getPassword()));
		userConsumer.accept(user);
		
	
		return userRepository.insert(user);
	}
}