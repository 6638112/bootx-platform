package cn.bootx.baseapi.core.parameter.entity;

import cn.bootx.baseapi.core.parameter.convert.SystemConvert;
import cn.bootx.baseapi.dto.parameter.SystemParameterDto;
import cn.bootx.baseapi.param.system.SystemParameterParam;
import cn.bootx.common.core.function.EntityBaseFunction;
import cn.bootx.common.mybatisplus.base.MpBaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统参数
 *
 * @author xxm
 * @date 2021/10/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("base_param")
public class SystemParameter extends MpBaseEntity implements EntityBaseFunction<SystemParameterDto> {

    /** 参数名称 */
    private String name;

    /** 参数键名 */
    private String paramKey;

    /** 参数值 */
    private String value;

    /** 参数类型 */
    private Integer type;

    /** 是否启用 */
    private Boolean enable;

    /** 内置参数 */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private boolean internal;

    /** 备注 */
    private String remark;

    @Override
    public SystemParameterDto toDto() {
        return SystemConvert.CONVERT.convert(this);
    }

    public static SystemParameter init(SystemParameterParam in) {
        return SystemConvert.CONVERT.convert(in);
    }

}
