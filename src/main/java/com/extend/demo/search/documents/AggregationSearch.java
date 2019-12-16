package com.extend.demo.search.documents;

import lombok.Data;

import java.util.List;

/**
 * @author snh
 * @version 1.0
 * @className AggregationSearch
 * @description TODO
 * @date 2019/11/27 17:25
 **/
@Data
public class AggregationSearch {

    private List<Periodical> periodicals;

    private List<Thesis> theses;
}
