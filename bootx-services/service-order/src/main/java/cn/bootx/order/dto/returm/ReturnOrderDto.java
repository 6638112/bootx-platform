package cn.bootx.order.dto.returm;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @author xxm
* @date 2020/11/19
*/
@Data
@Accessors(chain = true)
@Schema(title = "退货单dto")
public class ReturnOrderDto implements Serializable {
    private static final long serialVersionUID = 3654344763212531452L;

    /**
     * 退款中
     */
    public static final int STATE_REFUNDING = 0;

    /**
     * 退款成功
     */
    public static final int STATE_REFUNDED = 1;

    /**
     * 退款失败
     */
    public static final int STATE_REFUND_FAILURE = 2;


    @Schema(name = "主键ID")
    private Long id;

    @Schema(name = "租户ID")
    private Long tenantId;

    @Schema(name = "渠道ID")
    private Long channelId;

    @Schema(name = "支付金额")
    private BigDecimal payAmount;

    @Schema(name = "原订单ID")
    private Long originOrderId;

    @Schema(name = "退货单状态")
    private Integer state;

    @Schema(name = "业务主键")
    private Long businessId;

    private List<ReturnOrderDetailDto> detailDtos;
}
