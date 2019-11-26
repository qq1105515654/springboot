package com.extend.demo;

import com.extend.demo.message.HelloMessage;
import com.extend.demo.search.documents.Person;
import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.service.ThesisService;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private HelloMessage message;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void contextLoads() {

        for (int i = 0; i < 10; i++) {
            message.sendHello();
        }

    }

    @Test
    public void testFanoutMessage() {

        for (int i = 0; i < 5; i++) {
            message.fanoutSend();
        }
    }


    @Test
    public void testDirectMessage() {
        for (int i = 0; i < 5; i++) {
            message.directSend();
        }
    }


    @Test
    public void testElasticSearch() {

        Client client = elasticsearchTemplate.getClient();

        GetRequest request = new GetRequest();
        request.index("dongbawen");
        request.type("health");
        request.id("1");

        ActionFuture<GetResponse> response = client.get(request);

        GetResponse getResponse = response.actionGet();
        Map<String, Object> map = getResponse.getSource();


        System.out.println(map.get("first_name"));
    }

    @Autowired
    private ThesisService thesisService;

    @Test
    public void testQueryIndex() {
        /*GetQuery query=new GetQuery();
        query.setId("1");

        Person person=elasticsearchTemplate.queryForObject(query, Person.class);
        System.out.println(person);*/
        //QueryBuilders.matchAllQuery();
        /*NativeSearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchQuery("first_name" , "云"));
        List<Person> list = elasticsearchTemplate.queryForList(searchQuery, Person.class);
        System.out.println(list);*/
        AggregatedPageImpl<Thesis> page=thesisService.query("细胞");
        page.get().forEach(thesis -> {
            System.out.println(thesis);
        });


    }


    @Test
    public void testOptional() {

        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setFirstName(i + "");
            person.setLastName("a" + i);
            personList.add(person);
        }

        Optional<Person> optionalPerson = personList.stream().findFirst();
        System.out.println(optionalPerson);


    }

}
