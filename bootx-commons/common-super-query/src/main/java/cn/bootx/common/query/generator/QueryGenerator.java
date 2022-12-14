package cn.bootx.common.query.generator;

import cn.bootx.common.query.entity.QueryParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 查询条件生成器
 * @author xxm
 * @date 2021/11/17
 */
public class QueryGenerator {

    /**
     * 生成查询条件 (根据查询参数对象生成)
     * @param queryParams 参数
     * @param <T> 泛型
     * @return 查询器
     */
    public static <T> QueryWrapper<T> generator(QueryParams queryParams) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 查询条件
        SuperQueryGenerator.initQueryParam(queryWrapper,queryParams.getQueryParams());
        // 排序条件
        SuperQueryGenerator.initQueryOrder(queryWrapper,queryParams.getQueryOrders());

        return queryWrapper;
    }


    /**
     * 生成查询条件 (根据实体对象生成), 生成的多个查询条件之间用And连接
     * @param queryParams 参数
     * @param <T> 泛型
     * @return 查询器
     */
    public static <T> QueryWrapper<T> generator(Object queryParams) {
        return AnnotationQueryGenerator.generator(queryParams);
    }

    /**
     * 生成查询条件 (根据实体对象生成), 生成的多个查询条件之间用And连接
     * @param queryParams 参数
     * @param clazz 数据库Entity类
     * @param <T> 泛型
     * @return 查询器
     */
    public static <T> QueryWrapper<T> generator(Object queryParams,Class<T> clazz) {
        return AnnotationQueryGenerator.generator(queryParams,clazz);
    }

}
