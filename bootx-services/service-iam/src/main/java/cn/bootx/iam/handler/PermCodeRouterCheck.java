package cn.bootx.iam.handler;

import cn.bootx.common.core.annotation.PermCode;
import cn.bootx.common.core.entity.UserDetail;
import cn.bootx.common.core.util.CollUtil;
import cn.bootx.iam.core.upms.service.RolePermService;
import cn.bootx.starter.auth.authentication.RouterCheck;
import cn.bootx.starter.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 权限码方式请求路径拦截
 *
 * @author xxm
 * @date 2023/1/22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PermCodeRouterCheck implements RouterCheck {

    private final RolePermService rolePermService;

    /**
     * 路由检查
     */
    @Override
    public boolean check(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // controller上是否加了权限码鉴权注解
            PermCode permCode = handlerMethod.getBeanType().getAnnotation(PermCode.class);
            if (Objects.isNull(permCode)) {
                // 方法上上是否加了跳过鉴权注解
                permCode = handlerMethod.getMethodAnnotation(PermCode.class);
            }
            else {
                // controller和方法上都加了跳过鉴权注解,以方法上为准
                PermCode annotation = handlerMethod.getMethodAnnotation(PermCode.class);
                if (Objects.nonNull(annotation)) {
                    permCode = annotation;
                }
            }
            return this.ignoreAuth(permCode);
        }
        return false;
    }

    /**
     * 权限码鉴权注解处理
     */
    private boolean ignoreAuth(PermCode permCode) {
        if (Objects.isNull(permCode)) {
            return false;
        }
        List<String> permCodes = CollUtil.newArrayList(permCode.value());
        if (CollUtil.isEmpty(permCodes)) {
            return false;
        }
        Optional<UserDetail> UserDetailOpt = SecurityUtil.getCurrentUser();
        if (!UserDetailOpt.isPresent()) {
            return false;
        }
        List<String> userPermCodes = rolePermService.findEffectPermCodesByUserId(UserDetailOpt.get().getId());
        return userPermCodes.stream().anyMatch(permCodes::contains);
    }

}
