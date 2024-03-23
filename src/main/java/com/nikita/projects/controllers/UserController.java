package com.nikita.projects.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nikita.projects.ContactRepository;
import com.nikita.projects.UserRepository;
import com.nikita.projects.entities.Contact;
import com.nikita.projects.entities.User;
import com.nikita.projects.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {

	private String UPLOAD_DIR = new ClassPathResource("static/img").getFile().getAbsolutePath();

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ContactRepository contactRepo;

	public UserController() throws IOException {

	}

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
		m.addAttribute("title", "DashBoard - SmartContactManager");
		return "normal/user_dash";
	}

	@GetMapping("/addContact")
	public String addConpage(Model m) {
		m.addAttribute("title", "Add Contact - SmartContactManager");
		m.addAttribute("contact", new Contact());
		return "normal/add_contactform";
	}

	@PostMapping("/processContact")
	public String addContactpage(@Valid @ModelAttribute("contact") Contact contact,
			@RequestParam("img") MultipartFile file, Principal principal, HttpSession session) {
		try {
			User user = userRepo.getUserByUsername(principal.getName());
			contact.setUser(user);
			contactRepo.save(contact);

			// processing image
			if (!file.isEmpty()) {
				contact.setImgUrl(file.getOriginalFilename());
				Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				contact.setImgUrl("contact.png");
			}

			user.getContacts().add(contact);
			userRepo.save(user);

			session.setAttribute("message", new Message("Contact successfully added to list", "alert-success"));

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong!", "alert-danger"));
		}

		return "normal/add_contactform";
	}

	@GetMapping("/all-contacts/{page}")
	public String showAllContacts(Model m, Principal principal, @PathVariable("page") int page) {
		m.addAttribute("title", "Your contacts - SmartContactManager");

		Pageable pageable = PageRequest.of(page, 5);

		User user = userRepo.getUserByUsername(principal.getName());
		Page<Contact> contacts = contactRepo.getContactsByUser(user.getId(), pageable);

		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());

		return "normal/your_contacts";
	}

	@GetMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Integer id, Model m, Principal principal) {

		User user = userRepo.getUserByUsername(principal.getName());

		Contact contact = contactRepo.findById(id).get();

		// verifying that the contact is associated with the logged in user
		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
			m.addAttribute("title", contact.getName() + "-SmartContact5Manager");
		} else {
			m.addAttribute("title", "NotFound - SmartContactManager");
		}

		return "normal/specific-contact";
	}

	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer id, Principal principal, HttpSession session)
			throws IOException {

		User user = userRepo.getUserByUsername(principal.getName());

		Contact contact = contactRepo.findById(id).get();

		if (user.getId() == contact.getUser().getId()) {

			// deleting image from saved path
			Files.deleteIfExists(Paths.get(UPLOAD_DIR + File.separator + contact.getImgUrl()));

			contactRepo.deleteById(id);
		}

		return "redirect:/user/all-contacts/0";
	}

	@GetMapping("/update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer id, Model m, Principal principal) {

		User user = userRepo.getUserByUsername(principal.getName());
		Contact contact = contactRepo.findById(id).get();

		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("title", "Update Contact - SmartContactManager");
			m.addAttribute("contact", contact);
		}

		return "normal/update_form";
	}

	@PostMapping("/process-updated-contact")
	public String processUpdatedContact(@Valid @ModelAttribute("contact") Contact contact, Principal principal,
			@RequestParam("img") MultipartFile file, Model m, HttpSession session) throws IOException {

		User user = userRepo.getUserByUsername(principal.getName());

		try {

			if (file != null) {
				Files.deleteIfExists(Paths.get(UPLOAD_DIR + File.separator + contact.getImgUrl()));
				contact.setImgUrl(file.getOriginalFilename());
				Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
			}

			contact.setUser(user);
			contactRepo.save(contact);
			m.addAttribute("contact", contact);
			session.setAttribute("message", new Message("Contact successfully updated", "alert-success"));
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Contact successfully updated", "alert-success"));
		}

		return "normal/update_form";
	}
	
	@GetMapping("/your-profile")
	public String showProfile(Model m) {
		m.addAttribute("title", "Your Profile - SmartContactManager");
		return "normal/your_profile";
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
