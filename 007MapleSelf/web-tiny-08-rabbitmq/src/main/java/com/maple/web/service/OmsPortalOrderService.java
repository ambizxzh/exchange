package com.maple.web.service;

import com.maple.web.common.api.CommonResult;
import com.maple.web.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 *前台的订单管理服务
 *@author zxzh
 *@date 2021/5/2
 */
public interface OmsPortalOrderService {
    /*
    * 根据提交的信息生成订单
    * */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /*
    * 取消订单
    * */
    @Transactional
    void cancelOrder(Long orderId);
}
