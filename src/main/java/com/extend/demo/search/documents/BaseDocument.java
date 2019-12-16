package com.extend.demo.search.documents;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author snh
 * @version 1.0
 * @className BaseQuery
 * @description TODO ES基础文档、如果需求不同请自行实现
 * @date 2019/11/27 10:34
 **/
@Data
public class BaseDocument implements Serializable {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 时间
     */
    @Field(type = FieldType.Text)
    private String time;

    /**
     * 点击数
     */
    private String hits;

    /**
     * 所属组织单位：
     *      博硕论文：学位授予单位
     *      医学期刊：主管单位
     *      中文古籍、医学图书：出版社
     *      报纸杂志、医学案例、其他：来源
     *      会议报告：主办单位
     */
    private String organizations;


    /**
     * 文档类型：
     *      1：博硕论文
     *      2：中医古籍
     *      3：医学期刊
     *      4：医学图书
     *      5：报纸杂志
     *      6：会议报告
     *      7：外国文献
     *      8：医学案例
     *      0：其他
     */
    private String documentType;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 文档内容 TODO 医学图书、中医古籍内容不保存在ES，请上传PDF到文件服务器，保存链接或者为空
     */
    private String content;
}
