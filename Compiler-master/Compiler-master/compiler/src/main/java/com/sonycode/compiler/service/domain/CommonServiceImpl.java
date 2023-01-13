package com.sonycode.compiler.service.domain;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sonycode.compiler.service.api.CommonService;

@Component
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	ServletContext context;   
	
	Logger logger = LoggerFactory.getLogger(CommonService.class);
	
    public String getApplicationPath()
     {
    	 return System.getProperty("user.dir");
     }
    
    public String getRandomFolderName()
     {
    	return RandomStringUtils.random(5, String.valueOf(System.currentTimeMillis()));
     }
    
    public boolean createFolder(String fileName)
    {
    	File dir = new File(fileName);
    	boolean isCreated = dir.mkdir();
    	return isCreated;
    }
    
    public void deleteFolder(String fileName)
    {
    	File dir = new File(fileName);
    	deleteDir(dir);
    }
    public void deleteDir(File dirFile) {
        if (dirFile.isDirectory()) {
            File[] dirs = dirFile.listFiles();
            for (File dir: dirs) {
                deleteDir(dir);
            }
        }
        dirFile.delete();
    }    
    
}
