/*
package com.extend.demo.search.documents;

import lombok.Data;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.List;

*/
/**
 * @ClassName Person
 * @Description TODO
 * @Author snh
 * @Date 2019/9/6 8:48
 * @Version 1.0
 **//*

@Document(indexName = "dongbawen" , type = "health")
@Data
public class Person implements Serializable {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private int age;

    private String about;

    private List<String> interests;

}
*/
