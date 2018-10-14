package com.silence.mini.program.life.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author coocaapc
 */
public class JsonUtils {

    protected static final ObjectMapper mapper =new MyObjectMapper();
    /**
     * Json字符串转Java对象
     *
     * @param jsonString
     * @param classOfT
     * @return
     */
    public static <T> T json2Object(String jsonString, Class<T> classOfT) {

        return JSON.toJavaObject(JSON.parseObject(jsonString), classOfT);
    }

    /**
     * Json字符串转Map
     *
     * @param jsonString
     * @return
     */
    public static Map<String, String> json2Map(String jsonString) {

        return (Map<String, String>) JSON.parse(jsonString);
    }

    /**
     * Json字符串转Map
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> json2MapObj(String jsonString) {

        return (Map<String, Object>) JSON.parse(jsonString);
    }

    /**
     * Json字符串转Map
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> json2Maps(String jsonString) {

        return (List<Map<String, Object>>) JSON.parse(jsonString);
    }

    /**
     * Json字符串转Map
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Integer>> json2MapsInt(String jsonString) {

        return (List<Map<String, Integer>>) JSON.parse(jsonString);
    }

    /**
     * Object对象传字符串
     *
     * @param obj
     * @return
     */
    public static String obj2Json(Object obj) {

        return JSON.toJSONString(obj);
    }

    /**
     * Json字符串转List对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> clazz) {

        return JSONArray.parseArray(jsonStr, clazz);
    }

    /**
     * @param o 被转换对象
     * @return 字符串
     * @throws JsonProcessingException
     */
    public static <T> String toJson(T o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("实体转换成json出错",e);
        }
    }
    /**
     * 将泛型集合生成树型JSON格式字符串
     *
     * @param list
     * @param strPK  主键属性名
     * @param strFK  外键属性名
     * @param object 默认为NULL
     * @return
     * @throws Exception
     */
    public static String getListToTreeJson(List<?> list, String strPK, String strFK, Object object) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(generateTreeJson(list, strPK, strFK, object));
        sb.append("]");
        return sb.toString().replaceAll(",\"children\":\\[]", "").replaceAll(",]", "]");
    }

    private static String generateTreeJson(List<?> list, String strPK, String strFK, Object object) throws Exception {
        StringBuffer buf = new StringBuffer();
        Object obj = null;
        if (object == null) {
            for (Object entity : list) {
                obj = entity;
                Class<? extends Object> clazz = entity.getClass();// 获取集合中的对象类型
                Field[] fieldList = clazz.getDeclaredFields();// 获取他的字段数组
                // 根据字段名找到对应的get方法，无参数
                Object name = ReflectionUtils.invokeGetterMethod(entity, strFK);
                if (name.toString().equals("0")) {
                    buf.append("{");
                    // 循环要输出的字段
                    for (int i = 0, size = fieldList.length; i < size; i++) {
                        Field fieldSub = fieldList[i];
                        String filedName = fieldSub.getName();
                        if ("serialVersionUID".equals(filedName)) {
                            continue;
                        }
                        //metd = clazz.getMethod("get" + StringUtils.capitalize(fieldSub.getName()), null);
                        //Object value = metd.invoke(obj, null);
                        Object value = ReflectionUtils.invokeGetterMethod(obj, fieldSub.getName());

                        buf.append("\"").append(fieldSub.getName()).append("\"").append(":\"").append(value == null ? "" : value).append("\"");

                        if (i < size - 1) {
                            buf.append(",");
                        }
                    }
                    buf.append("}");
                    // 执行menuBean不为null的循环
                    buf.append(generateTreeJson(list, strPK, strFK, obj));
                    //buf.append(",\"children\":[");
                    //// 执行menuBean不为null的循环
                    //buf.append(generateTreeJson(list, strPK, strFK, obj));
                    //buf.append("]},");
                }
            }
        } else {
            for (Object entity : list) {
                obj = entity;
                Class<? extends Object> clazz = entity.getClass();// 获取集合中的对象类型
                Field[] fieldList = clazz.getDeclaredFields();// 获取他的字段数组

                Object name = ReflectionUtils.invokeGetterMethod(entity, strFK);
                Object pk = ReflectionUtils.invokeGetterMethod(object, strPK);

                if (name.equals(pk)) {
                    buf.append("{");
                    // 循环要输出的字段
                    for (int i = 0, size = fieldList.length; i < size; i++) {
                        Field fieldSub = fieldList[i];
                        String filedName = fieldSub.getName();
                        if ("serialVersionUID".equals(filedName)) {
                            continue;
                        }
                        Object value = ReflectionUtils.invokeGetterMethod(obj, fieldSub.getName());

                        buf.append("\"").append(fieldSub.getName()).append("\"").append(":\"").append(value == null ? "" : value).append("\"");
                        if (i < size - 1) {
                            buf.append(",");
                        }
                    }
                    buf.append("}");
                    // 执行menuBean不为null的循环
                    buf.append(generateTreeJson(list, strPK, strFK, obj));
                    //buf.append(",\"children\":[");
                    //// 执行menuBean不为null的循环
                    //buf.append(generateTreeJson(list, strPK, strFK, obj));
                    //buf.append("]},");
                }
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) {

        Map<String, String> map = JsonUtils.json2Map("{\"abc\":\"123\"}");
        System.out.println(map.get("abc"));
    }

}
