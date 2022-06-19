package cn.bootx.iam.controller;

import cn.bootx.common.core.annotation.IgnoreAuth;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.core.util.ValidationUtil;
import cn.bootx.iam.core.user.service.UserInfoService;
import cn.bootx.iam.core.user.service.UserQueryService;
import cn.bootx.iam.dto.user.LoginAfterUserInfo;
import cn.bootx.iam.dto.user.UserBaseInfoDto;
import cn.bootx.iam.dto.user.UserInfoDto;
import cn.bootx.iam.param.user.UserBaseInfoParam;
import cn.bootx.iam.param.user.UserChangeEmailParam;
import cn.bootx.iam.param.user.UserChangePhoneParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
* @author xxm
* @date 2020/4/25 20:02
*/
@Tag(name ="用户管理")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserInfoController {
	private final UserInfoService userInfoService;
    private final UserQueryService userQueryService;

    @Operation( summary = "账号是否被使用")
    @GetMapping("/existsUsername")
    public ResResult<Boolean> existsUsername(String username) {
        return Res.ok(userQueryService.existsUsername(username));
    }

    @Operation( summary = "账号是否被使用(不包含自己)")
    @GetMapping("/existsUsernameNotId")
    public ResResult<Boolean> existsUsername(String username,Long id) {
        return Res.ok(userQueryService.existsUsername(username,id));
    }

    @Operation( summary = "手机号是否被使用")
    @GetMapping("/existsPhone")
    public ResResult<Boolean> existsPhone(String phone) {
        return Res.ok(userQueryService.existsPhone(phone));
    }

    @Operation( summary = "手机号是否被使用(不包含自己)")
    @GetMapping("/existsPhoneNotId")
    public ResResult<Boolean> existsPhone(String phone,Long id) {
        return Res.ok(userQueryService.existsPhone(phone,id));
    }

    @Operation( summary = "邮箱是否被使用")
    @GetMapping("/existsEmail")
    public ResResult<Boolean> existsEmail(String email) {
        return Res.ok(userQueryService.existsEmail(email));
    }

    @Operation( summary = "邮箱是否被使用(不包含自己)")
    @GetMapping("/existsEmailNotId")
    public ResResult<Boolean> existsEmail(String email,Long id) {
        return Res.ok(userQueryService.existsEmail(email,id));
    }

    @Operation(summary = "修改密码")
    @PostMapping("/updatePassword")
    public ResResult<Void> updatePassword(@NotBlank(message = "旧密码不能为空") String password,
                                              @NotBlank(message = "新密码不能为空") String newPassword){
        userInfoService.updatePassword(password,newPassword);
        return Res.ok();
    }

    @Operation(summary = "修改手机号")
    @PostMapping("/updatePhone")
    public ResResult<Void> updatePhone(@RequestBody UserChangePhoneParam param){
        ValidationUtil.validateParam(param);
        userInfoService.updatePhone(param.getPhone(),param.getOldCaptcha(),param.getNewCaptcha());
        return Res.ok();
    }

    @Operation(summary = "修改手机号")
    @PostMapping("/updateEmail")
    public ResResult<Void> updateEmail(@RequestBody UserChangeEmailParam param){
        ValidationUtil.validateParam(param);
        userInfoService.updateEmail(param.getEmail(),param.getOldCaptcha(),param.getNewCaptcha());
        return Res.ok();
    }

    @Operation(summary = "查询用户安全信息")
    @GetMapping("/getUserSecurityInfo")
    public ResResult<UserInfoDto> getUserSecurityInfo(){
        return Res.ok(userInfoService.getUserSecurityInfo());
    }

    @Operation(summary = "查询用户基础信息")
    @GetMapping("/getUserBaseInfo")
    public ResResult<UserBaseInfoDto> getUserBaseInfo(){
        return Res.ok(userInfoService.getUserBaseInfo());
    }

    @Operation(summary = "修改用户基础信息")
    @PostMapping("/updateBaseInfo")
    public ResResult<Void> updateBaseInfo(@RequestBody UserBaseInfoParam param){
        userInfoService.updateUserBaseInfo(param);
        return Res.ok();
    }

    @IgnoreAuth
    @Operation(summary = "登录后获取用户信息")
    @GetMapping("/getLoginAfterUserInfo")
    public ResResult<LoginAfterUserInfo> getLoginAfterUserInfo(){
        return Res.ok(userInfoService.getLoginAfterUserInfo());
    }

}
