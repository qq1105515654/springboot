package com.extend.demo.search.controller;

import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.service.ThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author snh
 * @version 1.0
 * @className ThesisController
 * @description TODO
 * @date 2019/11/22 15:11
 **/
@RequestMapping("thesis")
@RestController
public class ThesisController {

    @Autowired
    private ThesisService thesisService;

    @PostMapping("/save")
    public Object save(@RequestBody Thesis thesis){
        thesisService.save(thesis);
        return "成功";
    }

    @GetMapping("/get")
    public Object get(@RequestParam("keyword") String keyword){
        return thesisService.selectByKeyword(keyword);
    }

    @GetMapping("/get/top")
    public Object get(){
        return thesisService.getTopByClickRate();
    }

}
