package com.extend.demo.search.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author snh
 * @version 1.0
 * @className Periodical
 * @description TODO
 * @date 2019/11/27 10:47
 **/
@Data
@Document(indexName = "periodical")
public class Periodical extends BaseDocument {

    @Id
    private String periodicalId;

    /**
     * 期刊期数
     */
    private int termNum;

    private String issn;

    private String cn;

    /**
     * 期刊标签: "科研;医学;ccc"
     */
    private String periodicalTag;
}
