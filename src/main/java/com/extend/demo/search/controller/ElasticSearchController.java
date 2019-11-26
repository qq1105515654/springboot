package com.extend.demo.search.controller;

import com.extend.demo.search.documents.Thesis;
import com.extend.demo.search.service.PersonService;
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
    private PersonService personService;



    @GetMapping(value = "/query/lastName/{lastName}")
    public Object queryByLastName(@PathVariable String lastName) {
        return personService.findPersonByLastName(null);
    }

    @GetMapping("/query/about/{about}")
    public Object queryByAbout(@PathVariable String about) {
        return personService.findPersonByAbout(about);
    }

}
