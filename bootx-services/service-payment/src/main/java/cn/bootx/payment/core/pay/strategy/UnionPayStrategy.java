package cn.bootx.payment.core.pay.strategy;

import cn.bootx.payment.code.pay.PayChannelCode;
import cn.bootx.payment.core.pay.exception.ExceptionInfo;
import cn.bootx.payment.core.pay.func.AbsPayStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * 云闪付
 *
 * @author xxm
 * @date 2022/3/8
 */
@Slf4j
@Scope(SCOPE_PROTOTYPE)
@Service
@RequiredArgsConstructor
public class UnionPayStrategy extends AbsPayStrategy {

    @Override
    public int getType() {
        return PayChannelCode.UNION_PAY;
    }

    @Override
    public void doPayHandler() {

    }

    @Override
    public void doErrorHandler(ExceptionInfo exceptionInfo) {

    }

    @Override
    public void doCloseHandler() {

    }

    @Override
    public void doRefundHandler() {

    }

}
