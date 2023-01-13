package com.sonycode.compiler.service.domain;

import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sonycode.compiler.dto.CodeDTO;
import com.sonycode.compiler.dto.OutputDTO;
import com.sonycode.compiler.dto.StdOutErrorDTO;
import com.sonycode.compiler.service.api.CommonService;
import com.sonycode.compiler.service.api.JavaScriptCompilerService;
import com.sonycode.compiler.service.api.PythonCompilerService;

@Component
public class JavaScriptCompilerServiceImpl implements JavaScriptCompilerService {
	
Logger logger = LoggerFactory.getLogger(JavaScriptCompilerService.class);
	
	@Autowired
	CommonService commonService;
	
	@Override
	public String compileCode(CodeDTO codeDTO) 
	{
		String output = "No OutPut To Print";
		OutputDTO outPutDTO = new OutputDTO();
		String filePath = new String();
		try {
			   String applicationPath = commonService.getApplicationPath();
			   String randomFolderName = commonService.getRandomFolderName();
			   filePath = applicationPath + "/src/main/resources/CodeFiles/JavaScriptFile/"+ randomFolderName + "/";	   
			   commonService.createFolder(filePath);
			   logger.info("FilePath : {}",filePath);
			   String fileName = "Main";
			   FileWriter fWriter = new FileWriter(filePath + fileName + ".js");
			   fWriter.write(codeDTO.getCode());
			   fWriter.close();
		       runCode(outPutDTO, filePath, fileName);
		       logger.info("outPutDTO.getRunTimeError() : {}",outPutDTO.getRunTimeError());
		       if(outPutDTO.getRunTimeError().length() > 0)
		          { 
		        	  output = outPutDTO.getRunTimeError();
		          }
		          else
		          {
		        	  logger.info("outPutDTO.getRunTimeOutput() : {}",outPutDTO.getRunTimeOutput());
		        	  if(outPutDTO.getRunTimeOutput().length() > 0)
			          {
			        	  output = outPutDTO.getRunTimeOutput();
			          }
		          }
		       		       
	        } catch (Exception e) {
	        	logger.error("Error while Executing : {}",codeDTO.toString());
	            logger.error("Exception caught in compileCode() : { }",e);
	            output = e.getMessage();
	        }
		    commonService.deleteFolder(filePath);
		    logger.info("return output");
		return output;
	}

	private void runCode(OutputDTO outPutDTO, String filePath, String fileName) throws Exception {
		
		logger.info("runCode() start ");
		CommandLineExecutor commandLineExecutor  = new CommandLineExecutor(filePath,"node "+fileName+".js");
		commandLineExecutor.run();
		Thread thread = new Thread(commandLineExecutor);
	    thread.start();
	    int maxRetry = 70;
	    int retryCount = 0;
	    while(commandLineExecutor.getStdOutErrorDTO() == null)
	    {
	    	retryCount = retryCount + 1;
	    	Thread.sleep(100);
	    	if(commandLineExecutor.getStdOutErrorDTO() == null && maxRetry < retryCount)
	    	{
	    		outPutDTO.setRunTimeError("Timeout");
	    		thread.interrupt();
	    		return;
	    	}
	    }
	    
		StdOutErrorDTO stdOutErrorDTO = commandLineExecutor.getStdOutErrorDTO();
		   outPutDTO.setRunTimeOutput(stdOutErrorDTO.getStdOut());
		   outPutDTO.setRunTimeError(stdOutErrorDTO.getStdError());
	   logger.info("runCode() end ");
	   
	}

}
