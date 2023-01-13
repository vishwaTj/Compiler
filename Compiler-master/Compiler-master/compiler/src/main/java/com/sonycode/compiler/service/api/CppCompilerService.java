package com.sonycode.compiler.service.api;

import com.sonycode.compiler.dto.CodeDTO;

public interface CppCompilerService {
	String compileCode(CodeDTO codeDTO);
}
