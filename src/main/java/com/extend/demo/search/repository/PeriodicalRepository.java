package com.extend.demo.search.repository;

import com.extend.demo.search.documents.Periodical;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author snh
 * @version 1.0
 * @className PeriodicalRepository
 * @description TODO
 * @date 2019/11/27 14:30
 **/
public interface PeriodicalRepository extends ElasticsearchRepository<Periodical,String> {

}
