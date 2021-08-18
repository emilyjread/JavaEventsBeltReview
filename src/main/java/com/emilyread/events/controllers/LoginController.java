package com.emilyread.events.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emilyread.events.models.User;
import com.emilyread.events.services.UserService;
import com.emilyread.events.validators.UserValidator;

@Controller
public class LoginController {
	private final UserService userService;
	private final UserValidator userValidator;
	
	public LoginController(UserService userService, UserValidator userValidator) {
		this.userValidator= userValidator;
		this.userService = userService;
	}
	

	//home page

	@RequestMapping("/")
	public String login(@ModelAttribute("user") User user) {
		return "login.jsp";
	}
	
	//register
	
	@RequestMapping(value="/registration",  method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "/login.jsp";
		}
		else {
			User u = userService.registerUser(user);
			session.setAttribute("user_id", u.getId());
			return "redirect:/dashboard";
		}
	}
	
	//Login verification

	
	@RequestMapping(value="/login",  method=RequestMethod.POST)
	public String login(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session){
		
		if(result.hasErrors()) {
			return "/login.jsp";
		}
		else {
			
			userService.authenticateUser(email, password);
			User u = userService.findByEmail(email);
			if(u==null) {
				return "redirect:/";
			}
			else {
				session.setAttribute("user_id", u.getId());
				return "redirect:/dashboard";				
			}
		}
	}
	
	
	
	//logout
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
}
}
