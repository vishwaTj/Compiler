package com.sonycode.compiler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputDTO {
	
	@JsonProperty(value = "CompileTimeOutput")
	String compileTimeOutput = "";
	@JsonProperty(value = "CompileTimeError")
	String compileTimeError  = "";
	@JsonProperty(value = "RunTimeOutput")
	String runTimeOutput  = "";
	@JsonProperty(value = "RunTimeError")
	String runTimeError  = "";
	

	public String getCompileTimeOutput() {
		return compileTimeOutput;
	}
	public void setCompileTimeOutput(String compileTimeOutput) {
		this.compileTimeOutput = compileTimeOutput;
	}
	public String getCompileTimeError() {
		return compileTimeError;
	}
	public void setCompileTimeError(String compileTimeError) {
		this.compileTimeError = compileTimeError;
	}
	public String getRunTimeOutput() {
		return runTimeOutput;
	}
	public void setRunTimeOutput(String runTimeOutput) {
		this.runTimeOutput = runTimeOutput;
	}
	public String getRunTimeError() {
		return runTimeError;
	}
	public void setRunTimeError(String runTimeError) {
		this.runTimeError = runTimeError;
	}
	
}
