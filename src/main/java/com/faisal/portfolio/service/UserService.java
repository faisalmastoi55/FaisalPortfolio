package com.faisal.portfolio.service;

public interface UserService {

	public boolean sendEmail(String subject, String message, String to);
	
	public void removeSessionMessage();
}
