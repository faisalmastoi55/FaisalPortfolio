package com.faisal.portfolio.service;

public interface UserService {

	boolean sendEmail(String subject, String body, String to);
	void sendAutoReply(String name, String email, String subject);
	boolean processClientEmail(String name, String email, String subject1, String message1);
	
	void removeSessionMessage();
}
