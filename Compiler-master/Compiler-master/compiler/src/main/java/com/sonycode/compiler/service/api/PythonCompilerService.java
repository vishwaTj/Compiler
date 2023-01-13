package com.sonycode.compiler.service.api;

import com.sonycode.compiler.dto.CodeDTO;

public interface PythonCompilerService {
	String compileCode(CodeDTO codeDTO);
}
