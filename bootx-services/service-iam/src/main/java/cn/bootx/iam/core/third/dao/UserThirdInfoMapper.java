package cn.bootx.iam.core.third.dao;

import cn.bootx.iam.core.third.entity.UserThirdInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户三方登录绑定详情
 *
 * @author xxm
 * @date 2022-07-02
 */
@Mapper
public interface UserThirdInfoMapper extends BaseMapper<UserThirdInfo> {

}