package cn.bootx.payment.core.pay.strategy;

import cn.bootx.payment.code.pay.PayChannelCode;
import cn.bootx.payment.core.pay.func.AbsPayStrategy;
import cn.bootx.payment.core.paymodel.voucher.entity.Voucher;
import cn.bootx.payment.core.paymodel.voucher.service.VoucherPayService;
import cn.bootx.payment.core.paymodel.voucher.service.VoucherPaymentService;
import cn.bootx.payment.core.paymodel.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**   
* 储值卡支付
* @author xxm  
* @date 2022/3/13 
*/
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class VoucherStrategy extends AbsPayStrategy {
    private final VoucherPayService voucherPayService;
    private final VoucherService voucherService;
    private final VoucherPaymentService voucherPaymentService;
    private List<Voucher> vouchers;

    @Override
    public int getType() {
        return PayChannelCode.VOUCHER;
    }

    /**
     * 支付前处理
     */
    @Override
    public void doBeforePayHandler() {
        // 获取并校验余额
        this.vouchers = voucherPayService.getAndCheckVoucher(this.getPayMode());
    }

    /**
     * 支付操作
     */
    @Override
    public void doPayHandler() {
        voucherPayService.pay(getPayMode().getAmount(),this.getPayment(),this.vouchers);
    }

    @Override
    public void doCloseHandler() {

    }

    @Override
    public void doRefundHandler() {

    }
}
