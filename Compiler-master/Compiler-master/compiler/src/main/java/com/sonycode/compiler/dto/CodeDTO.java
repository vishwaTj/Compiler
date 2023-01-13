package com.sonycode.compiler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeDTO {
	
	@JsonProperty(value = "Code")
	String code;
	
	@JsonProperty(value = "Language")
	String language;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Override
	public String toString()
	{
		return "Code = "+ code +
				"\n Language =" + language ;
	}
	
}
