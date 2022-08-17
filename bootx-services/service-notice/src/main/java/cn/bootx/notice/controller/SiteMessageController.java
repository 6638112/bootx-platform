package cn.bootx.notice.controller;

import cn.bootx.common.core.rest.PageResult;
import cn.bootx.common.core.rest.Res;
import cn.bootx.common.core.rest.ResResult;
import cn.bootx.common.core.rest.param.PageParam;
import cn.bootx.notice.core.site.domain.SiteMessageInfo;
import cn.bootx.notice.core.site.service.SiteMessageService;
import cn.bootx.notice.dto.site.SiteMessageDto;
import cn.bootx.notice.param.site.SendSiteMessageParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**   
*
* @author xxm  
* @date 2021/8/8 
*/
@Tag(name ="站内信")
@RestController
@RequestMapping("/site/message")
@RequiredArgsConstructor
public class SiteMessageController {
    private final SiteMessageService siteMessageService;
    
    @Operation(summary = "发送站内信")
    @PostMapping("/send")
    public ResResult<Void> send(@RequestBody SendSiteMessageParam param){
        siteMessageService.send(param);
        return Res.ok();
    }

    @Operation(summary = "消息详情")
    @GetMapping("/findById")
    public ResResult<SiteMessageDto> findById(Long id){
        return Res.ok(siteMessageService.findById(id));
    }

    @Operation(summary = "获取未读的接收消息条数")
    @GetMapping("/countByReceiveNotRead")
    public ResResult<Integer> countByReceiveNotRead(SiteMessageInfo query){
        return Res.ok(siteMessageService.countByReceiveNotRead(query));
    }

    @Operation(summary = "接收消息分页")
    @GetMapping("/pageByReceive")
    public ResResult<PageResult<SiteMessageInfo>> pageByReceive(PageParam pageParam,SiteMessageInfo query){
        return Res.ok(siteMessageService.pageByReceive(pageParam,query));
    }

    @Operation(summary = "发送消息分页")
    @GetMapping("/pageBySender")
    public ResResult<PageResult<SiteMessageDto>> pageBySender(PageParam pageParam, Boolean haveRead){
        return Res.ok();
    }

    @Operation(summary = "消息分页")
    @GetMapping("/page")
    public ResResult<PageResult<SiteMessageDto>> page(PageParam pageParam){
        return Res.ok(siteMessageService.page(pageParam));
    }

    @Operation(summary = "标为已读")
    @PostMapping("/read")
    public ResResult<Void> read(Long id){
        siteMessageService.read(id);
        return Res.ok();
    }
}
