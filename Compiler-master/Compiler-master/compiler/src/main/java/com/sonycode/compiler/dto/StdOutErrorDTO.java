package com.sonycode.compiler.dto;

public class StdOutErrorDTO {
    
	String stdOut;
	String stdError;
	
	public StdOutErrorDTO(String stdOut,String stdError)
	{
		this.stdOut = stdOut;
		this.stdError = stdError;
	}
	
	public String getStdOut() {
		return stdOut;
	}
	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}
	
	public String getStdError() {
		return stdError;
	}
	public void setStdError(String stdError) {
		this.stdError = stdError;
	}
}
