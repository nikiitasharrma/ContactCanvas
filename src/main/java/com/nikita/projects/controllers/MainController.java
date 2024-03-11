package com.nikita.projects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nikita.projects.entities.User;
import com.nikita.projects.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.nikita.projects.UserRepository;

@Controller
public class MainController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String homeView(Model m) {
		m.addAttribute("title", "Home-Smart Contact Manager");
		return "home";
	}

	@GetMapping("/about")
	public String aboutView(Model m) {
		m.addAttribute("title", "About-Smart Contact Manager");
		return "about";
	}

	@GetMapping("/signup")
	public String signupView(Model m) {
		m.addAttribute("title", "Register-Smart Contact Manager");
		m.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/processSignup")
	public String processSignup(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model m,
			HttpSession session) {

		try {
			if (!agreement) {
				throw new Exception("Please accept the terms and conditions to proceed with the registration");
			}
			if (result.hasErrors()) {
				return "signup";
			}
			User resultUser = userRepo.save(user);
			System.out.println(resultUser + " " + agreement);
			session.setAttribute("message", new Message("Successfully registered!", "alert-success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong! " + e.getMessage(), "alert-danger"));
		}
		return "signup";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

}
