package cn.bootx.starter.audit.log.core.db.service;

import cn.bootx.common.core.entity.UserDetail;
import cn.bootx.common.core.exception.DataNotExistException;
import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.jackson.util.JacksonUtil;
import cn.bootx.common.mybatisplus.util.MpUtil;
import cn.bootx.starter.audit.log.core.db.dao.DataVersionLogDbManager;
import cn.bootx.starter.audit.log.core.db.entity.DataVersionLogDb;
import cn.bootx.starter.audit.log.dto.DataVersionLogDto;
import cn.bootx.starter.audit.log.param.DataVersionLogParam;
import cn.bootx.starter.audit.log.service.DataVersionLogService;
import cn.bootx.starter.auth.util.SecurityUtil;
import cn.hutool.core.util.DesensitizedUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
* 数据版本日志数据库实现
* @author xxm
* @date 2022/1/10
*/
@Slf4j
@Service
@ConditionalOnProperty(prefix = "bootx.starter.audit-log", value = "store", havingValue = "jdbc",matchIfMissing = true)
@RequiredArgsConstructor
public class DataVersionLogDbService implements DataVersionLogService {
    private final DataVersionLogDbManager manager;

    /**
     * 添加
     */
    @Override
    @Transactional
    public void add(DataVersionLogParam param) {
        int maxVersion = manager.getMaxVersion(param.getDataName(), param.getDataId());
        DataVersionLogDb dataVersionLog = new DataVersionLogDb()
                .setDataName(param.getDataName())
                .setDataId(param.getDataId())
                .setDataContent(JacksonUtil.toJson(param))
                .setCreator(SecurityUtil.getCurrentUser().map(UserDetail::getId).orElse(DesensitizedUtil.userId()))
                .setCreateTime(LocalDateTime.now())
                .setVersion(maxVersion+1);
        manager.save(dataVersionLog);
    }

    /**
     * 获取
     */
    @Override
    public DataVersionLogDto findById(Long id) {
        return manager.findById(id).map(DataVersionLogDb::toDto).orElseThrow(DataNotExistException::new);
    }

    /**
     * 分页
     */
    @Override
    public PageResult<DataVersionLogDto> page(PageParam pageParam, DataVersionLogParam param) {
        return MpUtil.convert2PageResult(manager.page(pageParam,param));
    }

    /**
     * 删除
     */
    @Override
    public void delete(Long id) {

    }
}
