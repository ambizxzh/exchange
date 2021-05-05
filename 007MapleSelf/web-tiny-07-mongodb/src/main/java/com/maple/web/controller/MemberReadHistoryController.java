package com.maple.web.controller;

import com.maple.web.common.api.CommonResult;
import com.maple.web.nosql.mongodb.document.MemberReadHistory;
import com.maple.web.service.MemberReadHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/readHistory")
@Api(tags = "MemberReadHistoryController", description = "会员商品浏览记录管理")
public class MemberReadHistoryController {
    @Autowired
    private MemberReadHistoryService memberReadHistoryService;

    @PostMapping("/create")
    @ApiOperation("创建浏览记录")
    private CommonResult create(@RequestBody MemberReadHistory memberReadHistory){
        int count = memberReadHistoryService.create(memberReadHistory);
        if(count > 0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @PostMapping("/delete")
    @ApiOperation("删除浏览记录")
    private CommonResult delete(@RequestParam("ids") List<String> ids){
        int count = memberReadHistoryService.delete(ids);
        if(count > 0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @GetMapping("/list")
    @ApiOperation("列出浏览记录")
    private CommonResult<List<MemberReadHistory>> list(Long memberId){
        return CommonResult.success(memberReadHistoryService.list(memberId));
    }
}
