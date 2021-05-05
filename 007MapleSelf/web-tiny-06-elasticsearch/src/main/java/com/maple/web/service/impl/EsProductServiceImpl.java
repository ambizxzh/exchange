package com.maple.web.service.impl;

import com.maple.web.dao.EsProductDao;
import com.maple.web.nosql.elasticsearch.document.EsProduct;
import com.maple.web.nosql.elasticsearch.repository.EsProductRepository;
import com.maple.web.service.EsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *将数据库MySQL中的数据放入搜索数据库Elasticsearch中
 * esProductDao->esProductRepository
 *@author zxzh
 *@date 2021/5/1
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER= LoggerFactory.getLogger(EsProductServiceImpl.class);
    @Autowired
    private EsProductDao esProductDao;
    @Autowired
    private EsProductRepository esProductRepository;
    @Override
    public int importAll() {
        List<EsProduct> esProductList = esProductDao.getAllEsProductList(null);//MyBatis操作数据库

        //继承了ElasticsearchRepository的接口Repository提供Elasticsearch操作
        Iterable<EsProduct> esProductIterable=esProductRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator=esProductIterable.iterator();
        int result=0;
        while(iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result=null;
        List<EsProduct> esProductList = esProductDao.getAllEsProductList(id);//取出所有数据
        while(esProductList.size()>0){//逐行保存
            EsProduct esProduct = esProductList.get(0);
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){//输入不为空才删除
            List<EsProduct> esProductList = new ArrayList<>();
            for(Long id : ids){
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                esProductList.add(esProduct);
            }
            esProductRepository.deleteAll(esProductList);
        }
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
