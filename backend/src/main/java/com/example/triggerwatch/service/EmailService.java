package com.example.triggerwatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendTriggerAlert(String foundWords, String fullName)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("anomaabasu@gmail.com");
		message.setSubject("Trigger words detected");
		message.setText("Ful name: " + fullName + "Detected trigger words: " +foundWords);
		
		mailSender.send(message);
	}
}
