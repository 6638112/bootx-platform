package cn.bootx.sales.core.match.convert;

import cn.bootx.sales.core.match.entity.MatchRuleConfig;
import cn.bootx.sales.dto.match.MatchRuleDto;
import cn.bootx.sales.param.strategy.MatchRuleParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 转换
* @author xxm
* @date 2021/5/19
*/
@Mapper
public interface MatchRuleConvert {
    MatchRuleConvert INSTANCE = Mappers.getMapper(MatchRuleConvert.class);

    MatchRuleConfig convert(MatchRuleDto in);

    MatchRuleConfig convert(MatchRuleParam in);

    MatchRuleDto convert(MatchRuleConfig in);
}
