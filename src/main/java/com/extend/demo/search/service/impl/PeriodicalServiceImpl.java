package com.extend.demo.search.service.impl;

import com.extend.demo.search.documents.Periodical;
import com.extend.demo.search.repository.PeriodicalRepository;
import com.extend.demo.search.service.PeriodicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author snh
 * @version 1.0
 * @className PeriodicalServiceImpl
 * @description TODO
 * @date 2019/11/27 14:29
 **/
@Service
public class PeriodicalServiceImpl implements PeriodicalService {

    @Autowired
    private PeriodicalRepository periodicalRepository;

    @Override
    public void insert(Periodical periodical) {
        periodicalRepository.save(periodical);
    }
}
