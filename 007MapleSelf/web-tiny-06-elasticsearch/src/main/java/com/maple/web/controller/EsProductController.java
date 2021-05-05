package com.maple.web.controller;

import com.maple.web.common.api.CommonPage;
import com.maple.web.common.api.CommonResult;
import com.maple.web.nosql.elasticsearch.document.EsProduct;
import com.maple.web.service.EsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/esProduct")
@Api(tags = "EsProductController", value = "商品的搜索 管理Controller")//description = "商品的搜索 管理Controller",
public class EsProductController {
    @Autowired
    private EsProductService esProductService;

    @ApiOperation(value = "导入数据库中的所有商品到搜索ES中")
    @PostMapping("/importAll")
    private CommonResult<Integer> importAll(){
        int result = esProductService.importAll();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "根据id删除对应的商品搜索")
    @GetMapping("/delete/{id}")
    private CommonResult<Object> delete(@PathVariable("id") Long id){
        esProductService.delete(id);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据id创建商品搜索")
    @PostMapping("/create/{id}")
    private CommonResult<EsProduct> create(@PathVariable("id") Long id){
        EsProduct esProduct = esProductService.create(id);
        if(esProduct != null){
            return CommonResult.success(esProduct);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "根据id组批量删除商品搜索")
    @PostMapping("/delete/batch")
    private CommonResult<Object> deleteAll(@RequestParam("ids") List<Long> ids){
        esProductService.delete(ids);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "简单搜索:根据关键字搜索 名称或副标题, 按指定页码对数据进行分隔")
    @PostMapping("/search/simple")
    private CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false, defaultValue = "0") int pageNum,
                                                       @RequestParam(required = false, defaultValue = "5") int pageSize){
        Page<EsProduct> esProductPage= esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
