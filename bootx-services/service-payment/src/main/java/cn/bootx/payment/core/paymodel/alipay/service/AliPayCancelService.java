package cn.bootx.payment.core.paymodel.alipay.service;

import cn.bootx.common.core.exception.BizException;
import cn.bootx.payment.code.paymodel.AliPayCode;
import cn.bootx.payment.core.payment.entity.Payment;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ijpay.alipay.AliPayApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 支付宝支付取消和退款
 * @author xxm
 * @date 2021/4/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliPayCancelService {

    /**
     * 关闭支付
     */
    public void cancelRemote(Payment payment){
        // 只有部分需要调用支付宝网关进行关闭
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        model.setOutTradeNo(String.valueOf(payment.getId()));

        try {
            AlipayTradeCancelResponse response = AliPayApi.tradeCancelToResponse(model);
            log.info(JSONUtil.toJsonStr(response));
            if (!Objects.equals(AliPayCode.SUCCESS,response.getCode())){
                log.error("网关返回撤销失败: {}",response.getSubMsg());
                throw new BizException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("关闭订单失败:",e);
            throw new BizException("关闭订单失败");
        }
    }

    /**
     * 退款
     */
    public void refund(Payment payment, BigDecimal amount){
        AlipayTradeRefundModel refundModel = new AlipayTradeRefundModel();
        refundModel.setOutTradeNo(String.valueOf(payment.getId()));
        refundModel.setRefundAmount(amount.toPlainString());
        try {
            AlipayTradeRefundResponse response = AliPayApi.tradeRefundToResponse(refundModel);
            log.info(JSONUtil.toJsonStr(response));
            if (!Objects.equals(AliPayCode.SUCCESS,response.getCode())){
                log.error("网关返回退款失败: {}",response.getSubMsg());
                throw new BizException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("订单退款失败:",e);
            throw new BizException("订单退款失败");
        }
    }
}


