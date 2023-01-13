package com.sonycode.compiler.service.domain;

import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sonycode.compiler.dto.CodeDTO;
import com.sonycode.compiler.dto.OutputDTO;
import com.sonycode.compiler.dto.StdOutErrorDTO;
import com.sonycode.compiler.service.api.CCompilerService;
import com.sonycode.compiler.service.api.CommonService;

@Component
public class CCompilerServiceImpl implements CCompilerService {

	 Logger logger = LoggerFactory.getLogger(CCompilerService.class);
		
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
				   filePath = applicationPath + "/src/main/resources/CodeFiles/CFile/"+ randomFolderName + "/";	   
				   commonService.createFolder(filePath);
				   logger.info("FilePath : {}",filePath);
				   String fileName = "main";
				   FileWriter fWriter = new FileWriter(filePath + fileName + ".c");
				   fWriter.write(codeDTO.getCode());
				   fWriter.close();
			       compileCode(filePath,fileName,outPutDTO);
			       logger.info("After compiler code: {}" );
			       
			       if(outPutDTO.getCompileTimeError().length()  == 0)
			       {   
			    	  fileName = "a"; 
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

		private void runCode(OutputDTO outPutDTO, String filePath, String fileName) throws Exception {
			logger.info("runCode() start ");
					
			CommandLineExecutor commandLineExecutor  = new CommandLineExecutor(filePath,"./"+fileName+".out");
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

	    
		private void compileCode(String filePath,String fileName,OutputDTO outPutDTO) throws Exception
		  {
			logger.info("compileCode() start ");
			CommandLineExecutor commandLineExecutor = new CommandLineExecutor(filePath,"gcc "+fileName+ ".c");
			commandLineExecutor.run();
			StdOutErrorDTO stdOutErrorDTO = commandLineExecutor.getStdOutErrorDTO();
			outPutDTO.setCompileTimeOutput(stdOutErrorDTO.getStdOut());
			outPutDTO.setCompileTimeError(stdOutErrorDTO.getStdError());
			logger.info("compileCode() end ");
		  }
	
}
