package cn.bootx.common.mybatisplus.base;

import cn.bootx.common.core.annotation.actable.Column;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 *
 * @author xxm
 * @date 2022/7/26
 */
@Getter
@Setter
@FieldNameConstants(innerTypeName="Create")
public abstract class MpCreateEntity extends MpIdEntity{

    /** 创建者ID */
    @Column(comment = "创建者ID",order = Integer.MAX_VALUE-600)
    @TableField(fill = FieldFill.INSERT)
    private Long creator;

    /** 创建时间 */
    @Column(comment = "创建时间",order = Integer.MAX_VALUE-500)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
