package com.maple.web.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.maple.web.dto.ControllerLog;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 *对Controller层的织入的日志切面
 *@author zxzh
 *@date 2021/5/13
 */
@Aspect//切面
@Component
public class ControllerLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerLogAspect.class);
    /*
    * 使用Pointcut定义切点,给各个类型的通知提供可复用的切点
    * */
    @Pointcut("execution(public * com.maple.web.controller.*.*(..))")//切点表达式
    private void pointCutLog(){}

    @Before("pointCutLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{}//JoinPoint是连接点,
    // spring提供的关于目标对象的信息,如类名称、方法名称、方法参数

    @AfterReturning(value = "pointCutLog()", returning = "returnResult")
    public void doAfterReturn(Object returnResult) throws Throwable{}

    @Around("pointCutLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();//现在的时间戳,毫秒
        //解析出切点的方法
        Object result = joinPoint.proceed();//执行结果.要抛出可抛出的异常 throw Throwable
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ControllerLog controllerLog = new ControllerLog();
        if(method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            //记录请求信息
            controllerLog.setDescription(apiOperation.value());
        }
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long endTime = System.currentTimeMillis();
        //将消息填入自定义的ControllerLog中
        String urlStr = request.getRequestURL().toString();
        controllerLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));//去除后缀
        controllerLog.setIp(request.getRemoteUser());
        controllerLog.setMethod(request.getMethod());
        controllerLog.setParameter(getParameter(method, joinPoint.getArgs()));
        controllerLog.setResult(result);
        controllerLog.setSpendTime((int) (endTime - startTime));
        controllerLog.setStartTime(startTime);
        controllerLog.setUri(request.getRequestURI());
        controllerLog.setUrl(urlStr);
        LOGGER.info("{}", JSONUtil.parse(controllerLog));//将对象格式化存在日志里,便于阅读
        return result;//这个必须和方法的返回值都有,否则,内容会被这里截留,而不会传到前端
    }

    private Object getParameter(Method method, Object[] args){
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for(int i = 0; i < parameters.length; i++){
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if(requestBody != null){//在parameters取到了RequestBody才进行存储
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if(requestParam != null){
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if(!StringUtils.isEmpty(requestParam.value())){
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if(argList.size() == 0){
            return null;
        }else if(argList.size() == 1){
            return argList.get(0);
        }else {
            return argList;
        }
    }
}
