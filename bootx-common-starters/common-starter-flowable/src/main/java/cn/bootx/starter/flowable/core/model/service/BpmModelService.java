package cn.bootx.starter.flowable.core.model.service;

import cn.bootx.common.core.exception.BizException;
import cn.bootx.common.core.exception.DataNotExistException;
import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.dto.LabelValue;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.common.mybatisplus.util.MpUtil;
import cn.bootx.starter.flowable.core.model.dao.BpmModelManager;
import cn.bootx.starter.flowable.core.model.dao.BpmModelTaskManager;
import cn.bootx.starter.flowable.core.model.entity.BpmModel;
import cn.bootx.starter.flowable.core.model.entity.BpmModelTask;
import cn.bootx.starter.flowable.dto.model.BpmModelDto;
import cn.bootx.starter.flowable.exception.ModelNotExistException;
import cn.bootx.starter.flowable.param.model.BpmModelParam;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.bootx.starter.flowable.code.ModelCode.*;

/**
 * 流程模型
 * @author xxm
 * @date 2022/8/23 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmModelService {

    private final RepositoryService repositoryService;

    private final BpmModelManager bpmModelManager;
    private final BpmModelTaskManager bpmModelTaskManager;

    /**
     * 创建模型
     */
    public void add(BpmModelParam bpmModelParam){
        BpmModel flowBpmModel = new BpmModel()
                .setName(bpmModelParam.getName())
                .setModelType(bpmModelParam.getModelType())
                .setMainProcess(false)
                .setPublish(UNPUBLISHED)
                .setModelEditorXml(DEFAULT_XML)
                .setEnable(false)
                .setRemark(bpmModelParam.getRemark());
        bpmModelManager.save(flowBpmModel);
    }

    /**
     * 上传bpm文件
     */
    public void uploadBpmn(BpmModelParam bpmModelParam){
        String modelEditorXml = bpmModelParam.getModelEditorXml();
        BpmModel bpmModel = bpmModelManager.findById(bpmModelParam.getId()).orElseThrow(ModelNotExistException::new);
        bpmModel.setModelEditorXml(modelEditorXml);
        bpmModelManager.updateById(bpmModel);
    }

    /**
     * 复制
     */
    public void copy(Long id){
        BpmModel flowBpmModel = bpmModelManager.findById(id).orElseThrow(ModelNotExistException::new);

    }

    /**
     * 修改
     */
    public void update(BpmModelParam param){
        BpmModel flowBpmModel = bpmModelManager.findById(param.getId()).orElseThrow(ModelNotExistException::new);

        BeanUtil.copyProperties(param, flowBpmModel, CopyOptions.create().ignoreNullValue());
        bpmModelManager.updateById(flowBpmModel);
    }

    /**
     * 发布流程
     */
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id){
        BpmModel bpmModel = bpmModelManager.findById(id).orElseThrow(ModelNotExistException::new);

        if (Objects.equals(bpmModel.getPublish(), PUBLISHED)){
            throw new BizException("流程模型已经发布");
        }
        //部署
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        Deployment deploy = deploymentBuilder.name(bpmModel.getName())
                // 文件后缀名有要求
                .addString(bpmModel.getName()+".bpmn", bpmModel.getModelEditorXml())
                .key(bpmModel.getDefKey())
                .category(bpmModel.getModelType())
                .deploy();
        //流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        //回填属性
        bpmModel.setDeployId(deploy.getId())
                .setDefId(processDefinition.getId())
                .setDefKey(processDefinition.getKey())
                .setDefName(processDefinition.getName())
                .setDefRemark(processDefinition.getDescription())
                .setMainProcess(true)
                .setPublish(PUBLISHED)
                .setEnable(true)
                .setProcessVersion(processDefinition.getVersion());
        bpmModelManager.cancelMainProcessByDefKey(bpmModel.getDefKey());
        bpmModelManager.updateById(bpmModel);
        this.updateTaskNodes(bpmModel);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        BpmModel flowBpmModel = bpmModelManager.findById(id).orElseThrow(() -> new DataNotExistException(""));

        //发布状态删除
        if (Objects.equals(PUBLISHED, flowBpmModel.getPublish())){
            // 先删除部署以及对应的流程
            repositoryService.deleteDeployment(flowBpmModel.getDeployId(),true);
            bpmModelManager.deleteById(id);
        }
        bpmModelManager.deleteById(id);
    }

    /**
     * 分页
     */
    public PageResult<BpmModelDto> page(PageParam pageParam, BpmModelParam bpmModelParam){
        return MpUtil.convert2DtoPageResult(bpmModelManager.page(pageParam,bpmModelParam));
    }

    /**
     * 获取单条
     */
    public BpmModelDto findById(Long id){
        return bpmModelManager.findById(id).map(BpmModel::toDto).orElseThrow(ModelNotExistException::new);
    }

    /**
     * 根据流程定义id获取模型信息
     */
    public BpmModelDto findByDefId(String defId){
        return bpmModelManager.findByDefId(defId).map(BpmModel::toDto).orElseThrow(ModelNotExistException::new);
    }

    /**
     * 获取生效并部署的主流程列表
     */
    public List<LabelValue> findMainProcess(){
        return bpmModelManager.findMainProcess().stream()
                .map(bpmModel -> new LabelValue(bpmModel.getName(),bpmModel.getId()))
                .collect(Collectors.toList());
    }

    /**
     * 更新关联任务节点信息
     */
    private void updateTaskNodes(BpmModel bpmModel){
        bpmModelTaskManager.lambdaUpdate()
                .set(BpmModelTask::getDefId,bpmModel.getDefId())
                .set(BpmModelTask::getDefKey, bpmModel.getDefKey())
                .eq(BpmModelTask::getModelId,bpmModel.getId())
                .update();
    }

    /**
     * 校验流程
     */
    public void verifyModel(){
        // 校验是否有xml文档

        // 校验对应的任务节点是否已经被配置
    }
}
