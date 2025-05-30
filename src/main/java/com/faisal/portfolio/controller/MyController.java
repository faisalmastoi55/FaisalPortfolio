package com.faisal.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.faisal.portfolio.helper.Message;
import com.faisal.portfolio.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {
	
	@Autowired
	private UserService userService;

	@GetMapping()
	public String portfolio()
	{
		
		return "faisal_portfolio";
	}
	
	@PostMapping("/sendMail")
	public String sendMail(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("subject") String subject1, @RequestParam("message") String message1,
			HttpSession session)
	{
		String subject = "Client Message";
		String message = "" + "<div>" + "<h2>" + "Name : "+"<b>" + name +"</b>" + "</h2>" 
								+ "<h2>" + "Email : "+"<b>" + email +"</b>" + "</h2>"  
								+ "<h2>" + "Subject : "+"<b>" + subject1 +"</b>" + "</h2>" 
								+ "<h4>" + "Message : "+"<i>" + message1 +"</i>" + "</h4>" + "</div>";
		String to = "faisalmastoi341@gmail.com";

		boolean sendEmail = this.userService.sendEmail(subject, message, to);

		if (sendEmail) {
			
			session.setAttribute("message", new Message("Your message has been sent.", "alert-success"));
				
		} else {

			session.setAttribute("message", new Message("Sorry " + name + ", it seems that our mail server is not responding. Please try again later!", "alert-danger"));
			
		}
		return "redirect:#contact";
	}
}
