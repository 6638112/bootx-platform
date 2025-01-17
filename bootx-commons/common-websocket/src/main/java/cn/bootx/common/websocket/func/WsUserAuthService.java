package cn.bootx.common.websocket.func;

/**
 * websocket用户认证相关接口
 *
 * @author xxm
 * @date 2022/6/9
 */
public interface WsUserAuthService {

    /**
     * 根据token获取用户Id
     */
    Long getUserIdByToken(String token);

}
