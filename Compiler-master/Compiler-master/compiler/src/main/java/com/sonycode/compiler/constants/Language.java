package com.sonycode.compiler.constants;

public enum Language {
	
	PYTHON("Python"),
	CPP("Cpp"),
	JAVA("Java"),
	C("C"),
	CHASH("CHASH"),
	JAVASCRIPT("JavaScript");
	
	private final String name;
	
	private Language(String name)
	{
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
