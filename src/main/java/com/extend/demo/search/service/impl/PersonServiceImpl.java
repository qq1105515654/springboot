/*
package com.extend.demo.search.service.impl;

import com.extend.demo.search.documents.Person;
import com.extend.demo.search.repository.PersonRepository;
import com.extend.demo.search.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.internal.SearchContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * @ClassName PersonServiceImpl
 * @Description TODO
 * @Author snh
 * @Date 2019/9/6 14:53
 * @Version 1.0
 **//*

@Service
public class PersonServiceImpl implements PersonService {


    @Autowired
    private PersonRepository personRepository;

    @Override
    public Iterable<Person> findPersonByLastName(String lastName) {

        if (StringUtils.isEmpty(lastName)) {
            return personRepository.findAll();
        }
        return personRepository.findPersonByLastName(lastName);
    }

    @Override
    public Iterable<Person> findPersonByAbout(String about) {
        return personRepository.findPersonByAbout(about);
    }

    @Override
    public Iterable<Person> search(String content) {


        return null;
    }


}
*/
