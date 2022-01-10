package cn.bootx.starter.quartz.core.dao;

import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.mybatisplus.impl.BaseManager;
import cn.bootx.common.mybatisplus.util.MpUtil;
import cn.bootx.starter.quartz.core.entity.QuartzJob;
import cn.bootx.starter.quartz.param.QuartzJobParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
* 定时任务
* @author xxm  
* @date 2021/11/2 
*/
@Slf4j
@Repository
@RequiredArgsConstructor
public class QuartzJobManager extends BaseManager<QuartzJobMapper, QuartzJob> {

    /**
     * 分页
     */
    public Page<QuartzJob> page(PageParam pageParam, QuartzJobParam param){
        Page<QuartzJob> mpPage = MpUtil.getMpPage(pageParam, QuartzJob.class);
        return lambdaQuery()
                .orderByDesc(QuartzJob::getId)
                .page(mpPage);
    }
}
