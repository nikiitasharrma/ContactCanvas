package com.nikita.projects.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/index")
	public String dashboard(Model m, Principal principal) {
		m.addAttribute("title", "DashBoard-SmartContactManager");
		
		String userName = principal.getName();
		System.out.println("USERNAME " + userName);
		
		User user = userRepo.getUserByUsername(userName);
		System.out.println(user);
		
		m.addAttribute("user", user);
		
		return "normal/user_dash";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
}
