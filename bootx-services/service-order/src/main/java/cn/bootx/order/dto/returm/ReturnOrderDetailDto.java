package cn.bootx.order.dto.returm;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**   
*
* @author xxm  
* @date 2020/11/19 
*/
@Data
@Accessors(chain = true)
@Schema(title = "退货单明细dto")
public class ReturnOrderDetailDto implements Serializable {

    private static final long serialVersionUID = 3467727603218965746L;

    @Schema(name = "主键ID")
    private Long id;

    @Schema(name = "订单ID")
    private Long orderId;

    @Schema(name = "skuId")
    private Long skuId;

    @Schema(name = "支付金额")
    private BigDecimal payAmount;

    @Schema(name = "原订单明细ID")
    private Long originDetailId;

}
