package com.sonycode.compiler.service.api;

import com.sonycode.compiler.dto.CodeDTO;

public interface JavaScriptCompilerService {
	String compileCode(CodeDTO codeDTO);
}
