package cn.bootx.starter.flowable.core.instance.service;

import cn.bootx.common.core.exception.DataNotExistException;
import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.starter.auth.util.SecurityUtil;
import cn.bootx.starter.flowable.core.instance.convert.BpmActivityConvert;
import cn.bootx.starter.flowable.core.instance.dao.BpmInstanceManager;
import cn.bootx.starter.flowable.core.instance.entity.BpmInstance;
import cn.bootx.starter.flowable.dto.instance.ActivityInstanceChart;
import cn.bootx.starter.flowable.dto.instance.BpmInstanceDto;
import cn.bootx.starter.flowable.dto.instance.InstanceInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 实例查询
 * @author xxm
 * @date 2022/8/31 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmInstanceQueryService {
    private final BpmInstanceManager bpmInstanceManager;

    private final RuntimeService runtimeService;
    private final HistoryService historyService;

    /**
     * 我的发起分页
     */
    public PageResult<InstanceInfo> pageMyApply(PageParam pageParam){
        val instanceQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy(String.valueOf(SecurityUtil.getUserId()))
                .orderByProcessInstanceStartTime().desc();
        val historicProcessInstances = instanceQuery.listPage(pageParam.start(), pageParam.getSize());
        long total = instanceQuery.count();

        List<String> instanceId = historicProcessInstances.stream().map(HistoricProcessInstance::getId).collect(Collectors.toList());
        List<InstanceInfo> instanceInfos = this.convertInstanceInfo(instanceId);
        return new PageResult<InstanceInfo>().setCurrent(pageParam.getCurrent())
                .setRecords(instanceInfos)
                .setSize(pageParam.getSize())
                .setTotal(total);
    }

    /**
     * 获取流程实例详情
     */
    public BpmInstanceDto findByInstanceId(String instanceId){
        return bpmInstanceManager.findByInstanceId(instanceId).map(BpmInstance::toDto)
                .orElseThrow(() -> new DataNotExistException("流程实例不存在"));
    }

    /**
     * 获取流程执行的节点, 用于绘制流程进展图
     */
    public List<ActivityInstanceChart> getFlowNodes(String instanceId){
        List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId).list();

        return activityList.stream().map(BpmActivityConvert.CONVERT::convert).distinct().collect(Collectors.toList());
    }


    /**
     * 获取当前执行的节点
     */
    public List<String> getCurrentNodes(String instanceId){

        return runtimeService.createExecutionQuery().processInstanceId(instanceId)
                .list()
                .stream()
                .map(Execution::getActivityId)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 转换 processInstances 为 系统中的对象
     */
    public List<InstanceInfo> convertInstanceInfo(List<String> instanceIds){
        Map<String, BpmInstance> bpmInstanceMap = bpmInstanceManager.findAllByInstanceIds(instanceIds).stream().collect(Collectors.toMap(BpmInstance::getInstanceId, o -> o));
        return instanceIds.stream().map(instanceId -> {
            BpmInstance bpmInstance = Optional.ofNullable(bpmInstanceMap.get(instanceId)).orElse(new BpmInstance());
            return new InstanceInfo()
                    .setName(bpmInstance.getInstanceName())
                    .setInstanceId(bpmInstance.getInstanceId())
                    .setState(bpmInstance.getState())
                    .setStartTime(bpmInstance.getStartTime())
                    .setEndTime(bpmInstance.getEndTime())
                    .setStartUserId(bpmInstance.getStartUserId())
                    .setStartUserName(bpmInstance.getStartUserName())
                    .setDefMame(bpmInstance.getDefName());
        }).collect(Collectors.toList());
    }
}
