package cn.bootx.common.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * spring默认配置
 * @author xxm
 * @date 2023/3/29
 */
@EnableScheduling
@EnableRetry
@ComponentScan
public class SpringConfigApplication {

}
