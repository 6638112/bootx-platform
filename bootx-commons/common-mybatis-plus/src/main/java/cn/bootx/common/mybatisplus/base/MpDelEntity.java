package cn.bootx.common.mybatisplus.base;

import cn.bootx.common.core.annotation.actable.Column;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
* MP基础类, 真实删除
* @author xxm
* @date 2022/7/17
*/
@Getter
@Setter
@FieldNameConstants(innerTypeName="Del")
public abstract class MpDelEntity extends MpCreateEntity{

    /** 最后修者ID */
    @Column(comment = "最后修者ID",order = Integer.MAX_VALUE-400)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long lastModifier;

    /** 最后修改时间 */
    @Column(comment = "最后修改时间",order = Integer.MAX_VALUE-300)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedTime;

    /** 乐观锁 */
    @Version
    @Column(comment = "乐观锁",isNull = false,order = Integer.MAX_VALUE-200)
    private Integer version = 0;
}
