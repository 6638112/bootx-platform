package cn.bootx.starter.dingtalk.param.notice;

import cn.bootx.starter.dingtalk.code.DingTalkNoticeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
* @author xxm
* @date 2020/11/29
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Schema(title = "钉钉文本消息")
public class DingTalkTextNotice extends DingTalkNotice implements Serializable {

    private static final long serialVersionUID = 8237431306046852539L;
    @Schema(description= "文本消息体")
    private DingTalkText text;

    public DingTalkTextNotice(String msg, List<String> phones) {
        super(new DingTalkAt(phones), DingTalkNoticeCode.MSG_TEXT);
        text = new DingTalkText(msg);
    }

    public DingTalkTextNotice(String msg) {
        super(DingTalkNoticeCode.MSG_TEXT);
        text = new DingTalkText(msg);
    }
}