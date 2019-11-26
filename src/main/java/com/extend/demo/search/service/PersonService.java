package com.extend.demo.search.service;

import com.extend.demo.search.documents.Person;

import java.util.List;

/**
 * @ClassName PersonService
 * @Description TODO
 * @Author snh
 * @Date 2019/9/6 14:48
 * @Version 1.0
 **/
public interface PersonService {

    /**
     * @Author snh
     * @Description TODO 根据 lastName 查询 Person 信息
     * @Date 2019/9/6  14:49
     * @Param [lastName]
     * @Return java.util.List<com.extend.demo.search.documents.Person>  返回值
     **/
    Iterable<Person> findPersonByLastName(String lastName);


    Iterable<Person> findPersonByAbout(String about);

    Iterable<Person> search(String content);
}
