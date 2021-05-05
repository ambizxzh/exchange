package com.maple.web.service;

import com.maple.web.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * 会员浏览记录管理Service
 */
public interface MemberReadHistoryService {
    /*
    * 生成会员的浏览记录
    * */
    int create(MemberReadHistory memberReadHistory);

    /*
    * 根据id组批量删除记录
    * */
    int delete(List<String> ids);

    /*
    * 根据用户Id获取用户的浏览记录
    * */
    List<MemberReadHistory> list(Long memberId);
}
