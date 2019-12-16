package com.extend.demo.search.service;

import com.extend.demo.search.documents.Thesis;

import java.util.List;

/**
 * @author snh
 * @version 1.0
 * @className SearchService
 * @description TODO 查询接口
 * @date 2019/11/27 16:37
 **/
public interface SearchService {
    /**
     * 查询
     * @param keyword
     * @return
     */
    Object search(String keyword);

    /**
     * 查询博硕论文
     * @param keyword
     * @return
     */
    List<Thesis> searchThesis(String keyword);
}
