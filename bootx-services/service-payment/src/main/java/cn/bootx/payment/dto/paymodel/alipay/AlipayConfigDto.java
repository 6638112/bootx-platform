package cn.bootx.payment.dto.paymodel.alipay;

import cn.bootx.common.core.enums.SensitiveType;
import cn.bootx.common.core.rest.dto.BaseDto;
import cn.bootx.common.jackson.sensitive.SensitiveInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
* @author xxm
* @date 2021/2/26
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("支付宝配置")
public class AlipayConfigDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 6641158663606363171L;

    /** 名称 */
    private String name;

    /** 支付宝商户appId */
    @SensitiveInfo(value = SensitiveType.OTHER)
    private String appId;

    /** 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 */
    private String notifyUrl;

    /** 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址 */
    private String returnUrl;

    /** 请求网关地址 */
    private String serverUrl;

    /** 认证类型 证书/公钥 */
    private Integer authType;

    /** 签名类型 */
    private String signType;

    /** 支付宝公钥 */
    private String alipayPublicKey;

    /** 私钥 */
    private String privateKey;

    /** 应用公钥证书 */
    private String appCert;

    /** 支付宝公钥证书文件 */
    private String alipayCert;

    /** 支付宝CA根证书文件 */
    private String alipayRootCert;

    /** 超时配置 */
    private String expireTime;

    /** 可用支付方式 */
    private List<String> payTypeList;

    /** 是否沙箱环境 */
    private boolean sandbox;

    /** 是否启用 */
    private Boolean activity;

    /** 状态 */
    private Integer state;

    /** 备注 */
    private String remark;
}
