package cn.bootx.common.core.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**   
* 基础Dto
* @author xxm  
* @date 2020/6/1 16:12 
*/
@Getter
@Setter
@Accessors(chain = true)
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 2985535678913619503L;
    @Schema(name = "主键")
    private Long id;

    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "最后修改时间")
    private LocalDateTime lastModifiedTime;

    @Schema(name = "版本号")
    private Integer version;

}
