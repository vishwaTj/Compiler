package com.sonycode.compiler.service.api;

public interface CommonService {
 
	 String getApplicationPath();
	 
	 String getRandomFolderName();
	 
	 boolean createFolder(String fileName);
	 
	 void deleteFolder(String fileName);
}
