package cn.bootx.starter.quartz;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**   
* 定时任务
* @author xxm  
* @date 2021/11/2 
*/
@ComponentScan
@MapperScan(annotationClass = Mapper.class)
public class QuartzJobApplication {
}