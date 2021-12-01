package cn.bootx.order.param.order;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* @author xxm
* @date 2020/10/15
*/
@Data
@Accessors(chain = true)
@Schema(title = "订单参数")
public class OrderParam implements Serializable {

    private static final long serialVersionUID = 546385575171313726L;

    @Schema(description= "code")
    private String code;

    @Schema(description= "购者id")
    private Long userId;

    @Schema(description= "订单描述")
    private String description;

    @Schema(description= "来源")
    private String source;

    @Schema(description= "渠道id")
    private Long channelId;

    @Schema(description= "订单类型")
    private Integer type;

    @Schema(description= "总金额")
    private BigDecimal totalAmount;

    @Schema(description= "附加参数 json")
    private String addition;

    @Schema(description= "设备id")
    private String deviceId;

    @Schema(description= "业务 ID")
    private Long businessId;

    @Schema(description= "订单明细")
    private List<OrderDetailParam> details;

    @Schema(description= "选择的优惠券")
    private List<Long> couponIds;

    @Schema(description= "全部待选优惠券",hidden = true)
    private List<Long> allCouponIds;

}
