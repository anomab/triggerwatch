package com.example.triggerwatch.dto;

public class SubmitRequest 
{
	private String fullName;
	private String textBody;
	
	
	public SubmitRequest() {}


	public SubmitRequest(String fullName, String textBody) 
	{
		super();
		this.fullName = fullName;
		this.textBody = textBody;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getTextBody() {
		return textBody;
	}


	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}
	
	
	

}
