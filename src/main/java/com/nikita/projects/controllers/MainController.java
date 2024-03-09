package com.nikita.projects.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.Contact;
import com.nikita.projects.entities.User;

@RestController
public class MainController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/test")
	public String testJPA() {
		User user = new User();
		user.setName("Nikita");
		user.setEmail("nikita@example.com");
		
		Contact c1 = new Contact();
		c1.setContactNo("12345");
		c1.setName("Abc");
		c1.setUser(user);
		
		Contact c2 = new Contact();
		c2.setContactNo("67890");
		c2.setName("Xyz");
		c2.setUser(user);
		
		List<Contact> contacts = List.of(c1, c2);
		user.setContacts(contacts);
		
		userRepo.save(user);
		
		return "Test Successful!";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

}
