package com.extend.demo.search.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author snh
 * @version 1.0
 * @className Thises
 * @description TODO
 * @date 2019/11/22 11:38
 **/
@Data
@Document(indexName = "rule",type = "thesis")
public class Thesis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 论文Id
     */
    @Id
    private String thesisId;

    /**
     * 论文标题：肿瘤驯化的肺上皮细胞促进肺转移前微环境的作用与免疫机制研究
     */
    @Field(type = FieldType.Keyword,analyzer = "ik_smart")
    private String thesisTitle;

    /**
     * 论文标签: "科研;医学;ccc"
     */
    @Field(type = FieldType.Object)
    private String thesisTag;

    /**
     * 发行：日期
     */
    @Field(type = FieldType.Text)
    private String issue;

    /**
     * 作者：朱捷
     */
    @Field(type=FieldType.Keyword,analyzer = "ik_smart")
    private String author;

    /**
     * 文档类型：1.博硕论文，2.中医古籍，3.医学图书，4.报纸杂志，5.医学案例，6.其他
     * 文档类型：1
     */
    private int type;

    /**
     * 组织机构：北京大学
     */
    private String organizations;

    /**
     * 点击量：100010
     */
    private int clickRate;

}
