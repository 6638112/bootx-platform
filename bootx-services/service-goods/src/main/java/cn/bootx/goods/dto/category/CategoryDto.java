package cn.bootx.goods.dto.category;

import cn.bootx.common.core.rest.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

;

/**
* @author xxm
* @date 2020/11/19
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(title = "类目DTO")
public class CategoryDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -6395120064541083702L;

    @Schema(description="上级类目id")
    private Long pid;

    @Schema(description="类目名称", required = true)
    private String name;

    @Schema(description="类目描述")
    private String remark;

    @Schema(description="是否启用")
    private Boolean enable;

    @Schema(description="排序，默认0")
    private Double sortNo;

    @Schema(description= "层级")
    private Integer level;

    @Schema(description= " 图标/图片地址")
    private String image;

}