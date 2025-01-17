package cn.bootx.starter.code.gen.controller;

import cn.bootx.common.core.function.SystemKeyValueService;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.starter.code.gen.dto.CodeGenPreview;
import cn.bootx.starter.code.gen.param.CodeGenParam;
import cn.bootx.starter.code.gen.service.CodeGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码生成
 *
 * @author xxm
 * @date 2022/2/17
 */
@Tag(name = "代码生成")
@RestController
@RequestMapping("/gen/code")
@RequiredArgsConstructor
public class CodeGeneratorController {

    private final CodeGeneratorService generatorService;

    private final SystemKeyValueService systemKeyValueService;

    @Operation(summary = "预览生成代码")
    @PostMapping("/codeGenPreview")
    public ResResult<List<CodeGenPreview>> codeGenPreview(@RequestBody CodeGenParam param) {
        return Res.ok(generatorService.codeGenPreview(param));
    }

    @Operation(summary = "下载生成代码")
    @PostMapping("/genCodeZip")
    public ResponseEntity<byte[]> genCodeZip(@RequestBody CodeGenParam param) {
        return generatorService.genCodeZip(param);
    }

}
