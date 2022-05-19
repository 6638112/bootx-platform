package cn.bootx.iam.controller;

import cn.bootx.common.core.annotation.IgnoreAuth;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.iam.core.upms.service.RoleMenuService;
import cn.bootx.iam.dto.upms.MenuAndResourceDto;
import cn.bootx.iam.param.upms.RolePermissionParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**   
* 角色权限(菜单)关联关系
* @author xxm  
* @date 2021/7/12 
*/
@Tag(name ="角色菜单权限关系")
@RestController
@RequestMapping("/role/menu")
@RequiredArgsConstructor
public class RoleMenuController {
    private final RoleMenuService roleMenuService;

    @Operation(summary = "保存请求权限关系")
    @PostMapping("/save")
    public ResResult<Boolean> save(@RequestBody RolePermissionParam param){
        roleMenuService.save(param.getRoleId(),param.getPermissionIds());
        return Res.ok(true);
    }

    @Operation(summary = "获取权限菜单id列表,不包含资源权限")
    @GetMapping("/findMenuIds")
    public ResResult<List<Long>> findMenuIds(String clientCode){
        return Res.ok(roleMenuService.findMenuIds(clientCode));
    }

    @Operation(summary = "根据角色id获取关联权限id集合(包含资源和菜单)")
    @GetMapping("/findPermissionIdsByRole")
    public ResResult<List<Long>> findPermissionIdsByRole(Long roleId){
        return Res.ok(roleMenuService.findPermissionIdsByRole(roleId));
    }

    @IgnoreAuth
    @Operation(summary = "获取菜单和资源权限")
    @GetMapping("/getPermissions")
    public ResResult<MenuAndResourceDto> getPermissions(String clientCode){
        return Res.ok(roleMenuService.getPermissions(clientCode));
    }
}
