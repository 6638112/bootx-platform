package cn.bootx.starter.auth.authentication;

import cn.bootx.starter.auth.entity.AuthClient;

/**
 * 获取认证终端
 *
 * @author xxm
 * @date 2022/6/27
 */
public interface GetAuthClientService {

    /**
     * 认证应用
     */
    AuthClient getAuthApplication(String authApplicationCode);

}
