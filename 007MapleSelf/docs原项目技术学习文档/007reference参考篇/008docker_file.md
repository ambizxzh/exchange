# 使用Dockerfile为SpringBoot应用构建Docker镜像

> 上次写过一篇[使用Maven插件构建Docker镜像](https://mp.weixin.qq.com/s/q2KDzHbPkf3Q0EY8qYjYgw) ，讲述了通过docker-maven-plugin来构建docker镜像的方式，此种方式需要依赖自建的Registry镜像仓库。本文将讲述另一种方式，使用Dockerfile来构建docker镜像，此种方式不需要依赖自建的镜像仓库，只需要应用的jar包和一个Dockerfile文件即可。

## Dockerfile常用指令

### ADD
用于复制文件，格式：
```
ADD <src> <dest>
```
示例：
```shell
# 将当前目录下的mall-tiny-docker-file.jar包复制到docker容器的/目录下
ADD mall-tiny-docker-file.jar /mall-tiny-docker-file.jar
```

### ENTRYPOINT(entry point, 切入点,入口点.类似与开机自启)
指定docker容器启动时执行的命令，格式：
```
ENTRYPOINT ["executable", "param1","param2"...]
```
示例：
```shell
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/mall-tiny-docker-file.jar"]
```

### ENV
用于设置环境变量，格式：
```
ENV <key> <value>
```
示例：
```shell
# mysql运行时设置root密码
ENV MYSQL_ROOT_PASSWORD root
```

### EXPOSE
声明需要暴露的端口(只声明不会打开端口)，格式：
```
EXPOSE <port1> <port2> ...
```
示例：
```shell
# 声明服务运行在8080端口
EXPOSE 8080
```

### FROM
指定所需依赖的基础镜像，格式：
```
FROM <image>:<tag>
```
示例：
```shell
# 该镜像需要依赖的java8的镜像
FROM java:8
```

### MAINTAINER
指定维护者的名字，格式：
```
MAINTAINER <name>
```
示例：
```shell
MAINTAINER macrozheng
```

### RUN
在容器构建过程中执行的命令，我们可以用该命令自定义容器的行为，比如安装一些软件，创建一些文件等，格式：
```
RUN <command>
RUN ["executable", "param1","param2"...]
```
示例：
```shell
# 在容器构建过程中需要在/目录下创建一个mall-tiny-docker-file.jar文件
RUN bash -c 'touch /mall-tiny-docker-file.jar'
```

## 使用Dockerfile构建SpringBoot应用镜像

https://blog.csdn.net/metgo/article/details/107258220

### 编写Dockerfile文件

```shell
# 该镜像需要依赖的基础镜像.拉取一个jdk1.8的docker image
FROM java:8
# 将当前目录下的jar包复制到docker容器的/目录下
ADD mall-tiny-docker-file-0.0.1-SNAPSHOT.jar /mall-tiny-docker-file.jar
# 运行过程中创建一个mall-tiny-docker-file.jar文件
RUN bash -c 'touch /mall-tiny-docker-file.jar'
# 声明服务运行在8080端口.该容器暴露的端口是多少，就是jar在容器中以多少端口运行
EXPOSE 8080
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/mall-tiny-docker-file.jar"]
# 指定维护者的名字
MAINTAINER macrozheng
```

### 修改application.yml，将localhost改为db

> 可以把docker中的容器看作独立的虚拟机，mall-tiny-docker访问localhost自然会访问不到mysql，docker容器之间可以通过指定好的服务名称db进行访问，至于db这个名称可以在运行mall-tiny-docker容器的时候指定(该文章最后一步, 其实直接使用localhost会把这个localhost当成服务名称, 与上面的db作用一致, 但是为了避免与本地IP地址混淆,我们一般会将localhost这个字符名称改掉如改成上面的db)。其实出于对各个容器的需要, 需要很多链接, 所以命名需要与使用的技术栈相关, 比如需要链接mysql、redis、elasticsearch、mongo这些技术栈都要命名比较有辨识度且相关的名字

```yml
spring:
  datasource:
    url: jdbc:mysql://db:3306/mall?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

### 使用maven打包应用

在IDEA中双击package命令进行打包:  
![](../images/refer_screen_91.png)  
打包成功后展示：
```shell
[INFO] --- spring-boot-maven-plugin:2.1.3.RELEASE:repackage (repackage) @ mall-tiny-docker-file ---
[INFO] Replacing main artifact with repackaged archive
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 30.749 s
[INFO] Finished at: 2019-06-16T14:11:07+08:00
[INFO] Final Memory: 43M/306M
[INFO] ------------------------------------------------------------------------
```
将应用jar包及Dockerfile文件上传到linux服务器(就是将这两个文件复制到自己指定的文件夹下,我放在了/home/zxzh/git_zxzh/007MapleSelf/web-tiny-14-deploy-notProject   下)：
![](../images/refer_screen_92.png)
![](../images/refer_screen_95.png)
### 在Linux上构建docker镜像(制作镜像)
在**Dockerfile所在目录**执行以下命令：
```shell
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
# 注意最后的 .  表示 Dockerfile 文件在当前目录下
docker build -t mall-tiny/mall-tiny-docker-file:0.0.1-SNAPSHOT .
```

-t：给生成的镜像命名，本文命名为mall-tiny/mall-tiny-docker-file:0.0.1-SNAPSHOT  也可命名docker-test之类的非路径名
命令最后的 “**.**”：指定镜像构建过程中的上下文环境的目录(不能忘)

输出如下信息：

```shell
Sending build context to Docker daemon  36.37MB
Step 1/5 : FROM java:8
 ---> d23bdf5b1b1b
Step 2/5 : ADD mall-tiny-docker-file-0.0.1-SNAPSHOT.jar /mall-tiny-docker-file.jar
 ---> c920c9e9d045
Step 3/5 : RUN bash -c 'touch /mall-tiny-docker-file.jar'
 ---> Running in 55506f517f19
Removing intermediate container 55506f517f19
 ---> 0727eded66dc
Step 4/5 : EXPOSE 8080
 ---> Running in d67a5f50aa7d
Removing intermediate container d67a5f50aa7d
 ---> 1b8b4506eb2d
Step 5/5 : ENTRYPOINT ["java", "-jar","/mall-tiny-docker-file.jar"]
 ---> Running in 0c5bf61a0032
Removing intermediate container 0c5bf61a0032
 ---> c3614dad21b7
Successfully built c3614dad21b7
Successfully tagged mall-tiny/mall-tiny-docker-file:0.0.1-SNAPSHOT
```
查看docker镜像：
![](../images/refer_screen_93.png)
### 运行mysql服务并设置

#### 1.使用docker命令启动：
```shell
docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root  \
-d mysql:5.7
```
#### 2.进入运行mysql的docker容器：
```shell
docker exec -it mysql /bin/bash
```
#### 3.使用mysql命令打开客户端：
```shell
mysql -uroot -proot --default-character-set=utf8
```
#### 4.修改root帐号的权限，使得任何ip都能访问：
```sql
//mysql8.0之前的语句
grant all privileges on *.* to 'reader' @'%' identified by '123456';
//mysql8.0之后的语句
create user reader@'%' identified by '123456'; grant all privileges on *.* to reader@'%' with grant option;
```
#### 5.创建mall数据库：
```sql
create database mall character set utf8
```
#### 6.将mall.sql文件拷贝到mysql容器的/目录下：
```shell
docker cp /mydata/mall.sql mysql:/
```
#### 7.将sql文件导入到数据库：
```shell
use mall;
source /mall.sql;
```

### 运行mall-tiny-docker-file应用

```shell
docker run -p 8080:8080 --name mall-tiny-docker-file \
--link mysql:db \
-v /etc/localtime:/etc/localtime \
-v /mydata/app/mall-tiny-docker-file/logs:/var/logs \
-d mall-tiny/mall-tiny-docker-file:0.0.1-SNAPSHOT
```
--link mysql:db  表示将容器中的数据库的IP(在项目的application.yml中mysql的IP已重命名为db)链接到该docker中的另一个容器mysql(已运行)

访问接口文档地址http://192.168.3.101:8080/swagger-ui.html：
![](../images/refer_screen_94.png)

## 项目源码地址

[https://github.com/macrozheng/mall-learning/tree/master/mall-tiny-docker-file](https://github.com/macrozheng/mall-learning/tree/master/mall-tiny-docker-file)


