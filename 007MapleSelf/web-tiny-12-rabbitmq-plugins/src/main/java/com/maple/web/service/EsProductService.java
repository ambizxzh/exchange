package com.maple.web.service;

import com.maple.web.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *商品搜索管理Service
 *@author zxzh
 *@date 2021/4/30
 */
public interface EsProductService {
    /*
    * 从数据库中导入所有商品到ElasticSearch
    * */
    int importAll();
    /*
    * 根据id删除商品搜索
    * */
    void delete(Long id);
    /*
    * 根据id创建商品搜索
    * */
    EsProduct create(Long id);
    /*
    * 根据一组id批量删除商品搜索
    * */
    void delete(List<Long> ids);
    /*
    * 根据关键字  按页码 搜索名称或者副标题
    * */
    Page<EsProduct> search(String keyword,Integer pageNum, Integer pageSize);
}
