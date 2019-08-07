package com.facturachida.auth.service;


import java.util.function.Consumer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.facturachida.auth.data.AuthUser;
import com.facturachida.auth.repository.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {	


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

		AuthUser aUser = userRepository.findByUserName(username);

		if (aUser != null){
			return new User(aUser.getUsername(), aUser.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}


	}



	public AuthUser save(AuthUser user){

		Consumer<AuthUser> userConsumer = u -> u.setPassword(passwordEncoder.encode(u.getPassword()));


		userConsumer.accept(user);
	
		return userRepository.save(user);
	}
}