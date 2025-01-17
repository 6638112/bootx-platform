package cn.bootx.starter.flowable.param.task;

import cn.bootx.starter.flowable.code.TaskCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 流程任务审批参数
 *
 * @author xxm
 * @date 2022/8/26
 */
@Data
@Accessors(chain = true)
@Schema(title = "流程任务审批参数")
public class TaskApproveParam {

    /**
     * @see TaskCode#RESULT_PASS
     */
    @Schema(description = "处理结果")
    private String type;

    @Schema(description = "任务id")
    private String taskId;

    @Schema(description = "下一步节点")
    private String nextNodeId;

    @Schema(description = "退回节点节点")
    private String backNodeId;

    @Schema(description = "下一步执行人")
    private Object nextAssign;

    @Schema(description = "表单数据")
    private Map<String, Object> formVariables;

    @Schema(description = "审批意见")
    private String reason;

}
