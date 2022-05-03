package cn.bootx.payment.code;

public interface PaymentEventCode {

    /** 支付中心交换机 */
    String EXCHANGE_PAYMENT = "service.payment";

    /** 支付完成 */
    String PAY_COMPLETE = "pay.complete";

    /** 支付撤销 */
    String PAY_CANCEL = "pay.cancel";
}
