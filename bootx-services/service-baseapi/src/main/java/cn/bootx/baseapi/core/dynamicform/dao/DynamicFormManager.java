package cn.bootx.baseapi.core.dynamicform.dao;

import cn.bootx.baseapi.core.dynamicform.entity.DynamicForm;
import cn.bootx.baseapi.param.dynamicform.DynamicFormParam;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.mybatisplus.base.MpIdEntity;
import cn.bootx.common.mybatisplus.impl.BaseManager;
import cn.bootx.common.mybatisplus.util.MpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 动态表单
 *
 * @author xxm
 * @date 2022-07-28
 */
@Repository
@RequiredArgsConstructor
public class DynamicFormManager extends BaseManager<DynamicFormMapper, DynamicForm> {

    /**
     * 分页
     */
    public Page<DynamicForm> page(PageParam pageParam, DynamicFormParam param) {
        Page<DynamicForm> mpPage = MpUtil.getMpPage(pageParam, DynamicForm.class);
        return lambdaQuery().select(DynamicForm.class, MpUtil::excludeBigField)
            .like(Objects.nonNull(param.getCode()), DynamicForm::getCode, param.getCode())
            .like(Objects.nonNull(param.getName()), DynamicForm::getName, param.getName())
            .orderByDesc(MpIdEntity::getId)
            .page(mpPage);
    }

    public boolean existsByCode(String code) {
        return existedByField(DynamicForm::getCode, code);
    }

    public boolean existsByCode(String code, Long id) {
        return existedByField(DynamicForm::getCode, code, id);
    }

}