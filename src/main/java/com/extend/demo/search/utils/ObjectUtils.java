package com.extend.demo.search.utils;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author snh
 * @version 1.0
 * @className ObjectUtils
 * @description TODO 自定义对象工具类
 * @date 2019/11/11 11:33
 **/
public class ObjectUtils {

    public static final Predicate<Object> isNull= Objects::isNull;

    public static final Predicate<Object> nonNull=Objects::nonNull;

    public static boolean isNull(Object obj){
        return isNull.test(obj);
    }

    public static boolean nonNull(Object obj){
        return nonNull.test(obj);
    }
}
