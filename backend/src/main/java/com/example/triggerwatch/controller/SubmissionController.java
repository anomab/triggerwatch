package com.example.triggerwatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.triggerwatch.dto.SubmitRequest;
import com.example.triggerwatch.service.SubmissionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubmissionController 
{
	private final SubmissionService submmissionService;

	public SubmissionController(SubmissionService submmissionService) 
	{
		super();
		this.submmissionService = submmissionService;
	}
	
	@PostMapping("/submit")
	public ResponseEntity<String> submit(@RequestBody SubmitRequest req)
	{
		String result = submmissionService.checkForTriggers
				(req.getFullName(), req.getTextBody());
		
		return ResponseEntity.ok(result);
	}
	
}
