package com.extend.demo.search.service.impl;

import com.extend.demo.search.documents.AggregationSearch;
import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.service.SearchService;
import com.extend.demo.search.utils.EsResultUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author snh
 * @version 1.0
 * @className SearchServiceImpl
 * @description TODO
 * @date 2019/11/27 16:38
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Object search(String keyword) {
        HighlightBuilder highlightBuilder=EsResultUtils.buildHighlightField(AggregationSearch.class);
        Client client=elasticsearchTemplate.getClient();
        SearchResponse response=client.prepareSearch("thesis","periodical")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery(keyword))
                .highlighter(highlightBuilder)
                //.setPostFilter() filter
                .setFrom(0).setSize(10).setExplain(true).get();
        AggregationSearch search=EsResultUtils.esCompositeQueryDataConvertToEntity(AggregationSearch.class,response.getHits());
        return search;
    }

    @Override
    public List<Thesis> searchThesis(String keyword) {
        HighlightBuilder highlightBuilder=EsResultUtils.buildSingleHighlightField(Thesis.class);
        SearchResponse response=elasticsearchTemplate.getClient().prepareSearch("thesis")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery(keyword))
                .highlighter(highlightBuilder)
                //.setPostFilter() filter
                .setFrom(0).setSize(10).setExplain(true).get();
        return EsResultUtils.esSingleQueryDataConvertToEntity(Thesis.class,response.getHits());
    }
}
