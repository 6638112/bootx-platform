package cn.bootx.starter.file.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**   
* 上传文件信息
* @author xxm  
* @date 2022/1/12 
*/
@Data
@Accessors(chain = true)
public class UpdateFileDto {

    /** 主键 */
    private Long id;

    /** 存储位置 */
    private Integer location;

    /** 文件名称 */
    private Integer fileName;

    /** 文件后缀 */
    private Integer fileSuffix;

    /** 文件大小 */
    private Integer fileSize;

    /** 创建者ID */
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
