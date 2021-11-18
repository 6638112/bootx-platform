package cn.bootx.starter.query.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**   
*
* @author xxm  
* @date 2021/11/17 
*/
@Data
@Accessors(chain = true)
@ApiModel("查询排序")
public class QueryOrder {

    @ApiModelProperty("设置排序字段")
    private String sortField;

    @ApiModelProperty("是否升序")
    private boolean asc = true;
}
