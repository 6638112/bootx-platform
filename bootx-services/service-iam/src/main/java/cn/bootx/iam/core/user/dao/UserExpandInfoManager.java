package cn.bootx.iam.core.user.dao;

import cn.bootx.common.mybatisplus.impl.BaseManager;
import cn.bootx.iam.core.user.entity.UserExpandInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户扩展信息
 *
 * @author xxm
 * @date 2022/1/8
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserExpandInfoManager extends BaseManager<UserExpandInfoMapper, UserExpandInfo> {

}
