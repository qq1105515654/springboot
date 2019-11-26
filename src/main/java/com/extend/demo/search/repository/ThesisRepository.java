package com.extend.demo.search.repository;

import com.extend.demo.search.documents.Thesis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Iterator;
import java.util.List;

/**
 * @author snh
 * @version 1.0
 * @className ThesisRepository
 * @description TODO
 * @date 2019/11/22 14:24
 **/
public interface ThesisRepository extends ElasticsearchRepository<Thesis,String> {


    /**
     * 根据点击量获取排名
     * @return
     */
    Iterable<Thesis> getTopByClickRate();

    /**
     * 根据时间排序
     * @return
     */
    Iterable<Thesis> getAllOrderByIssue();


}
