package cn.bootx.baseapi.core.sql.convert;

import cn.bootx.baseapi.core.sql.entity.QuerySql;
import cn.bootx.baseapi.dto.sql.QuerySqlDto;
import cn.bootx.baseapi.param.sql.QuerySqlParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author xxm
 * @date 2023/3/13
 */
@Mapper
public interface QuerySqlConvert {

    QuerySqlConvert CONVERT = Mappers.getMapper(QuerySqlConvert.class);

    QuerySqlDto convert(QuerySql in);

    QuerySql convert(QuerySqlParam in);

}
