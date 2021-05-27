# 该镜像需要依赖的基础镜像 java8
FROM java:8
# 将当前目录下的jar包复制到docker容器的/目录下
ADD web-tiny-02-swaggerui-0.0.1-SNAPSHOT.jar /web-tiny-02-swaggerui.jar
# 在容器的/目录下创建一个web-tiny-02-swaggerui.jar文件 并运行
RUN bash -c 'touch /web-tiny-02-swaggerui.jar'
# 声明要暴露的端口,服务运行在此端口上
EXPOSE 8080
# 指定docker容器启动时运行的jar包(执行的命令)
ENTRYPOINT ["java", "-jar", "/web-tiny-02-swaggerui.jar"]
# 指定维护者的名字
MAINTAINER zxzh
