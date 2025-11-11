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
	public String sendMail(@RequestParam("name") String name,
						   @RequestParam("email") String email,
						   @RequestParam("subject") String subject1,
						   @RequestParam("message") String message1,
						   HttpSession session) {

		boolean sent = userService.processClientEmail(name, email, subject1, message1);

		if (sent) {
			session.setAttribute("message",
					new Message("Your message has been sent successfully!", "alert-success"));
		} else {
			session.setAttribute("message",
					new Message("Sorry " + name + ", it seems our mail server is not responding. Please try again later!",
							"alert-danger"));
		}

		return "redirect:#contact";
	}
}
