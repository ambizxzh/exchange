package com.maple.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.maple.web.dao"})//"com.maple.web.mbg.mapper"可加可不加,
// 添加"com.maple.web.dao"是因为要扫描dao中的mapper,因为dao中也有与xxx.xml数据
// 库mapper文件(这些xml是根据数据库的表格生成的)相关联的类,如UmsAdminRoleRelationDao
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
