package com.extend.demo.search.service.impl;

import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.repository.ThesisRepository;
import com.extend.demo.search.service.ThesisService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author snh
 * @version 1.0
 * @className ThesisServiceImpl
 * @description TODO
 * @date 2019/11/22 15:09
 **/
@Service
public class ThesisServiceImpl implements ThesisService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ThesisRepository thesisRepository;

    @Override
    public void save(Thesis thesis) {
        thesisRepository.save(thesis);
    }

    @Override
    public Iterable<Thesis> selectByKeyword(String keyword) {
        //单独构建
        //MultiMatchQueryBuilder multiMatchQueryBuilder=QueryBuilders.multiMatchQuery(keyword,"thesisTitle","author","organizations","thesisTag");
        SearchQuery searchQuery=new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.multiMatchQuery(keyword,"thesisTitle","author","organizations","thesisTag")).
                withHighlightFields(new HighlightBuilder.Field[]{
                        new HighlightBuilder.Field("thesisTitle").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("author").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("organizations").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("thesisTag").preTags("<font color='#dd4b39'>").postTags("</font>")}).build();
        Page<Thesis> page=thesisRepository.search(searchQuery);
        return page;
    }

    @Override
    public AggregatedPageImpl<Thesis> query(String keyword){
        Pageable pageable=PageRequest.of(0,10);
        SearchQuery searchQuery=new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.multiMatchQuery(keyword,"title","author","organizations","thesisTag")).
                withHighlightFields(new HighlightBuilder.Field[]{
                        new HighlightBuilder.Field("title").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("author").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("organizations").preTags("<font color='#dd4b39'>").postTags("</font>"),
                        new HighlightBuilder.Field("thesisTag").preTags("<font color='#dd4b39'>").postTags("</font>")}).build();
        searchQuery.setPageable(pageable);

        return (AggregatedPageImpl<Thesis>) elasticsearchTemplate.queryForPage(searchQuery, Thesis.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Thesis> chunk=new ArrayList<>();
                try {
                    for (SearchHit hit : response.getHits()) {
                        if(response.getHits().getHits().length <= 0){
                            return null;
                        }
                        Thesis thesis=new Thesis();
                        Map<String,HighlightField> map=hit.getHighlightFields();
                        Class thesisClass=thesis.getClass();
                        Set<String> keySet=map.keySet();
                        for (String key: keySet) {
                            HighlightField highlightField=map.get(key);
                            Field field=thesisClass.getDeclaredField(key);
                            field.setAccessible(true);
                            field.set(thesis,highlightField.fragments()[0].toString());
                        }
                        System.out.println(hit.getSourceAsString());
                        chunk.add(thesis);
                    }
                    if(chunk.size()>0){
                        return new AggregatedPageImpl<>((List<T>)chunk);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public Iterable<Thesis> getTopByClickRate() {
        return thesisRepository.queryAllByHitsOrderByHitsDesc();
    }

}
