package cn.bootx.common.actable.annotation.impl;

import cn.bootx.common.core.annotation.actable.Column;
import cn.bootx.common.core.code.actable.MySqlTypeConstant;
import cn.bootx.common.actable.utils.ColumnUtils;

import java.lang.annotation.Annotation;

/**
 * 实现行注解
 * @author xxm
 * @date 2023/1/17
 */
public class ColumnImpl implements Column {
    /**
     * 字段名
     * 1.4.0版本支持，类同javax.persistence.Column.name
     *
     * @return 字段名：不填默认使用属性名作为表字段名
     */
    @Override
    public String value() {
        return "";
    }

    /**
     * 字段名
     * 1.3.0版本支持，类同javax.persistence.Column.name
     *
     * @return 字段名：不填默认使用属性名作为表字段名
     */
    @Override
    public String name() {
        return "";
    }

    /**
     * 数据库字段排序, 数字小的在前面
     */
    @Override
    public int order() {
        return 0;
    }

    /**
     * 字段类型：不填默认使用属性的数据类型进行转换，转换失败的字段不会添加
     * 仅支持cn.bootx.common.actable.constants.MySqlTypeConstant中的枚举数据类型
     * 不填默认转换类：cn.bootx.common.actable.command.JavaToMysqlType
     * 1.3.0版本支持，也可通过注解实现：cn.bootx.common.core.annotation.actable.ColumnType
     *
     * @return 字段类型
     */
    @Override
    public MySqlTypeConstant type() {
        return MySqlTypeConstant.DEFAULT;
    }

    /**
     * 字段长度，默认是255
     * 类型默认长度参考：cn.bootx.common.core.code.actable.MySqlTypeConstant
     * 1.3.0版本支持，类同javax.persistence.Column.length
     *
     * @return 默认字段长度，默认是255
     */
    @Override
    public int length() {
        return 255;
    }

    /**
     * 小数点长度，默认是0
     * 类型默认长度参考：cn.bootx.common.core.code.actable.MySqlTypeConstant
     * 1.3.0版本支持，类同javax.persistence.Column.scale
     *
     * @return 小数点长度，默认是0
     */
    @Override
    public int decimalLength() {
        return 0;
    }

    /**
     * 是否为可以为null，true是可以，false是不可以，默认为true
     * 也可通过注解实现：cn.bootx.common.core.annotation.actable.IsNotNull
     * 1.3.0版本支持，类同javax.persistence.Column.nullable
     *
     * @return 是否为可以为null，true是可以，false是不可以，默认为true
     */
    @Override
    public boolean isNull() {
        return true;
    }

    /**
     * 是否是主键，默认false
     * 也可通过注解实现：cn.bootx.common.core.annotation.actable.IsKey
     * 1.3.0版本支持，类同javax.persistence.Id
     *
     * @return 是否是主键，默认false
     */
    @Override
    public boolean isKey() {
        return false;
    }

    /**
     * 是否自动递增，默认false
     * 也可通过注解实现：cn.bootx.common.core.annotation.actable.IsAutoIncrement
     *
     * @return 是否自动递增，默认false 只有主键才能使用
     */
    @Override
    public boolean isAutoIncrement() {
        return false;
    }

    /**
     * 默认值，默认为null
     * 1.3.0版本支持，也可通过注解实现：cn.bootx.common.core.annotation.actable.DefaultValue
     *
     * @return 默认值
     */
    @Override
    public String defaultValue() {
        return ColumnUtils.DEFAULT_VALUE;
    }

//    /**
//     * 开启默认值原生模式
//     * 原生模式介绍：默认是false表示非原生，此时value只支持字符串形式，会将value值以字符串的形式设置到字段的默认值，例如value="aa" 即sql为 DEFAULT "aa"
//     * 如果设置isNative=true，此时如果value="current_timestamp"，即sql为 DEFAULT current_timestamp
//     *
//     * @return
//     */
//    @Override
//    public boolean isNativeDefValue() {
//        return false;
//    }

    /**
     * 数据表字段备注
     * 1.3.0版本支持，也可通过注解实现：cn.bootx.common.actable.annotation.Comment
     *
     * @return 默认值，默认为空
     */
    @Override
    public String comment() {
        return "";
    }

    /**
     * 是否排除该字段, 默认不排除
     */
    @Override
    public boolean ignore() {
        return false;
    }

    /**
     * Returns the annotation type of this annotation.
     *
     * @return the annotation type of this annotation
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
