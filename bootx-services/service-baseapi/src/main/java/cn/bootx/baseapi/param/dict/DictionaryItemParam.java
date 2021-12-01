package cn.bootx.baseapi.param.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
* 字典项参数
* @author xxm  
* @date 2021/8/4 
*/
@Data
@Accessors(chain = true)
@Schema(title = "字典项参数")
public class DictionaryItemParam implements Serializable {
    private static final long serialVersionUID = -6847496213782805488L;

    @Schema(description= "主键")
    private Long id;

    /** 字典ID */
    private Long dictId;

    /** 字典编码 */
    private String dictCode;

    /** 字典项编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 字典项排序 */
    private Double sortNo;

    /** 备注 */
    private String remark;
}
