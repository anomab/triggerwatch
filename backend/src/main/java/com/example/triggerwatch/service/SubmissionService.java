package com.example.triggerwatch.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService 
{
	@Autowired
	private EmailService emailService;
	
	private final Set<String> triggerWords = new HashSet<>
	(Arrays.asList("bomb", "blast", "virus", "attack"));
	
	private String normalize(String input)
	{
		if (input == null)
			return "";
		
		return input
				.toLowerCase()
				.replaceAll("[^a-z\\s]", "")
				.replaceAll("\\s+", " ")
				.trim();
	}
	
	
	
	public String checkForTriggers(String fullName, String textBody)
	{
		String normalizedName = normalize(fullName);
		String normalizedText = normalize(textBody);
		
		// The following logic only identifies the first instance of a trigger word
		// stops execution
		
		/*
		for (String word : triggerWords)
		{
			String lowerWord = word.toLowerCase();
			
			if (lowerName.contains(word) || lowerText.contains(word))
			{
				return "Trigger word found: " + word;
			}
		}
		
		return "No trigger word found";
		*/
		
		StringBuilder foundWords = new StringBuilder();
		
		for (String word : triggerWords)
		{
			String lowerWord = word.toLowerCase();
			
			boolean foundInName = normalizedName.contains(lowerWord);
			boolean foundInText = normalizedText.contains(lowerWord);
			
			if (foundInName || foundInText)
			{
				if (foundWords.length() > 0)
				{
					foundWords.append(", ");
				}
				foundWords.append(word);
			}
		}
		
		if (foundWords.length() == 0)
		{
			return "No trigger words found.";
		}
		
		emailService.sendTriggerAlert(foundWords.toString(), fullName);
		return "Trigger words found: " +foundWords;
	}
}
