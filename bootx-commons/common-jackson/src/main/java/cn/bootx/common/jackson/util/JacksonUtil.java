package cn.bootx.common.jackson.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**   
* jackson常用工具类封装
* @author xxm  
* @date 2020/11/29 
*/
@Slf4j
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    static void setObjectMapper(ObjectMapper objectMapper){
        JacksonUtil.objectMapper = objectMapper;
    }

    /**
     * 对象序列化为json字符串,转换异常将被抛出
     */
    public static String toJson(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json序列化失败");
        }
    }

    /**
     *  JSON字符串转为实体类对象，转换异常将被抛出
     */
    public static <T> T toBean(String content, Class<T> valueType){
        try {
            return objectMapper.readValue(content,valueType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("json反序列化失败");
        }
    }
    /**
     *  JSON字符串转为实体类对象，转换异常将被抛出
     */
    public static <T> T toBean(String content, TypeReference<? extends T> ref){
        try {
            return objectMapper.readValue(content,ref);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("json反序列化失败");
        }
    }
}