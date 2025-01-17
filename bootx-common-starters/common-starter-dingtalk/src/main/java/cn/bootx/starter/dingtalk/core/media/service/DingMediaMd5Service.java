package cn.bootx.starter.dingtalk.core.media.service;

import cn.bootx.starter.dingtalk.core.media.dao.DingMediaMd5Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xxm
 * @date 2022/7/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DingMediaMd5Service {

    private final DingMediaMd5Manager dingMediaMd5Manager;

}
