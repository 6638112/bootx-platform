package cn.bootx.baseapi.core.dynamicform.dao;

import cn.bootx.baseapi.core.dynamicform.entity.DynamicForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态表单
 *
 * @author xxm
 * @date 2022-07-28
 */
@Mapper
public interface DynamicFormMapper extends BaseMapper<DynamicForm> {

}