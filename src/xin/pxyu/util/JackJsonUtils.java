package xin.pxyu.util;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
JackJsonUtils生成json数据和解析json数据
解析json
@param content
@param valueType
 */
public class JackJsonUtils {
    static ObjectMapper objectMapper;
    public static<T> T fromJson(String content,Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   /*
JackJsonUtils生成json数据和解析json数据
生成json
@param object
 */
   public static String toJson(Object object){
       if(objectMapper==null){
           objectMapper=new ObjectMapper();
       }
       try {
           return objectMapper.writeValueAsString(object);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }
}
