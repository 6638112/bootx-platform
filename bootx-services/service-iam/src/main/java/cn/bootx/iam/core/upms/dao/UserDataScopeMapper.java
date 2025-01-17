package cn.bootx.iam.core.upms.dao;

import cn.bootx.iam.core.upms.entity.UserDataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xxm
 * @date 2021/12/23
 */
@Mapper
public interface UserDataScopeMapper extends BaseMapper<UserDataScope> {

    void saveAll(@Param("userDataScopes") List<UserDataScope> userDataScopes);

}
