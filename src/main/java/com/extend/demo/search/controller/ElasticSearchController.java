package com.extend.demo.search.controller;

import com.extend.demo.search.service.SearchService;
import com.extend.demo.search.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ElasticSearchController
 * @Description TODO
 * @Author snh
 * @Date 2019/9/6 14:41
 * @Version 1.0
 **/

@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public Object search(@RequestParam("keyword") String keyword){
        return searchService.search(keyword);
    }

    @GetMapping("/search/thesis")
    public Object searchThesis(@RequestParam("keyword")String keyword){
        return searchService.searchThesis(keyword);
    }

}
