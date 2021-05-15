package com.maple.web.dao;

import com.maple.web.mbg.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *后台用户 与角色管理(AdminRoleRelation) 自定义 Dao
 *@author zxzh
 *@date 2021/5/8
 */
public interface UmsAdminRoleRelationDao {
    /*
    * 获取用户的所有权限
    * */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
