package cn.bootx.starter.data.perm.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**   
* 数据权限配置
* @author xxm  
* @date 2021/12/3 
*/
@Getter
@Setter
@ConfigurationProperties("bootx.starter.data-perm")
public class DataPermProperties {

    /** 开启字段加密 */

    /** 开启请求拦截 */

    /** 字段加密 key */
    private String fieldDecryptKey = "UCrtxSCwYZNCIlav";

}
