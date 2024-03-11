package com.nikita.projects.controllers;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

//import com.nikita.projects.UserRepository;

@RestController
public class MainController {
	
//	@Autowired
//	private UserRepository userRepo;
	
	@GetMapping("/")
	public ModelAndView homeView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title","Home-Smart Contact Manager");
		mv.setViewName("home");
		return mv;
	}
	
	@GetMapping("/about")
	public ModelAndView aboutView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title","About-Smart Contact Manager");
		mv.setViewName("about");
		return mv;
	}
	
	@GetMapping("/signup")
	public ModelAndView signupView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title","Register-Smart Contact Manager");
		mv.setViewName("signup");
		return mv;
	}

//	public UserRepository getUserRepo() {
//		return userRepo;
//	}
//
//	public void setUserRepo(UserRepository userRepo) {
//		this.userRepo = userRepo;
//	}

}
