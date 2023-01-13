package com.sonycode.compiler.service.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sonycode.compiler.dto.StdOutErrorDTO;

public class CommandLineExecutor implements Runnable  {

	Logger logger = LoggerFactory.getLogger(CommandLineExecutor.class);
	
	StdOutErrorDTO stdOutErrorDTO;
	
	String command;
	String filePath;
	
	public CommandLineExecutor(String filePath,String command)
	{
		this.filePath = filePath;
		this.command = command;
	}
		
	@Override
	public void run() {
		try {
			
			stdOutErrorDTO = runProcess(this.filePath, this.command);
		} catch (Exception e) {
			stdOutErrorDTO = new StdOutErrorDTO("",e.getMessage());
		}
	}
	
	 public  StdOutErrorDTO  runProcess(String filePath,String command) throws Exception {
		    logger.info("runProcess filePath : {} , command : {}",filePath,command);
		    File file = new File(filePath);
		    String[] commands = {  "/bin/bash","-c",command};
		    ProcessBuilder builder = new ProcessBuilder(commands);
		    builder.directory(file);
		    Process pro = builder.start();
		    pro.waitFor();
	        String stdout = printLines(command + " stdout:", pro.getInputStream());
	        String stderr = printLines(command + " stderr:", pro.getErrorStream());
	        pro.waitFor();
	        logger.info(command + " exitValue() " + pro.exitValue());
	        return new StdOutErrorDTO(stdout,stderr);
	      }
	 
	 public String printLines(String cmd, InputStream ins) throws Exception {
	        String allLines = "";
		    String line = null;
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(ins));
	        while ((line = in.readLine()) != null) {
	            logger.info(cmd + " " + line);
	            allLines = allLines + line + "\n";
	        }
	        return allLines;
	   }
	 
	 public StdOutErrorDTO getStdOutErrorDTO()
	 {
		 return stdOutErrorDTO;
	 }

}
