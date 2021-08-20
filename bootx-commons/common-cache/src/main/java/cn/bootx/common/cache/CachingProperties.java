package cn.bootx.common.cache;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
* spring cache 配置
* @author xxm
* @date 2021/6/11
*/
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "bootx.common.cache")
public class CachingProperties {

    /** 默认超时时间 */
    private Integer defaultTtl = 60*30;

    /** 自定义过期时间 秒 */
    private Map<String,Integer> keysTtl = new HashMap<>();
}
