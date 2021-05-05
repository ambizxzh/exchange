package com.maple.web.service.impl;

import com.maple.web.common.api.CommonResult;
import com.maple.web.component.CancelOrderSender;
import com.maple.web.dto.OrderParam;
import com.maple.web.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *前台的订单管理服务
 *@author zxzh
 *@date 2021/5/2
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);
    @Autowired
    private CancelOrderSender cancelOrderSender;
    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //todo 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generatorOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);

        return CommonResult.success(null, "下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        //todo 取消下单操作
        LOGGER.info("process cancelOrder orderId:{}", orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId){
        //延迟取消订单的时长
        long delayTimes = 30 * 1000;//30s
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
