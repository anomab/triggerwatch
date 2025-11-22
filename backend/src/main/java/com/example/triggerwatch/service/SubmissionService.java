package com.example.triggerwatch.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService 
{
	@Autowired
	private EmailService emailService;
	
	// Static set of trigger words. A set because it holds unique values. Faster
	// look-ups
	private final Set<String> triggerWords = new HashSet<>
	(Arrays.asList("bomb", "blast", "virus", "attack"));
	
	// Pre-compiling regex patterns for each trigger word
	private final List<Pattern> triggerPatterns=triggerWords.stream()
			.map( word -> Pattern.compile("\\b" + word + "\\b", Pattern.CASE_INSENSITIVE))
			.toList();
	
	// Data pre-processing
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
	
	// We tokenize to reduce false positives. Ex. Bombay contains 'bomb'
	private Set<String> tokenize(String input)
	{
		if (input==null || input.isEmpty())
			return Set.of();
		
		return new HashSet<>(Arrays.asList(input.split("\\s+")));
	}
	
	// Searching logic
	public String checkForTriggers(String fullName, String textBody)
	{
		String normalizedName = normalize(fullName);
		String normalizedText = normalize(textBody);
		
		Set<String> nameTokens = tokenize(normalizedName);
		Set<String> textTokens = tokenize(normalizedText);
		
		StringBuilder foundWords = new StringBuilder();
		
		for (Pattern pattern : triggerPatterns)
		{
			Matcher nameMatcher=pattern.matcher(normalizedName);
			Matcher textMatcher=pattern.matcher(normalizedText);
			
			if (nameMatcher.find() || textMatcher.find())
			{
				String word = pattern.pattern().replace("\\b", "");
				
				if (foundWords.length()>0)
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
