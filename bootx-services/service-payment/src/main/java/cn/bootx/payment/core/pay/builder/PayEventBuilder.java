package cn.bootx.payment.core.pay.builder;

import cn.bootx.payment.core.payment.entity.Payment;
import cn.bootx.payment.event.PayCancelEvent;
import cn.bootx.payment.event.PayCompleteEvent;
import cn.bootx.payment.event.PayRefundEvent;
import lombok.experimental.UtilityClass;

/**   
* 支付事件生成器
* @author xxm  
* @date 2022/7/11 
*/
@UtilityClass
public class PayEventBuilder {
    /**
     * 支付完成
     */
    public PayCompleteEvent buildPayComplete(Payment payment) {
        PayCompleteEvent event = new PayCompleteEvent()
                .setPaymentId(payment.getId())
                .setBusinessId(payment.getBusinessId());
        return event;
    }

    /**
     * 支付撤销/关闭
     */
    public PayCancelEvent buildPayCancel(Payment payment){
        PayCancelEvent event = new PayCancelEvent()
                .setPaymentId(payment.getId())
                .setBusinessId(payment.getBusinessId());
        return event;
    }

    /**
     * 支付退款
     */
    public PayRefundEvent buildPayRefund(Payment payment){
        PayRefundEvent event = new PayRefundEvent()
                .setPaymentId(payment.getId())
                .setBusinessId(payment.getBusinessId());
        return event;
    }

}
