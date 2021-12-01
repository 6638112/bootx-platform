package cn.bootx.starter.audit.log.controller;

import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.starter.audit.log.core.service.OperateLogService;
import cn.bootx.starter.audit.log.dto.OperateLogDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 操作日志
* @author xxm
* @date 2021/9/8
*/
@Tag(name ="操作日志")
@RestController
@RequestMapping("/log/operate")
@RequiredArgsConstructor
public class OperateLogController {
    private final OperateLogService operateLogService;

    @Operation(summary = "分页")
    @GetMapping("/page")
    public ResResult<PageResult<OperateLogDto>> page(PageParam pageParam, OperateLogDto operateLogDto){
        return Res.ok(operateLogService.page(pageParam,operateLogDto));
    }

    @Operation(summary = "获取")
    @GetMapping("/findById")
    public ResResult<OperateLogDto> findById(Long id){
        return Res.ok(operateLogService.findById(id));
    }
}
