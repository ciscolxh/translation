package com.luokes.utils;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Type;

public class JsonUtils {

    /**
     * @param object object
     * @param <T>    T
     * @return 将对象准换为json字符串
     */
    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * @param json json
     * @param clz  clz
     * @param <T>  T
     * @return 将json字符串转换为对象
     */
    public static <T> T deserialize(String json, Class<T> clz) {
        return JSON.parseObject(json,clz);
    }


    /**
     * @param json json
     * @param type type
     * @param <T>  T
     * @return 将json字符串转换为对象
     */
    public static <T> T deserialize(String json, Type type){
        return JSON.parseObject(json,type);
    }


}
