package cn.bootx.baseapi.core.config.dao;

import cn.bootx.baseapi.core.config.entity.SystemParameter;
import cn.bootx.baseapi.param.system.SystemParameterParam;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.mybatisplus.base.MpBaseEntity;
import cn.bootx.common.mybatisplus.impl.BaseManager;
import cn.bootx.common.mybatisplus.util.MpUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**   
* 系统参数
* @author xxm  
* @date 2021/10/25 
*/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SystemParamManager extends BaseManager<SystemParamMapper, SystemParameter> {

    /**
     * key重复检查
     */
    public boolean existedByKey(String key){
        return this.existedByField(SystemParameter::getParamKey,key);
    }
    /**
     * key重复检查
     */
    public boolean existedByKey(String key,Long id){
        return lambdaQuery().eq(SystemParameter::getParamKey,key)
                .ne(MpBaseEntity::getId,id)
                .exists();
    }

    /**
     * 分页
     */
    public Page<SystemParameter> page(PageParam pageParam, SystemParameterParam param){
        Page<SystemParameter> mpPage = MpUtils.getMpPage(pageParam, SystemParameter.class);
        return lambdaQuery()
                .orderByDesc(MpBaseEntity::getCreateTime)
                .page(mpPage);
    }
}