package cn.bootx.starter.flowable.handler.behavior;

import cn.bootx.starter.flowable.util.FlowableUtil;
import org.flowable.bpmn.model.Activity;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;

import java.util.List;

/**   
 * 串签处理
 * @author xxm  
 * @date 2022/9/6 
 */
public class BpmSequentialMultiInstanceBehavior extends SequentialMultiInstanceBehavior {
    private final BpmParallelMultiInstanceAssignService assignService;
    public BpmSequentialMultiInstanceBehavior(Activity activity, AbstractBpmnActivityBehavior innerActivityBehavior, BpmParallelMultiInstanceAssignService assignService) {
        super(activity, innerActivityBehavior);
        this.assignService = assignService;
    }

    /**
     * 多实例创建
     */
    @Override
    protected int resolveNrOfInstances(DelegateExecution execution) {
        // 清空collectionExpression , 它和 collectionVariable 是互斥的
        super.collectionExpression = null;
        // 设置 collectionElementVariable(迭代出来的处理人) 和 collectionString(候选人集合)
        super.collectionString = FlowableUtil.formatCollectionVariable(execution.getCurrentActivityId());
        super.collectionElementVariable = FlowableUtil.formatCollectionElementVariable(execution.getCurrentActivityId());

        List<Long> taskUsers = assignService.getTaskUsers(execution,this);
        execution.setVariable(super.collectionString, taskUsers);
        return taskUsers.size();
    }

    /**
     * 是否满足结束条件
     */
    @Override
    public boolean completionConditionSatisfied(DelegateExecution execution) {
        return super.completionConditionSatisfied(execution);
    }
}
