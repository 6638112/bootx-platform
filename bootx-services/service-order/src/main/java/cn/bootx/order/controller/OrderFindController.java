package cn.bootx.order.controller;

import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.order.core.order.service.OrderFindService;
import cn.bootx.order.dto.order.OrderDto;
import cn.hutool.core.date.DatePattern;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author xxm
* @date 2021/4/13
*/
@Tag(name ="订单查询")
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderFindController {
    private final OrderFindService orderFindService;


    @Operation(summary = "根据用户获取订单")
    @GetMapping("/findByUser")
    public ResResult<List<OrderDto>> findByUser(Long id){
        return Res.ok(orderFindService.findByUser(id));
    }

    @Operation(summary = "订单列表 分页")
    @GetMapping("/page")
    public ResResult<PageResult<OrderDto>> page(PageParam page){
        return Res.ok(orderFindService.page(page));
    }

    @Operation(summary = "获取完整订单详情")
    @GetMapping("/getWholeById")
    public ResResult<OrderDto> getWholeById(Long id) {
        return Res.ok(orderFindService.getWholeById(id));
    }

    @Operation(summary = "查询订单包含的skuIds")
    @GetMapping("/findOrderSkuIds")
    public ResResult<List<Long>> findOrderSkuIds(Long orderId){
        return Res.ok(orderFindService.findOrderSkuIds(orderId));
    }

    @Operation(summary = "获取指定类型超时订单的id集合")
    @GetMapping("/findPayTimeoutOrderIdsByType")
    public ResResult<List<Long>> findPayTimeoutOrderIdsByType(@DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN) LocalDateTime date, Integer type){
        return Res.ok(orderFindService.findPayTimeoutOrderIdsByType(date,type));
    }

}
