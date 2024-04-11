package com.nikita.projects.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nikita.projects.entities.User;
import com.nikita.projects.helper.Message;

import com.nikita.projects.UserRepository;

@Controller
public class MainController {
	
	private String UPLOAD_DIR = new ClassPathResource("static/img").getFile().getAbsolutePath();

	public MainController() throws IOException {
		
	}
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String homeView(Model m) {
		m.addAttribute("title", "Home - ContactCanvas");
		return "home";
	}

	@GetMapping("/signup")
	public String signupView(Model m) {
		m.addAttribute("title", "Register - ContactCanvas");
		m.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/processSignup")
	public String processSignup(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam("img") MultipartFile file, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, HttpSession session) {

		try {
			if (!agreement) {
				throw new Exception("Please accept the terms and conditions to proceed with the registration");
			}
			if (result.hasErrors()) {
				return "signup";
			}

			// processing image
			if (!file.isEmpty()) {
				user.setImageUrl(file.getOriginalFilename());
				Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				user.setImageUrl("contact.png");
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User resultUser = userRepo.save(user);
			System.out.println(resultUser + " " + agreement);
			session.setAttribute("message", new Message("Successfully registered!", "alert-success"));
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong! " + e.getMessage(), "alert-danger"));
		}
		return "signup";
	}

	@GetMapping("/login")
	public String loginPage(Model m) {
		m.addAttribute("title", "Login - ContactCanvas");
		return "login";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
