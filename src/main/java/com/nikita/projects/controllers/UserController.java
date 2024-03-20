package com.nikita.projects.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.Contact;
import com.nikita.projects.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@ModelAttribute
	public void commomData(Model m, Principal principal) {
		
		String userName = principal.getName();
		System.out.println("USERNAME " + userName);
		
		User user = userRepo.getUserByUsername(userName);
		System.out.println(user);
		
		m.addAttribute("user", user);
	}
	
	@GetMapping("/index")
	public String dashboard(Model m, Principal principal) {
		m.addAttribute("title", "DashBoard-SmartContactManager");
		return "normal/user_dash";
	}
	
	@GetMapping("/addContact")
	public String addConpage(Model m) {
		m.addAttribute("title", "Add Contact-SmartContactManager");
		m.addAttribute("contact", new Contact());
		return "normal/add_contactform";
	}
	
	@PostMapping("/processContact")
	public String addConpage(@ModelAttribute("contact") Contact contact) {
		System.out.println(contact);
		
		return "normal/add_contactform";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
}
