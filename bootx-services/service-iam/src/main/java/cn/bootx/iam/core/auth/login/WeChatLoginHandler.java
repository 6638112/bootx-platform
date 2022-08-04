package cn.bootx.iam.core.auth.login;

import cn.bootx.iam.core.third.dao.UserThirdManager;
import cn.bootx.iam.core.third.entity.UserThird;
import cn.bootx.iam.core.third.service.UserTiredOperateService;
import cn.bootx.iam.core.user.dao.UserInfoManager;
import cn.bootx.iam.core.user.entity.UserInfo;
import cn.bootx.starter.auth.authentication.OpenIdAuthentication;
import cn.bootx.starter.auth.code.AuthLoginTypeCode;
import cn.bootx.starter.auth.configuration.AuthProperties;
import cn.bootx.starter.auth.entity.AuthInfoResult;
import cn.bootx.starter.auth.entity.LoginAuthContext;
import cn.bootx.starter.auth.exception.LoginFailureException;
import cn.bootx.starter.auth.util.SecurityUtil;
import cn.bootx.starter.wechat.core.login.service.WeChatQrLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static cn.bootx.starter.auth.code.AuthLoginTypeCode.*;

/**
* 微信登录(公众号)
* @author xxm
* @date 2021/8/2
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class WeChatLoginHandler implements OpenIdAuthentication {
    private final UserTiredOperateService userTiredOperateService;
    private final WeChatQrLoginService weChatQrLoginService;

    private final UserThirdManager userThirdManager;
    private final UserInfoManager userInfoManager;
    private final AuthProperties authProperties;

    @Override
    public String getLoginType() {
        return AuthLoginTypeCode.WE_CHAT;
    }

    /**
     * 认证
     */
    @Override
    public AuthInfoResult attemptAuthentication(LoginAuthContext context) {
        String authCode = context.getRequest().getParameter(AUTH_CODE);
        AuthUser authUser = this.getAuthUser(authCode, null);

        // 获取微信公众号关联的用户id
        UserThird userThird = userThirdManager.findByField(UserThird::getWeChatId,authUser.getUuid())
                .orElseThrow(() -> new LoginFailureException("微信没有找到绑定的用户"));

        // 获取用户信息
        UserInfo userInfo = userInfoManager.findById(userThird.getUserId())
                .orElseThrow(() -> new LoginFailureException("用户不存在"));

        return new AuthInfoResult()
                .setUserDetail(userInfo.toUserDetail())
                .setId(userInfo.getId());
    }

    /**
     * 获取关联的的第三方平台用户信息
     * @param authCode key 值, 可以用来拿用户信息
     */
    @Override
    public AuthUser getAuthUser(String authCode, String state){
        // 根据微信二维码的值获取关联扫码的微信信息
        String uuid = weChatQrLoginService.getStatus(authCode);
        AuthUser authUser = new AuthUser();
        authUser.setNickname("未知");
        authUser.setUsername("未知");
        authUser.setAvatar("未知");
        authUser.setUuid(uuid);
        return authUser;
    }
    /**
     * 绑定用户
     */
    @Override
    public void bindUser(String authCode, String state){
        Long userId = SecurityUtil.getUserId();
        AuthUser authUser = this.getAuthUser(authCode, state);
        userTiredOperateService.checkOpenIdBind(authUser.getUuid(), UserThird::getWeChatId);
        userTiredOperateService.bindOpenId(userId,authUser.getUuid(), UserThird::setWeChatId);
        userTiredOperateService.bindOpenInfo(userId,authUser,WE_CHAT);
    }

    /**
     * 获取微信认证请求
     */
    private AuthWeChatOpenRequest getAuthRequest(){
        val thirdLogin = authProperties.getThirdLogin().getWeCom();
        if (Objects.isNull(thirdLogin)){
            throw new LoginFailureException("钉钉开放登录配置有误");
        }
        return new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(thirdLogin.getClientId())
                .clientSecret(thirdLogin.getClientSecret())
                .redirectUri(thirdLogin.getRedirectUri())
                .build());
    }
}
