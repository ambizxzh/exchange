package com.maple.web.nosql.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *搜索的商品信息
 *@author zxzh
 *@date 2021/4/30
 */
@Document(indexName = "pms",shards = 1,replicas = 1)//
// shard和replicas用于分库分表、主从复制和读写分离,
// shards表示库数(分片),replicas表示每个库中的表的个数数(副本数)
@Setter
@Getter
public class EsProduct implements Serializable {
    private static final long serialVersionID=-1L;//序列化I/O流 的 ID号
    @Id
    private long id;
    @Field(type = FieldType.Keyword)//按照单个文字进行拆分词语:因为单个单词、中文单个文字
    private String productSn;
    private long brandId;
    @Field(type = FieldType.Keyword)
    private String brandName;
    private Long productCategoryId;
    @Field(type = FieldType.Keyword)
    private String productCategoryName;
    private String pic;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)//需要对中文文本进行按照语境拆分词语(分词),使用中文分词器IKAnalyzer
    private String name;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String subTitle;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String keywords;
    private BigDecimal price;
    private Integer sale;
    private Integer newStatus;
    private Integer recommendStatus;
    private Integer stock;
    private Integer promotionType;
    private Integer sort;
    @Field(type =FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;
}
