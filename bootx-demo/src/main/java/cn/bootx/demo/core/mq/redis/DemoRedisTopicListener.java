package cn.bootx.demo.core.mq.redis;

import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.redis.listener.RedisTopicListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试Redis消息队列
 *
 * @author xxm
 * @date 2022/5/7
 */
@Slf4j
@Component
public class DemoRedisTopicListener implements RedisTopicListener<ResResult<String>> {

    @Override
    public String getTopic() {
        return "demo:redis";
    }

    @Override
    public void onMessage(ResResult<String> obj) {
        log.info("{}", obj);
    }

}
