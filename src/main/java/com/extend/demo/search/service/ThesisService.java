package com.extend.demo.search.service;

import com.extend.demo.search.documents.Thesis;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author snh
 * @version 1.0
 * @className ThesisService
 * @description TODO
 * @date 2019/11/22 15:07
 **/
public interface ThesisService {

    /**
     * 插入知识
     * @param thesis
     */
    void save(Thesis thesis);

    /**
     * 模糊查询论文，以标题、作者、标签、机构、时间
     * @param keyword
     * @return
     */
    Iterable<Thesis> selectByKeyword(String keyword);

    /**
     * 根据点击量获取排名
     * @return
     */
    Iterable<Thesis> getTopByClickRate();

    /**
     * 根据keyword 查询
     * @param keyword
     * @return
     */
    AggregatedPageImpl<Thesis> query(String keyword);

}
