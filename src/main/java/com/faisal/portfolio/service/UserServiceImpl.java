package com.faisal.portfolio.service;

import java.util.Properties;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public boolean sendEmail(String subject, String message, String to) {
		
		boolean f = false;

		// rest of the code
		String from = "faisalmastoi341@gmail.com";

		// variable for gmail
		String host = "smtp.gmail.com";

		// get the system properties
		Properties properties = new Properties();
		System.out.println("Properties : " + properties);

		// setting important information to properties object

		// host set
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.host", host);

		String userName = "faisalmastoi341";
		String password = "puzywgrnpumfyoei";

		// Step 1: to get the session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(userName, password);
			}

		});

		try {

			// Step 2: compose the message [text, multi media]
			Message message2 = new MimeMessage(session);

			// add recipient to message
			message2.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// from email
			message2.setFrom(new InternetAddress(from));

			// adding subject to message
			message2.setSubject(subject);

			// adding text to message
			/* message2.setText(message); */
			message2.setContent(message, "text/html");

			// send
			// Step 3: send the message using Transport class
			Transport.send(message2);

			System.out.println("Send success............");

			f = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return f;

		
	}
	
	@Override
	public void removeSessionMessage() {
		HttpSession sessions = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		sessions.removeAttribute("message");

	}

}
