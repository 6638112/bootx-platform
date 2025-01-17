package cn.bootx.iam.core.auth.handler;

import cn.bootx.iam.core.client.dao.ClientManager;
import cn.bootx.iam.core.client.entity.Client;
import cn.bootx.starter.auth.authentication.GetAuthClientService;
import cn.bootx.starter.auth.entity.AuthClient;
import cn.bootx.starter.auth.exception.ApplicationNotFoundException;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 获取认证应用
 *
 * @author xxm
 * @date 2022/6/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GetAuthClientServiceImpl implements GetAuthClientService {

    private final ClientManager clientManager;

    @Override
    public AuthClient getAuthApplication(String authClientCode) {
        Client client = clientManager.findByCode(authClientCode).orElseThrow(ApplicationNotFoundException::new);
        AuthClient authClient = new AuthClient();
        BeanUtil.copyProperties(client, authClient);
        return authClient;
    }

}
