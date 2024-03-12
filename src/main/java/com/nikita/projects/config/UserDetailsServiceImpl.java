package com.nikita.projects.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.getUserByUsername(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		CustomUserDetails userDetails = new CustomUserDetails(user); 

		return userDetails;
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	

}
