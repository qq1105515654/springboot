package com.extend.demo.search.utils;

import com.extend.demo.search.documents.AggregationSearch;
import com.extend.demo.search.documents.BaseDocument;
import com.extend.demo.search.documents.Periodical;
import com.extend.demo.search.documents.Thesis;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.elasticsearch.annotations.Document;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author snh
 * @version 1.0
 * @className ESResultUtils
 * @description TODO ES 返回结果操作工具类
 * @date 2019/11/27 17:13
 **/
public class EsResultUtils {

    private final static String PRE_TAGS="<font color='#dd4b39'>";
    private final static String POST_TAGS="</font>";


    /**
     * 将ES返回的结果映射为对应的对象中
     * @param clazz ES 所有索引的聚合类的反射对象
     * @param searchHits 查询击中的数据集合
     * @param <T>
     * @return
     */
    public static <T>T esCompositeQueryDataConvertToEntity(Class<T> clazz, SearchHits searchHits){
        List<Class> classes=getIndexClass(clazz);
        T t=buildObject(clazz);
        try {
            for (SearchHit searchHit : searchHits) {
                for (Class aClass : classes) {
                    if(isMatch(aClass,searchHit.getIndex())){
                        Map<String,Object> notHighlight=searchHit.getSourceAsMap();
                        Map<String, HighlightField> map=searchHit.getHighlightFields();
                        removeRepeatKey(notHighlight,map.keySet());
                        dataFill(t,aClass,map,notHighlight);
                    }
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException | InstantiationException e) {
           e.printStackTrace();
        }
        return t;
    }

    /**
     * 将ES返回结果映射为对应的对象的实体数据的列表
     * @param clazz
     * @param searchHits
     * @param <T>
     * @return
     */
    public static <T>List<T> esSingleQueryDataConvertToEntity(Class<T> clazz,SearchHits searchHits){
        List<T> list=new ArrayList<>();
        try {
            for (SearchHit searchHit : searchHits) {
                if(isMatch(clazz,searchHit.getIndex())){
                    Map<String,Object> notHighlight=searchHit.getSourceAsMap();
                    Map<String, HighlightField> map=searchHit.getHighlightFields();
                    removeRepeatKey(notHighlight,map.keySet());
                    entityDataFill(list,clazz,map,notHighlight);
                }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 在没有没有匹配到的高亮的键值中删除高亮的Key
     * @param notHighlightMap
     * @param keySet
     */
    public static void removeRepeatKey(Map<String,Object> notHighlightMap,Set<String> keySet){
        for (String s : keySet) {
            notHighlightMap.remove(s);
        }
    }

    /**
     * 给聚合对象中填充数据
     * @param t 聚合对象
     * @param clz   聚合对象中属性的泛型反射
     * @param map   从ES查询击中的高亮的属性值
     * @param <T>
     * @return
     */
    public static <T>T dataFill(T t,Class clz,Map<String,HighlightField> map,Map<String,Object> notHighlightMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        Class originClz=t.getClass();
        for (Field declaredField : originClz.getDeclaredFields()) {
            //判断聚合对象中属性对象的类型是否跟传入的泛型类型一致
            if(isType(declaredField,clz)){
                Method method=originClz.getMethod(parseGetMethod(declaredField.getName()));
                Object fieldObj=method.invoke(t);
                entityDataFill(fieldObj,clz,map,notHighlightMap);
            }
        }
        return t;
    }

    /**
     * 将集合中填充数据
     * @param fieldObj 聚合对象中的属性对象
     * @param entityClz 实体类反射
     * @param map
     */
    public static void entityDataFill(Object fieldObj,Class entityClz,Map<String,HighlightField> map,Map<String,Object> notHighlightMap) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        Method addMethod=fieldObj.getClass().getMethod("add",java.lang.Object.class);
        Object entity=entityClz.newInstance();
        Field[] fields=getAllField(entityClz);
        //为实体类填充数据
        for (String s : map.keySet()) {
            HighlightField highlightField=map.get(s);
            Field field=matchField(fields,replaceUnderline(s));
            field.setAccessible(true);
            field.set(entity,highlightField.fragments()[0].toString());
        }
        for (String s : notHighlightMap.keySet()) {
            Field field=matchField(fields,replaceUnderline(s));
            field.setAccessible(true);
            field.set(entity,notHighlightMap.get(s));
        }
        //调用方法为聚合对象中的属性对象赋值
        addMethod.invoke(fieldObj,entity);
    }

    /**
     * 构建对象
     * @param clz
     * @param <T>
     * @return
     */
    public static <T>T buildObject(Class<T> clz){
        Object obj=null;
        try {
            obj=clz.newInstance();
            for (Field declaredField : clz.getDeclaredFields()) {
                declaredField.setAccessible(true);
                declaredField.set(obj,new ArrayList<>());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 获取ES聚合对象中具体的文档类型的反射类集合
     * @param clazz
     * @return
     */
    public static List<Class> getIndexClass(Class clazz){
        List<Class> indexClasses= null;
        try {
            indexClasses = new ArrayList<>();
            for (Field declaredField : clazz.getDeclaredFields()) {
                ParameterizedType parameterizedType= (ParameterizedType) declaredField.getGenericType();
                ClassLoader classLoader=ClassLoader.getSystemClassLoader();
                indexClasses.add(classLoader.loadClass(parameterizedType.getActualTypeArguments()[0].getTypeName()));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return indexClasses;
    }

    /**
     * 判断集合属性中的泛型是否为传入的类型
     * @param field
     * @param clz
     * @return
     */
    public static boolean isType(Field field,Class clz){
        ParameterizedType parameterizedType= (ParameterizedType) field.getGenericType();
        Class type= (Class) parameterizedType.getActualTypeArguments()[0];
        /*未知原因，注释代码在单元测试中运行时 return true ,启动程序时 return false*/
        /*if(type.equals(clz)){
            return true;
        }*/
        if(type.getTypeName().equals(clz.getTypeName())){
            return true;
        }
        return false;
    }

    /**
     * 判断该属性类型是否为List
     * @param field
     * @return
     */
    public static boolean isList(Field field){
        ParameterizedType parameterizedType= (ParameterizedType) field.getGenericType();
        if(parameterizedType.getRawType().equals(List.class)){
            return true;
        }
        return false;
    }

    /**
     * 根据传入的聚合类属性值去创建该属性的集合泛型List对象
     * @param field 属性
     * @param clz   属性对应的类型的反射
     * @return
     */
    public static <T extends BaseDocument>List<T> getList(Field field,Class<T> clz){
        if(!isList(field)){
            return null;
        }
        ParameterizedType parameterizedType= (ParameterizedType) field.getGenericType();
        if(parameterizedType.getActualTypeArguments()[0].equals(clz)) {
            return new ArrayList<>();
        }
        return null;
    }

    /**
     * 获取文档实体类的Document注解的索引名
     * @param clz
     * @return
     */
    public static String getDocumentIndexName(Class clz){
        if(clz.isAnnotationPresent(Document.class)){
            Annotation annotation=clz.getAnnotation(Document.class);
            String indexName= (String) EsAnnotationUtils.getAnnotationValue(annotation,"indexName");
            return indexName ;
        }
        return null;
    }

    /**
     * 判断类中的Document注解中的indexName是否匹配
     * @param clz
     * @param indexName
     * @return
     */
    public static boolean isMatch(Class clz,String indexName){
        String documentIndexName=getDocumentIndexName(clz);
        if(StringUtils.isNotEmpty(indexName) && indexName.equals(documentIndexName)){
            return true;
        }
        return false;
    }

    /**
     * 解析属性名获取set方法
     * @param fieldName
     * @return
     */
    public static String parseSetMethod(String fieldName){
        return "set"+toUpperCase(fieldName);
    }

    /**
     * 获取GET 方法
     * @param fieldName
     * @return
     */
    public static String parseGetMethod(String fieldName){
        return "get"+toUpperCase(fieldName);
    }

    /**
     * 首字母转大写
     * @param fieldName
     * @return
     */
    public static String toUpperCase(String fieldName){
        char[] chars=fieldName.toCharArray();
        if(chars[0]>='a' || chars[0] >= 'z'){
            chars[0]= (char) (chars[0]-32);
        }
        return new String(chars);
    }

    /**
     * 去除属性名中的下划线并转成驼峰命名
     * @param fieldName
     * @return
     */
    public static String replaceUnderline(String fieldName){
        String[] splits=fieldName.split("_");
        String field=splits[0];
        for (int i = 1; i < splits.length; i++) {
            field+=toUpperCase(splits[i]);
        }
        return field;
    }

    /**
     * 获取属性所有的属性数组
     * @param clz
     * @return
     */
    public static String[] getFieldNames(Class clz){
        Set<String> fieldNameList=new HashSet<>();
        List<Class> clzes=getIndexClass(clz);
        for (Class clze : clzes) {
            for (Field declaredField : clze.getDeclaredFields()) {
                fieldNameList.add(declaredField.getName());
            }
            for (Field declaredField : clze.getSuperclass().getDeclaredFields()) {
                fieldNameList.add(declaredField.getName());
            }
        }
        String[] strs=new String[fieldNameList.size()];
        return fieldNameList.toArray(strs);
    }

    /**
     * 将单个类型的实体类中的所有属性名称获取出来，包括父类的属性
     * @param clz
     * @return
     */
    public static String[] getSingleFieldNames(Class clz){
        Set<String> fieldNameSet=new HashSet<>();
        for (Field declaredField : clz.getDeclaredFields()) {
            fieldNameSet.add(declaredField.getName());
        }
        for (Field declaredField : clz.getSuperclass().getDeclaredFields()) {
            fieldNameSet.add(declaredField.getName());
        }
        String[] strs=new String[fieldNameSet.size()];
        return fieldNameSet.toArray(strs);
    }

    /**
     * 获取所有的Field包括父类
     * @param clz
     * @return
     */
    public static Field[] getAllField(Class clz){
        List<Field> fieldList=new ArrayList<>();
        while (clz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clz.getDeclaredFields())));
            clz = clz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 使用属性名匹配属性对象
     * @param fields
     * @param fieldName
     * @return
     */
    public static Field matchField(Field[] fields,String fieldName){
        for (Field field : fields) {
            if(field.getName().equals(fieldName)){
                return field;
            }
        }
        return null;
    }

    /**
     * 构建高亮的属性
     * @param clz
     * @return
     */
    public static HighlightBuilder buildHighlightField(Class clz){
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        List<HighlightBuilder.Field> fieldList=highlightBuilder.fields();
        String[] fieldName=getFieldNames(clz);
        for (String s : fieldName) {
            fieldList.add(new HighlightBuilder.Field(s).preTags(PRE_TAGS).postTags(POST_TAGS));
        }
        return highlightBuilder;
    }

    /**
     * 构建单个实例的时候将该实例的所有属性都设置为ES击中的结果高亮显示
     * @param clz
     * @return
     */
    public static HighlightBuilder buildSingleHighlightField(Class clz){
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        List<HighlightBuilder.Field> fieldList=highlightBuilder.fields();
        String[] fieldNames=getSingleFieldNames(clz);
        for (String fieldName : fieldNames) {
            fieldList.add(new HighlightBuilder.Field(fieldName).preTags(PRE_TAGS).postTags(POST_TAGS));
        }
        return highlightBuilder;
    }


    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
       /* Thesis thesis=new Thesis();
        Field[] fields=getAllField(thesis.getClass());
        Field field=matchField(fields,"time");
        field.setAccessible(true);
        field.set(thesis,"张三");
        Method method=thesis.getClass().getMethod("getTime");
        String time= (String) method.invoke(thesis);
        System.out.println(time);*/

       AggregationSearch search=new AggregationSearch();
       System.out.println(isType(search.getClass().getDeclaredField("periodicals"), Periodical.class));
    }
}
