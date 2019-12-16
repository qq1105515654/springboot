package com.extend.demo.search.documents;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author snh
 * @version 1.0
 * @className Thises
 * @description TODO
 * @date 2019/11/22 11:38
 **/
@Data
@Document(indexName = "thesis")
@ToString
public class Thesis extends BaseDocument implements Serializable {

    /**
     * 论文Id
     */
    @Id
    private String thesisId;

    /**
     * 论文标签: "科研;医学;ccc"
     */
    private String thesisTag;

    /**
     * 作者：朱捷
     */
    @Field(type=FieldType.Keyword,analyzer = "ik_smart")
    private String author;

    /**
     * 导师名字
     */
    private String mentorName;

}
