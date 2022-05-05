package cn.bootx.starter.auth.authentication;

import cn.bootx.common.core.entity.UserDetail;
import cn.bootx.starter.auth.config.AuthProperties;
import cn.bootx.starter.auth.entity.LoginAuthContext;
import cn.bootx.starter.auth.entity.AuthInfoResult;
import cn.bootx.starter.auth.exception.LoginFailureException;

import javax.validation.constraints.NotNull;

/**
 * 抽象认证器
 * @author xxm
 * @date 2021/7/30
 */
public interface AbstractAuthentication {

    /**
     * 认证前操作
     */
    default void authenticationBefore(LoginAuthContext context) {

    }

    /**
     * 尝试认证, 必须重写
     */
    @NotNull AuthInfoResult attemptAuthentication(LoginAuthContext context);

    /**
     * 认证后处理
     */
    default void authenticationAfter(AuthInfoResult authInfoResult,LoginAuthContext context){

    }

    /**
     * 认证流程
     */
    default AuthInfoResult authentication(LoginAuthContext context){
        this.authenticationBefore(context);
        AuthInfoResult authInfoResult = this.attemptAuthentication(context);

        // 添加用户信息到上下文中
        UserDetail userDetail = authInfoResult.getUserDetail();
        context.setUserDetail(userDetail);

        // 判断是否开启了超级管理员
        AuthProperties authProperties = context.getAuthProperties();
        if (!authProperties.isEnableAdmin()&&userDetail.isAdmin()){
            throw new LoginFailureException("未开启超级管理员权限");
        }

        authenticationAfter(authInfoResult,context);
        return authInfoResult;
    }

}
