package cn.bootx.demo.controller;

import cn.bootx.common.core.annotation.Idempotent;
import cn.bootx.common.core.annotation.IgnoreAuth;
import cn.bootx.common.core.annotation.OperateLog;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.sequence.func.Sequence;
import cn.bootx.common.sequence.impl.DefaultRangeSequence;
import cn.bootx.common.sequence.range.SeqRangeConfig;
import cn.bootx.common.sequence.range.SeqRangeManager;
import cn.bootx.common.websocket.entity.WsRes;
import cn.bootx.common.websocket.entity.WsResult;
import cn.bootx.common.websocket.service.UserWsNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@IgnoreAuth
@Slf4j
@Tag(name ="测试控制器")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final Sequence sequence;
    private final SeqRangeManager seqRangeManager;
    private final UserWsNoticeService userWsNoticeService;

    @OperateLog(title = "测试日志")
    @OperateLog(title = "测试重复日志")
    @Idempotent
    @Operation(summary = "测试")
    @GetMapping("/hello")
    public ResResult<String> hello(){
        return Res.ok("hello");
    }

    @OperateLog(title = "测试回声日志", saveParam = true, saverReturn = true)
    @Operation(summary = "测试回声")
    @GetMapping("/say")
    public ResResult<String> say(String msg){
        return Res.ok(msg);
    }


    @Operation(summary = "序列生成器")
    @GetMapping("/sequence")
    public ResResult<String> sequence(){
        long cs = sequence.next("cs");
        return Res.ok(String.valueOf(cs));
    }

    @Operation(summary = "序列生成器自定义")
    @GetMapping("/sequenceZdy")
    public ResResult<Long> sequenceZdy(){
        SeqRangeConfig seqRangeConfig = new SeqRangeConfig()
                .setStep(5)
                .setRangeStart(0)
                .setRangeStep(5);
        DefaultRangeSequence defaultRangeSequence = new DefaultRangeSequence(seqRangeManager, seqRangeConfig);
        return Res.ok(defaultRangeSequence.next("aa"));
    }

    @Operation(summary = "校验测试")
    @GetMapping("/validation")
    public ResResult<Void> validation(@NotBlank(message = "校验测试") String msg, @NotNull(message = "不为空") Integer a){
        return Res.ok();
    }

    @Operation(summary = "用户全局ws消息通知测试")
    @GetMapping("/userNotice")
    public ResResult<Void> userNotice(Long id){

        // 推送消息通知框
        WsResult<String> result = WsRes.notificationError("警告");
        userWsNoticeService.sendMessageByUser(result,id);

        // 推送消息事件(通常由指定页面进行监听)
        result = WsRes.eventNotice("hello", "cs");
        userWsNoticeService.sendMessageByUser(result,id);
        return Res.ok();
    }
}
