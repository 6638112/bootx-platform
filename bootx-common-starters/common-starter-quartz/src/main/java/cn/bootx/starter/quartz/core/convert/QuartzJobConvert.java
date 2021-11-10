package cn.bootx.starter.quartz.core.convert;

import cn.bootx.starter.quartz.core.entity.QuartzJob;
import cn.bootx.starter.quartz.dto.QuartzJobDto;
import cn.bootx.starter.quartz.param.QuartzJobParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**   
* 定时任务转换
* @author xxm  
* @date 2021/11/2 
*/
@Mapper
public interface QuartzJobConvert {
    QuartzJobConvert CONVERT = Mappers.getMapper(QuartzJobConvert.class);

    @Mappings({})
    QuartzJobDto convert(QuartzJob in);

    @Mappings({})
    QuartzJob convert(QuartzJobParam in);
}