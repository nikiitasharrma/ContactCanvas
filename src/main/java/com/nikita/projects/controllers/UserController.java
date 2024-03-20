package com.nikita.projects.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String dashboard(Model m) {
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
	public String addConpage(@Valid @ModelAttribute("contact") Contact contact, @RequestParam("img") MultipartFile file,
			Principal principal) {
		try {
			User user = userRepo.getUserByUsername(principal.getName());
			contact.setUser(user);

			// processing image
			if (!file.isEmpty()) {
				contact.setImgUrl(file.getOriginalFilename());
				String uploadDir = new ClassPathResource("static/img").getFile().getAbsolutePath();
				Files.copy(file.getInputStream(), Paths.get(uploadDir + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}

			user.getContacts().add(contact);
			userRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "normal/add_contactform";
	}

	public UserRepository getUserRepo() {
		return userRepo;
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

}
