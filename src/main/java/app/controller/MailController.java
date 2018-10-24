package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.service.MailService;

@Controller

public class MailController {

	@Autowired
	private MailService mailService;
	
	
	
	private String sendSimpleMail(){
		
		return null;
		
	}
	
}
