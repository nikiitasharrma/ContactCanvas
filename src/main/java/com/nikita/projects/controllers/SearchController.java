package com.nikita.projects.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.projects.ContactRepository;
import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.Contact;
import com.nikita.projects.entities.User;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContactRepository contactRepo;
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<?> searchContacts(@PathVariable("keywords") String keywords, Principal principal){
		System.out.println(keywords);
		User user = userRepo.getUserByUsername(principal.getName());
		
		List<Contact> contacts = contactRepo.findByNameContainingAndUser(keywords, user);
		
		return ResponseEntity.ok(contacts);
	}
	
	public UserRepository getUserRepo() {
		return userRepo;
	}
	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	public ContactRepository getContactRepo() {
		return contactRepo;
	}
	public void setContactRepo(ContactRepository contactRepo) {
		this.contactRepo = contactRepo;
	}
	
	
}
