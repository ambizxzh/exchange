package com.maple.web.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.maple.web.common.api.CommonResult;
import com.maple.web.direct.DirectSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@Api(tags = "RabbitmqController", value = "RabbitMQ功能测试")
public class RabbitmqController {
    @Autowired
    private DirectSender directSender;

    @ApiOperation("路由模式")
    @GetMapping("/direct")
    public CommonResult directTest(){
        for(int i = 0; i < 10; i++){
            directSender.send(i);
            ThreadUtil.sleep(1000);
        }
        return CommonResult.success(null);
    }
}
