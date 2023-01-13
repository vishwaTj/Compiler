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
import com.sonycode.compiler.service.api.JavaCompilerService;

@Component
public class JavaCompilerServiceImpl  implements JavaCompilerService{
	
	Logger logger = LoggerFactory.getLogger(JavaCompilerService.class);
	
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
			   filePath = applicationPath + "/src/main/resources/CodeFiles/JavaFile/"+ randomFolderName + "/";	   
			   commonService.createFolder(filePath);
			   logger.info("FilePath : {}",filePath);
			   String fileName = getMainClassName(codeDTO.getCode());
			   FileWriter fWriter = new FileWriter(filePath + fileName + ".java");
			   fWriter.write(codeDTO.getCode());
			   fWriter.close();
		       String jdkBinPath = applicationPath + "/src/main/resources/Libraries/Java/jdk1.8.0_301/bin/";
		       compileCode(filePath,fileName,jdkBinPath,outPutDTO);
		       logger.info("After compiler code: {}" );
		       
		       if(outPutDTO.getCompileTimeError().length() == 0)
		       {   
		          runCode(outPutDTO, filePath, fileName,jdkBinPath);
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
		       }
		       else
		       {
		    	   output =  outPutDTO.getCompileTimeError();
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

	private void runCode(OutputDTO outPutDTO, String filePath, String fileName,String jdkBinPath) throws Exception {
		logger.info("runCode() start ");
				
		CommandLineExecutor commandLineExecutor  = new CommandLineExecutor(filePath,jdkBinPath+"java "+fileName);
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

    
	private void compileCode(String filePath,String fileName,String jdkBinPath,OutputDTO outPutDTO) throws Exception
	  {
		logger.info("compileCode() start ");
		CommandLineExecutor commandLineExecutor = new CommandLineExecutor(filePath,jdkBinPath+"javac "+fileName+ ".java");
		commandLineExecutor.run();
		StdOutErrorDTO stdOutErrorDTO = commandLineExecutor.getStdOutErrorDTO();
		outPutDTO.setCompileTimeOutput(stdOutErrorDTO.getStdOut());
		outPutDTO.setCompileTimeError(stdOutErrorDTO.getStdError());
		logger.info("compileCode() end ");
	  }
	
	private String getMainClassName(String code) throws Exception
	{
		String[] codeArray = code.trim().split(" ",5);
		if(!codeArray[0].equals("public"))
	    	{
			  throw new Exception("'public' Key Word Not Found before Main Class"); 
	    	}
		if(!codeArray[1].equals("class"))
	     	{
			  throw new Exception("'class' Key Word Not Found before Main Class");  
	     	}
		if(!(String.valueOf(codeArray[3].charAt(0)).equals("{") || codeArray[2].contains("{")))
		   {
			 throw new Exception("'{' Not Found after Main Class");  
		   }
		
		return codeArray[2];
	}
	
}
	
	
	

