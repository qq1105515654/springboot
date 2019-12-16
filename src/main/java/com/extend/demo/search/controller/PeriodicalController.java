package com.extend.demo.search.controller;

import com.extend.demo.search.documents.Periodical;
import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.service.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author snh
 * @version 1.0
 * @className PeriodicalController
 * @description TODO
 * @date 2019/11/27 14:33
 **/
@RequestMapping("periodical")
@RestController
public class PeriodicalController {

    @Autowired
    private PeriodicalService periodicalService;

    @PostMapping("/save")
    public Object save(@RequestBody Periodical periodical){
        periodicalService.insert(periodical);
        return "成功";
    }
}
