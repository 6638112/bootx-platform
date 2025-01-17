package cn.bootx.iam.core.user.service;

import cn.bootx.baseapi.core.captcha.service.CaptchaService;
import cn.bootx.common.core.exception.BizException;
import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.mybatisplus.base.MpIdEntity;
import cn.bootx.common.mybatisplus.util.MpUtil;
import cn.bootx.iam.code.UserStatusCode;
import cn.bootx.iam.core.client.dao.ClientManager;
import cn.bootx.iam.core.upms.service.UserRoleService;
import cn.bootx.iam.core.user.dao.UserExpandInfoManager;
import cn.bootx.iam.core.user.dao.UserInfoManager;
import cn.bootx.iam.core.user.entity.UserExpandInfo;
import cn.bootx.iam.core.user.entity.UserInfo;
import cn.bootx.iam.core.user.event.UserCreateEvent;
import cn.bootx.iam.dto.dept.DeptDto;
import cn.bootx.iam.dto.role.RoleDto;
import cn.bootx.iam.dto.user.UserInfoDto;
import cn.bootx.iam.dto.user.UserInfoWhole;
import cn.bootx.iam.exception.user.UserInfoNotExistsException;
import cn.bootx.iam.param.user.UserInfoParam;
import cn.bootx.iam.param.user.UserRegisterParam;
import cn.bootx.starter.auth.util.PasswordEncoder;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 超级管理员操作类
 *
 * @author xxm
 * @date 2021/9/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserInfoManager userInfoManager;

    private final UserExpandInfoManager userExpandInfoManager;

    private final UserRoleService userRoleService;

    private final UserDeptService userDeptService;

    private final PasswordEncoder passwordEncoder;

    private final UserQueryService userQueryService;

    private final CaptchaService captchaService;

    private final ClientManager clientManager;

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 分页查询
     */
    public PageResult<UserInfoDto> page(PageParam pageParam, UserInfoParam userInfoParam) {
        return MpUtil.convert2DtoPageResult(userInfoManager.page(pageParam, userInfoParam));
    }

    /**
     * 锁定用户
     */
    public void lock(Long userId) {
        userInfoManager.setUpStatus(userId, UserStatusCode.BAN);
    }

    /**
     * 批量锁定用户
     */
    public void lockBatch(List<Long> userIds) {
        userInfoManager.setUpStatusBatch(userIds, UserStatusCode.BAN);
    }

    /**
     * 解锁用户
     */
    public void unlock(Long userId) {
        userInfoManager.setUpStatus(userId, UserStatusCode.NORMAL);
    }

    /**
     * 批量解锁用户
     */
    public void unlockBatch(List<Long> userIds) {
        userInfoManager.setUpStatusBatch(userIds, UserStatusCode.NORMAL);
    }

    /**
     * 注册新用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterParam param) {
        // 验证
        if (!captchaService.validateImgCaptcha(param.getCaptchaKey(), param.getCaptcha())) {
            throw new BizException("验证码错误");
        }
        UserInfoParam userInfoParam = new UserInfoParam();
        BeanUtil.copyProperties(param, userInfoParam);
        userInfoParam.setName(param.getUsername());
        // TODO 默认注册就有所有终端的权限, 后期优化
        List<String> ids = clientManager.findAll()
            .stream()
            .map(MpIdEntity::getId)
            .map(String::valueOf)
            .collect(Collectors.toList());
        userInfoParam.setClientIdList(ids);
        this.add(userInfoParam);
    }

    /**
     * 添加新用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(UserInfoParam userInfoParam) {
        if (userQueryService.existsUsername(userInfoParam.getUsername())) {
            throw new BizException("账号已存在");
        }
        if (userQueryService.existsEmail(userInfoParam.getEmail())) {
            throw new BizException("邮箱已存在");
        }
        if (userQueryService.existsPhone(userInfoParam.getPhone())) {
            throw new BizException("手机号已存在");
        }
        // 注册时间
        UserInfo userInfo = UserInfo.init(userInfoParam);
        userInfo.setAdmin(false)
            .setStatus(UserStatusCode.NORMAL)
            .setPassword(passwordEncoder.encode(userInfo.getPassword()))
            .setRegisterTime(LocalDateTime.now());
        userInfoManager.save(userInfo);
        // 扩展信息
        UserExpandInfo userExpandInfo = new UserExpandInfo();
        userExpandInfo.setInitialPassword(true).setId(userInfo.getId());
        userExpandInfoManager.save(userExpandInfo);
        // 发送用户注册事件
        eventPublisher.publishEvent(new UserCreateEvent(this, userInfo.toDto()));

    }

    /**
     * 重置密码
     */
    public void restartPassword(Long userId, @NotBlank(message = "新密码不能为空") String newPassword) {

        UserInfo userInfo = userInfoManager.findById(userId).orElseThrow(UserInfoNotExistsException::new);
        // 新密码进行加密
        newPassword = passwordEncoder.encode(newPassword);
        userInfo.setPassword(newPassword);
        userInfoManager.updateById(userInfo);
    }

    /**
     * 编辑用户信息
     */
    public UserInfoDto update(UserInfoParam userInfoParam) {
        UserInfo userInfo = userInfoManager.findById(userInfoParam.getId())
            .orElseThrow(UserInfoNotExistsException::new);
        userInfoParam.setPassword(null);
        BeanUtil.copyProperties(userInfoParam, userInfo, CopyOptions.create().ignoreNullValue());
        if (CollUtil.isNotEmpty(userInfoParam.getClientIdList())) {
            userInfo.setClientIds(String.join(",", userInfoParam.getClientIdList()));
        }
        else {
            userInfo.setClientIds("");
        }
        return userInfoManager.updateById(userInfo).toDto();
    }

    /**
     * 获取用户详情
     */
    public UserInfoWhole getUserInfoWhole(Long userId) {
        // 用户信息
        UserInfo userInfo = userInfoManager.findById(userId).orElseThrow(UserInfoNotExistsException::new);
        UserExpandInfo userExpandInfo = userExpandInfoManager.findById(userId)
            .orElseThrow(UserInfoNotExistsException::new);
        // 角色信息
        List<RoleDto> rolesByUser = userRoleService.findRolesByUser(userId);
        // 部门组织
        List<DeptDto> deptListByUser = userDeptService.findDeptListByUser(userId);
        return new UserInfoWhole().setUserInfo(userInfo.toDto())
            .setUserExpandInfo(userExpandInfo.toDto())
            .setRoles(rolesByUser)
            .setDeptList(deptListByUser);
    }

}
