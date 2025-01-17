package cn.bootx.starter.wechat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户取消关注订阅消息
 *
 * @author xxm
 * @date 2022/7/16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeChatUnSubscribeHandler implements WeChatMpMessageHandler {

    @Override
    public String getEvent() {
        return WxConsts.EventType.UNSUBSCRIBE;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
            WxSessionManager sessionManager) {
        log.info("取消关注用户 OPENID: " + wxMessage.getFromUser());
        return null;
    }

}
