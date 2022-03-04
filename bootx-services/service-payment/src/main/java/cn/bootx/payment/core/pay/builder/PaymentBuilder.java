package cn.bootx.payment.core.pay.builder;

import cn.bootx.common.spring.util.WebServletUtil;
import cn.bootx.payment.code.pay.PayChannelCode;
import cn.bootx.payment.code.pay.PayStatusCode;
import cn.bootx.payment.core.pay.local.SyncPayInfoLocal;
import cn.bootx.payment.core.payment.entity.Payment;
import cn.bootx.payment.dto.payment.PayChannelInfo;
import cn.bootx.payment.dto.pay.PayResult;
import cn.bootx.payment.dto.payment.PaymentDto;
import cn.bootx.payment.dto.payment.RefundableInfo;
import cn.bootx.payment.param.pay.PayModeParam;
import cn.bootx.payment.param.pay.PayParam;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 支付对象构建器
 * @author xxm
 * @date 2021/2/25
 */
@UtilityClass
public class PaymentBuilder {

    /**
     * 构建payment记录
     */
    public Payment buildPayment(PayParam payParam){
        Payment payment = new Payment();

        HttpServletRequest request = WebServletUtil.getRequest();
        String ip = ServletUtil.getClientIP(request);
        // 基础信息
        payment.setBusinessId(payParam.getBusinessId())
                .setUserId(payParam.getUserId())
                .setTitle(payParam.getTitle())
                .setDescription(payParam.getDescription());

        // 支付方式和状态
        List<PayChannelInfo> payTypeInfos = buildPayTypeInfo(payParam.getPayModeList());
        List<RefundableInfo> refundableInfos = buildRefundableInfo(payParam.getPayModeList());
        // 计算总价
        BigDecimal sumAmount = payTypeInfos.stream()
                .map(PayChannelInfo::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        // 支付通道信息
        payment.setPayChannelInfo(JSONUtil.toJsonStr(payTypeInfos))
                .setRefundableInfo(JSONUtil.toJsonStr(refundableInfos))
                .setPayStatus(PayStatusCode.TRADE_PROGRESS)
                .setAmount(sumAmount)
                .setClientIp(ip)
                .setRefundableBalance(sumAmount);
        return payment;
    }

    /**
     * 构建PayTypeInfo
     */
    private List<PayChannelInfo> buildPayTypeInfo(List<PayModeParam> payModeParamList) {
        return CollectionUtil.isEmpty(payModeParamList) ? Collections.emptyList() : payModeParamList.stream()
                .map(PayModeParam::toPayTypeInfo)
                .collect(Collectors.toList());
    }
    /**
     * 构建RefundableInfo
     */
    private List<RefundableInfo> buildRefundableInfo(List<PayModeParam> payModeParamList) {
        return CollectionUtil.isEmpty(payModeParamList) ? Collections.emptyList() : payModeParamList.stream()
                .map(PayModeParam::toRefundableInfo)
                .collect(Collectors.toList());
    }

    /**
     * 根据Payment构建PayParam支付参数
     */
    public PayParam buildPayParamByPayment(Payment payment){
        PayParam payParam = new PayParam();
        // 恢复 payModeList
        List<PayModeParam> payModeParams = payment.getPayChannelInfoList().stream()
                .map(payTypeInfo -> new PayModeParam()
                        .setAmount(payTypeInfo.getAmount())
                        .setPayChannel(payTypeInfo.getPayChannel())
                        .setExtraParamsJson(payTypeInfo.getExtraParamsJson()))
                .collect(Collectors.toList());
        payParam.setPayModeList(payModeParams)
                .setBusinessId(payment.getBusinessId())
                .setUserId(payment.getUserId())
                .setTitle(payment.getTitle())
                .setTitle(payment.getTitle())
                .setDescription(payment.getDescription());
        return payParam;
    }


    /**
     * 根据Payment构建PaymentResult
     *
     * @param payment payment
     * @return paymentVO
     */
    public PayResult buildResultByPayment(Payment payment) {
        PayResult paymentResult;
        try {
            paymentResult = new PayResult();
            PaymentDto paymentDto = new PaymentDto();
            BeanUtils.copyProperties(payment, paymentDto);
            // 异步支付信息
            paymentResult.setSyncPayChannel(payment.getSyncPayChannel())
                    .setSyncPayMode(payment.isSyncPayMode())
                    .setPayStatus(payment.getPayStatus())
                    .setPayment(paymentDto);

            List<PayChannelInfo> channelInfos = payment.getPayChannelInfoList();

            // 设置异步支付参数
            List<PayChannelInfo> moneyPayTypeInfos = channelInfos.stream()
                    .filter(payTypeInfo -> PayChannelCode.SYNC_TYPE.contains(payTypeInfo.getPayChannel()))
                    .collect(Collectors.toList());
            if (!CollUtil.isEmpty(moneyPayTypeInfos)) {
                paymentResult.setSyncPayInfo(SyncPayInfoLocal.get());
            }
            // 清空线程变量
        } finally {
            SyncPayInfoLocal.clear();
        }
        return paymentResult;
    }
}
