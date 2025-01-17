package cn.bootx.common.core.function;

import cn.bootx.common.core.entity.UserDetail;

import java.util.Optional;

/**
 * 获取用户
 *
 * @author xxm
 * @date 2022/8/28
 */
public interface UserDetailService {

    /**
     * 获取用户信息
     */
    Optional<UserDetail> findByUserId(Long userId);

}
