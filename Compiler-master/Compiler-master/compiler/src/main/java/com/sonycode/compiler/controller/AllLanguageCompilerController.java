package com.sonycode.compiler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sonycode.compiler.constants.Language;
import com.sonycode.compiler.dto.CodeDTO;
import com.sonycode.compiler.service.api.CCompilerService;
import com.sonycode.compiler.service.api.ChashCompilerService;
import com.sonycode.compiler.service.api.CppCompilerService;
import com.sonycode.compiler.service.api.JavaCompilerService;
import com.sonycode.compiler.service.api.JavaScriptCompilerService;
import com.sonycode.compiler.service.api.PythonCompilerService;


@RestController
public class AllLanguageCompilerController {
	Logger logger = LoggerFactory.getLogger(AllLanguageCompilerController.class);
	
	@Autowired 
	JavaCompilerService javaCompilerService;
	
	@Autowired
	CppCompilerService cppCompilerService;
	
	@Autowired
	PythonCompilerService pythonCompilerService;
	
	@Autowired
	JavaScriptCompilerService javaScriptCompilerService;
	
	@Autowired
	CCompilerService cCompilerService; 
	
	@Autowired 
	ChashCompilerService chashCompilerService;
	
	
	
	@CrossOrigin(origins = "https://sonycode.com")
	@PostMapping("/compileCode")
	public  ResponseEntity<String>  compileCodeController(@RequestBody CodeDTO codeDTO)
	{
		logger.info(">> compileCodeController");
		logger.info("-- compileCodeController() CodeDTO : {}", codeDTO.toString());
		String output = new String();
		if(codeDTO.getLanguage().equals(Language.CPP.getName()))
			output = cppCompilerService.compileCode(codeDTO);
		else if(codeDTO.getLanguage().equals(Language.PYTHON.getName()))
			output = pythonCompilerService.compileCode(codeDTO);
		else if(codeDTO.getLanguage().equals(Language.JAVASCRIPT.getName()))
			output = javaScriptCompilerService.compileCode(codeDTO);
		else if(codeDTO.getLanguage().equals(Language.C.getName()))
			output = cCompilerService.compileCode(codeDTO);
		else if(codeDTO.getLanguage().equals(Language.CHASH.getName()))
			output = chashCompilerService.compileCode(codeDTO);
		else
		    output = javaCompilerService.compileCode(codeDTO);
		logger.info("<< compileCodeController");
		return ResponseEntity.ok()
		        .body(output);
	}
	
	@GetMapping("/testGet")
	public ResponseEntity<String> testGet()
	{
		return ResponseEntity.ok()
		        .header("Access-Control-Allow-Origin", "*")
		        .body("Custom header set testGet");
	}
	
	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@RequestMapping(value = "/testCompileCode",method = RequestMethod.POST)
	public ResponseEntity<String> testCompileCodeController(@RequestBody CodeDTO codeDTO)
	{
		logger.info(">> ResponseEntity compileCodeController");
		logger.info("<< ResponseEntity  CodeDTO compileCodeController : {}",codeDTO.toString());
		return ResponseEntity.ok()
		        .body("CodeDTO compileCodeController");
	}
	
	
}
