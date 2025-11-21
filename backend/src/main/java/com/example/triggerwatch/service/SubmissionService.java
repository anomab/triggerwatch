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
	
	private Set<String> tokenize(String input)
	{
		if (input==null || input.isEmpty())
			return Set.of();
		
		return new HashSet<>(Arrays.asList(input.split(" ")));
	}
	
	public String checkForTriggers(String fullName, String textBody)
	{
		String normalizedName = normalize(fullName);
		String normalizedText = normalize(textBody);
		
		Set<String> nameTokens = tokenize(normalizedName);
		Set<String> textTokens = tokenize(normalizedText);
		
		StringBuilder foundWords = new StringBuilder();
		
		for (String word : triggerWords)
		{	
			boolean foundInName = nameTokens.contains(word);
			boolean foundInText = textTokens.contains(word);
			
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
