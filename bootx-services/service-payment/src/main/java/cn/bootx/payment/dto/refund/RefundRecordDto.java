package cn.bootx.payment.dto.refund;

import cn.bootx.common.core.rest.dto.BaseDto;
import cn.bootx.payment.dto.payment.RefundableInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**   
* 退款记录
* @author xxm  
* @date 2022/3/2 
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "退款记录")
public class RefundRecordDto extends BaseDto {

    @Schema(description= "关联的业务id")
    private String businessId;

    @Schema(description = "付款付单号")
    private Long paymentId;

    @Schema(description = "异步方式关联退款请求号(部分退款情况)")
    private String refundRequestNo;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "退款金额")
    private BigDecimal amount;

    @Schema(description = "剩余可退")
    private BigDecimal refundableBalance;

    @Schema(description = "退款终端ip")
    private String clientIp;

    @Schema(description = "退款时间")
    private LocalDateTime refundTime;
    /**
     * @see RefundableInfo
     */
    @Schema(description = "退款信息列表")
    private String refundableInfo;

    /**
     * @see cn.bootx.payment.code.pay.PayStatusCode
     */
    @Schema(description = "退款状态")
    private int refundStatus;

    @Schema(description = "错误码")
    private String errorCode;

    @Schema(description = "错误信息")
    private String errorMsg;
}
