package com.sonycode.compiler.service.api;

import com.sonycode.compiler.dto.CodeDTO;

public interface JavaCompilerService {
	String compileCode(CodeDTO codeDTO);
}
