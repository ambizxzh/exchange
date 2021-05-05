流程参考:http://www.macrozheng.com/#/architect/mall_arch_01

## mysql数据库环境搭建

-   下载并安装mysql5.7版本，下载地址：https://dev.mysql.com/downloads/installer/
-   设置数据库帐号密码：root root
-   下载并安装客户端连接工具Navicat,下载地址：http://www.formysql.com/xiazai.html
-   创建数据库mall
-   导入mall的数据库脚本，脚本地址：https://github.com/macrozheng/mall-learning/blob/master/document/sql/mall.sql

## 项目使用框架介绍

### SpringBoot

>   SpringBoot可以让你快速构建基于Spring的Web应用程序，内置多种Web容器(如Tomcat)，通过启动入口程序的main函数即可运行。

### PagerHelper

>   MyBatis分页插件，简单的几行代码就能实现分页，在与SpringBoot整合时，只要整合了PagerHelper就自动整合了MyBatis。

```java
PageHelper.startPage(pageNum, pageSize);
//之后进行查询操作将自动进行分页
List<PmsBrand> brandList = brandMapper.selectByExample(new PmsBrandExample());
//通过构造PageInfo对象获取分页信息，如当前页码，总页数，总条数
PageInfo<PmsBrand> pageInfo = new PageInfo<PmsBrand>(list);Copy to clipboardErrorCopied
```

### Druid

>   alibaba开源的数据库连接池，号称Java语言中最好的数据库连接池。

### Mybatis generator

>   MyBatis的代码生成器，可以根据数据库生成model、mapper.xml、mapper接口和Example，通常情况下的单表查询不用再手写mapper。

MyBatis plus 可以自动生成 Entity Controller Service Mapper

## 1.新建工程

新建默认的Spring Initializr工程的Maven工程

![image-20210402145738303](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210402145738303.png)

![image-20210402150209663](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210402150209663.png)

![image-20210402150326672](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210402150326672.png)![image-20210402150539906](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210402150539906.png)



## 2.修改SpringBoot配置文件

将src/main/resources/application.properties的后缀名改成yml,即src/main/resources/application.yml.

添加数据源配置和MyBatis的mapper.xml的路径配置.

以下是模板,基本需要更改的是url中的数据库名称(以下代码中的mall)、username和root

```yml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mall?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root

mybatis:
  mapper-locations:
    - classpath:mapper/*.xml
    - classpath*:com/**/mapper/*.xml
```

![image-20210402151834876](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210402151834876.png)



## 3.使用pom.xml添加依赖

给工程文件夹下中的Maven配置文件pom.xml中添加依赖.

以下是用到的依赖,需要添加在`<dependencies> </dependencies>`标签里,标签里其他的类似的依赖(用处类似的)删掉,以免产生竞争

```xml

        <!--SpringBoot通用依赖模块-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--MyBatis分页插件-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.10</version>
        </dependency>
        <!--集成druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!-- MyBatis 生成器 -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.3</version>
        </dependency>
        <!--Mysql数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.15</version>
        </dependency>
        <!--        api 注解-->
        <dependency>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-annotations</artifactId>
            <version>1.6.0</version>
        </dependency>
        <!--        lombok简化,使用注解自动生成set、get和构造器等,使代码简洁-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
```

## 4.新建众多包,给项目构建结构

包的结构如图(图来源于网络http://www.macrozheng.com/#/architect/mall_arch_01)



| 包名或者文件                        | 用处                                                         | Bean的层次             | 类别                      | 注解(只有部分实体类有注解,接口类无注解) |
| ----------------------------------- | ------------------------------------------------------------ | ---------------------- | ------------------------- | --------------------------------------- |
| **src/main/java/com/maple/web包下** |                                                              |                        |                           |                                         |
| common                              | 存放通用类,如工具类和通用返回结果                            |                        |                           |                                         |
| component                           | 存放组件                                                     | 不明确层次的组件放在这 | 实体类                    | @Component                              |
| config                              | 存放Java配置,如跨域处理cors                                  |                        | 实体类                    | @Configuration                          |
| controller                          | 存放控制器,设定请求网址,与前端交互.用于接受用户请求并调用 Service 层返回数据给前端页面 | 控制层                 | 实体类                    | @Controller                             |
| dao                                 | **D**ata **A**ccess **O**bject,数据访问对象.存放自定义的mapper接口.持久层,定义数据库的**操作方法**,使用MyBatis框架.MyBatis Plus引入后,这里只有少部分的操作方法,大部分的操作方法在mbg.mapper下面,与model实体成对存在 | 持久层                 | 接口类                    | 无                                      |
| dto                                 | **D**ata **T**ransfer **O**bject,数据传输对象.存放自定义的传输对象,如请求参数和返回结果 |                        | 实体类                    |                                         |
| mbg                                 | **M**y**B**atis **G**enerator,MyBatis 生成器 包.存放mybatis相关代码,其中的mapper和model可以由Generator指定自动生成.**用来连接mysql数据库** |                        |                           |                                         |
| mbg.mapper                          | **面向数据操作**的自定义mybatis接口                          |                        | 接口类                    |                                         |
| mbg.model                           | mbg生成的代码,数据库的表格实体、复合主键实体(实现了Serializable接口) |                        | 实体类                    |                                         |
| CommentGenerator类                  | 自定义的注解的生成器                                         |                        | 实体类                    | 无                                      |
| Generator 类                        | mbg代码生成器,运行即可生成代码,使用数据流打开generatorConfig.xml |                        | 实体类                    | 无                                      |
| service                             | 存放服务类,需要用到持久层中的 Dao层和mbg.mapper层的接口.     | 服务层                 | 接口类                    | 无                                      |
| service.impl                        | 存放服务类的实现类.将mbg层连接的数据库的数据或redis的数据(没有在项目文件包里显现出来而是直接注入的)**拿过来进行封装**,以便给controller层使用(再封装) | 服务层                 | 实体类(实现service的接口) | @Service                                |
| **src\main\resources包下**          |                                                              |                        |                           |                                         |
| mapper                              | 存放自定义mapper.xml文件.是mbg.mapper和dao操作接口对应的xml文件,即MyBatis会话的数据源,是一些MySQL语句 |                        |                           |                                         |
| application.yml                     | 技术栈的数据源和路径定位,web服务打开后,这些源会一直连接不中断 |                        |                           |                                         |
| generatorConfig.xml                 | 生成器的配置文件,规定生成的文件的路径等,由Generator 类使用数据流打开 |                        |                           |                                         |
| generatorConnection.properties      | 生成器的数据库源,与yml文件不同,这个只在使用生成器生成文件时会使用,即 是 短连接 |                        |                           |                                         |



![image-20210410174157385](C:\Users\ZXZH\AppData\Roaming\Typora\typora-user-images\image-20210410174157385.png)

![img](http://www.macrozheng.com/images/arch_screen_02.png)

### 将一个类声明为Spring的 bean 的注解有哪些?(E:\001 java\JavaGuide\docs\system-design\framework\spring:Spring常见问题总结.md)

我们一般使用 `@Autowired` 注解自动装配 bean，要想把类标识成可用于 `@Autowired` 注解自动装配的 bean 的类,采用以下注解可实现：

- `@Component` ：通用的注解，可标注任意类为 `Spring` 组件。如果一个Bean不知道属于哪个层，可以使用`@Component` 注解标注。
- `@Repository` : 对应持久层即 Dao 层，主要用于数据库相关操作。Dao接口处理MySQL数据库,Repository接口处理各种其他的数据库
- `@Service` : 对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao层。
- `@Controller` : 对应 Spring MVC 控制层，主要用于接受用户请求并调用 Service 层返回数据给前端页面。

## 5.提供mapper自动生成的模板

在resouce文件夹下新建生成器配置文件 generatorConfig.xml, 

用来控制src/main/java/com.xxx.xxx 下的mbg包生成代码的配置文件,执行Generator类的main方法,将根据模板generatorConfig.xml生成mapper文件,简化了单表查询这类语句的写入操作:

配置数据库连接，生成mbg下的model、mapper接口及mapper.xml的路径。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <properties resource="generator.properties"/>
    <context id="MySqlContext" targetRuntime="MyBatis3" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <property name="javaFileEncoding" value="UTF-8"/>
        <!-- 为模型生成序列化方法-->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>
        <!-- 为生成的Java模型创建一个toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin"/>
        <!--可以自定义生成model的代码注释-->
        <commentGenerator type="com.macro.mall.tiny.mbg.CommentGenerator">
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="true"/>
            <property name="suppressDate" value="true"/>
            <property name="addRemarkComments" value="true"/>
        </commentGenerator>
        <!--配置数据库连接-->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.connectionURL}"
                        userId="${jdbc.userId}"
                        password="${jdbc.password}">
            <!--解决mysql驱动升级到8.0后不生成指定数据库代码的问题-->
            <property name="nullCatalogMeansCurrent" value="true" />
        </jdbcConnection>
        <!--指定生成model的路径-->
        <javaModelGenerator targetPackage="com.macro.mall.tiny.mbg.model" targetProject="mall-tiny-01\src\main\java"/>
        <!--指定生成mapper.xml的路径-->
        <sqlMapGenerator targetPackage="com.macro.mall.tiny.mbg.mapper" targetProject="mall-tiny-01\src\main\resources"/>
        <!--指定生成mapper接口的的路径-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.macro.mall.tiny.mbg.mapper"
                             targetProject="mall-tiny-01\src\main\java"/>
        <!--生成全部表tableName设为%-->
        <table tableName="pms_brand">
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```



## 6.新建生成器类

在mbg下新建Generator类,作为mbg代码生成器,运行便可生成代码

```java
package com.maple.web.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于生产MBG的代码
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<String>();
        //当生成的代码重复时，覆盖原代码
        boolean overwrite = true;
        //读取我们的 MBG 配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
```

## ~~7.添加MyBatis配置文件~~

用来配置 使用Generator动态生成的mapper接口  的路径

```java
package com.maple.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("com.maple.web.mbg.mapper")//mapper接口的路径设置
public class MyBatisConfig {
}
```

## 8.运行Generator的主函数main

若是多次运行，请将生成在src/main/resources下的com.maple.web.mbg.mapper删除,以免重复的文字出现在xml文件中,造成报错

java.lang.IllegalArgumentException: ResultMaps collection already contains value

若报错:

```
javax.net.ssl.SSLException
MESSAGE: closing inbound before receiving peer's close_notify
```

则将MySql的xxx.properties的连接文件的

 jdbc.connectionURL即数据库连接地址添加一个 useSSL=false, 注意这个要和其他的项使用 "&"分隔

```properties
jdbc.connectionURL=jdbc:mysql://localhost:3306/maple?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
```



若报错:

```
The specified target project directory mall-tiny-01\src\main\resources does not exist
```

则将xml的targetProject改为

```xml
targetProject="src/main/java"
```

即不用把项目的路径写进来

## 9.添加Service接口

添加添加、修改、删除及分页查询接口

```java

/**
 * PmsBrandService
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);//分页查询

    PmsBrand getBrand(Long id);
}
```

## 10.实现Service接口

实现service接口,注意一定要给该实现类添加@Service注解,接口中的方法的输入参数来自于对应的Mapper接口(即注入Mapper接口)

```java

/**
 * PmsBrandService实现类
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper brandMapper;//注入Mapper的接口

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {//分页查询
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
```

## 11.在Controller中使用service中的接口与前端对接

controller的各个方法的**输入值**,由自动注入的service接口得到

controller对**输入值**进行**封装**,就得到**返回值**

controller各个方法的**返回值**是前端请求的数据格式类型

实现PmsBrand表中的添加、修改、删除及分页查询接口,即实现PmsBrandMapper的接口。

```java

/**
 * 品牌管理Controller
 */
@Controller
@RequestMapping("/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService demoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList() {
        return CommonResult.success(demoService.listAllBrand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = demoService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.debug("createBrand success:{}", pmsBrand);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("createBrand failed:{}", pmsBrand);
        }
        return commonResult;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto, BindingResult result) {
        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.debug("updateBrand success:{}", pmsBrandDto);
        } else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
        }
        return commonResult;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id) {
        int count = demoService.deleteBrand(id);
        if (count == 1) {
            LOGGER.debug("deleteBrand success :id={}", id);
            return CommonResult.success(null);
        } else {
            LOGGER.debug("deleteBrand failed :id={}", id);
            return CommonResult.failed("操作失败");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = demoService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
        return CommonResult.success(demoService.getBrand(id));
    }
}
```

