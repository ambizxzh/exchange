package com.maple.web.nosql.elasticsearch.repository;

import com.maple.web.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
/**
 *商品ElasticSearch操作类
 *@author zxzh
 *@date 2021/4/30
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct,Long> {//<仓库存的实体对象数据类型(对象名),对象的主键数据类型>
    /**
     * 搜索查询
     *
     * @param name              商品名称
     * @param subTitle          商品标题
     * @param keywords          商品关键字
     * @param page              分页信息
     * @return EsProduct        搜索的产品对象,自定义的document
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
