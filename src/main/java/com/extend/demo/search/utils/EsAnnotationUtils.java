package com.extend.demo.search.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author snh
 * @version 1.0
 * @className EsAnnotationUtils
 * @description TODO
 * @date 2019/11/27 17:48
 **/
public class EsAnnotationUtils {

    /**
     * 根据属性名获取指定注解的属性值
     * @param annotation 注解
     * @param property  属性名
     * @return
     */
    public static Object getAnnotationValue(Annotation annotation, String property){
        Object result=null;
        if(annotation!=null){
            InvocationHandler handler= Proxy.getInvocationHandler(annotation);
            Map map= (Map) getFieldValue(handler,"memberValues");
            if(map!=null){
                result=map.get(property);
            }
        }
        return result;
    }

    /**
     *  内部方法，获取注解的属性映射集合
     * @param object
     * @param property
     * @param <T>
     * @return
     */
    private static <T> Object getFieldValue(T object,String property){
        if(ObjectUtils.nonNull(object) && ObjectUtils.nonNull(property)){
            Class<T> currClass= (Class<T>) object.getClass();
            try {
                Field field=currClass.getDeclaredField(property);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
