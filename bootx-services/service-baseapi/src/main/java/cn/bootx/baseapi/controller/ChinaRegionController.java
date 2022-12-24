package cn.bootx.baseapi.controller;

import cn.bootx.baseapi.core.region.service.ChinaRegionService;
import cn.bootx.baseapi.dto.region.RegionDto;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 中国行政区划
 * @author xxm
 * @date 2022/12/24
 */
@Tag(name = "中国行政区划")
@RestController
@RequestMapping("/china/region")
@RequiredArgsConstructor
public class ChinaRegionController {
    private final ChinaRegionService chinaRegionService;

    @Operation(summary = "获取一级行政区")
    @GetMapping("/findAllProvince")
    public ResResult<List<RegionDto>> findAllProvince(){
        return Res.ok(chinaRegionService.findAllProvince());
    }

    @Operation(summary = "根据区划级别和上级区划代码获取当前行政区划的列表")
    @GetMapping("/findAllRegionByPid")
    public ResResult<List<RegionDto>> findAllRegionByPid(Integer pid,Integer level){
        return Res.ok(chinaRegionService.findAllRegionByPid(pid,level));
    }

    @Operation(summary = "获取省市二级联动列表")
    @GetMapping("/findAllProvinceAndCity")
    public ResResult<List<RegionDto>> findAllProvinceAndCity(){
        return Res.ok(chinaRegionService.findAllProvinceAndCity());
    }

    @Operation(summary = " 获取省市区县三级联动列表")
    @GetMapping("/findAllProvinceAndCityAndArea")
    public ResResult<List<RegionDto>> findAllProvinceAndCityAndArea(){
        return Res.ok(chinaRegionService.findAllProvinceAndCityAndArea());
    }

}