package cn.bootx.starter.audit.log.handler;

import cn.bootx.common.core.annotation.OperateLog;
import cn.bootx.common.core.annotation.OperateLogs;
import cn.bootx.common.core.code.ServletCode;
import cn.bootx.common.core.entity.UserDetail;
import cn.bootx.common.headerholder.HeaderHolder;
import cn.bootx.common.jackson.util.JacksonUtil;
import cn.bootx.common.spring.util.AopUtil;
import cn.bootx.common.spring.util.WebServletUtil;
import cn.bootx.starter.audit.log.ip2region.IpToRegionService;
import cn.bootx.starter.audit.log.param.OperateLogParam;
import cn.bootx.starter.audit.log.service.OperateLogService;
import cn.bootx.starter.auth.util.SecurityUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 操作日志切面处理
 *
 * @author xxm
 * @date 2021/8/13
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperateLogAspectHandler {

    private final OperateLogService operateLogService;
    private final IpToRegionService ipToRegionService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(cn.bootx.common.core.annotation.OperateLog) || @annotation(cn.bootx.common.core.annotation.OperateLogs)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "o")
    public void doAfterReturning(JoinPoint joinPoint, Object o) {
        handleLog(joinPoint, null, o);
    }

    /**
     * 拦截异常操作
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    /**
     * 操作log处理
     */
    protected void handleLog(JoinPoint joinPoint, Exception e, Object o) {
        List<OperateLog> logs = getMethodAnnotation(joinPoint);
        if (CollUtil.isEmpty(logs)) {
            return;
        }
        // ip信息
        String ip = "未知";
        String location = "未知";
        Optional<String> ipOpt = Optional.ofNullable(WebServletUtil.getRequest()).map(ServletUtil::getClientIP);
        if (ipOpt.isPresent()){
            ip = ipOpt.get();
            location = ipToRegionService.getRegionStrByIp(ip);
        }
        Optional<UserDetail> currentUser = SecurityUtil.getCurrentUser();
        // 设置方法名称
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        for (OperateLog log : logs) {
            OperateLogParam operateLog = new OperateLogParam().setTitle(log.title())
                    .setOperateId(currentUser.map(UserDetail::getId).orElse(DesensitizedUtil.userId()))
                    .setUsername(currentUser.map(UserDetail::getUsername).orElse("未知"))
                    .setBusinessType(log.businessType().name().toLowerCase(Locale.ROOT))
                    .setOperateUrl(HeaderHolder.getHeader(ServletCode.REQUEST_URI))
                    .setMethod(className + "#" + methodName)
                    .setRequestMethod(HeaderHolder.getHeader(ServletCode.METHOD))
                    .setSuccess(true)
                    .setOperateIp(ip)
                    .setOperateLocation(location)
                    .setOperateTime(LocalDateTime.now());

            // 异常流
            if (Objects.nonNull(e)) {
                operateLog.setSuccess(false).setErrorMsg(e.getMessage());
            }

            // 参数
            if (log.saveParam()) {
                Object[] args = joinPoint.getArgs();
                operateLog.setOperateParam(JacksonUtil.toJson(args));
            }

            // 返回值
            if (log.saverReturn()) {
                operateLog.setOperateReturn(JacksonUtil.toJson(o));
            }

            operateLogService.add(operateLog);
        }
    }

    /**
     * 获取注解
     */
    private List<OperateLog> getMethodAnnotation(JoinPoint joinPoint) {
        List<OperateLog> operateLogs = Optional.ofNullable(AopUtil.getMethodAnnotation(joinPoint, OperateLogs.class))
                .map(OperateLogs::value)
                .map(ListUtil::of)
                .orElse(null);
        if (CollUtil.isEmpty(operateLogs)) {
            operateLogs = ListUtil.of(AopUtil.getMethodAnnotation(joinPoint, OperateLog.class));
        }
        return operateLogs;
    }

}
