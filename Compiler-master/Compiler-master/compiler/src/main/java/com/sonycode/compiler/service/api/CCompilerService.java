package com.sonycode.compiler.service.api;

import com.sonycode.compiler.dto.CodeDTO;

public interface CCompilerService {
	String compileCode(CodeDTO codeDTO);
}
