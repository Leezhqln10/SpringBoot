package com.cy.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.utils.IPUtils;
import com.cy.pj.common.utils.ShiroUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * AOP编程入门 定义切面对象类型，其特点: 1)使用@Aspect注解修饰 2)切面内部包含切入点和通知的定义
 * 2.1)通过Pointcut注解定义切入点(通常会对应某个类或多个类中的方法的集合)
 * 2.2)通过@Around等注解描述的方法为通知方法(此方法内部要实现扩展业务的织入)
 *
 * @author qilei
 */
@Order(2)
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    // private static final Logger log=
    // LoggerFactory.getLogger(SysLogAspect.class);
    // bean(bean名称)为一个切入点表达式
    // @Pointcut("bean(sysUserServiceImpl)")
    @Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
    public void doLogPointCut() {
    }

    /**
     * 环绕通知方法(这个内部可以在目标方法执行之前，之后添加扩展业务逻辑)
     *
     * @param jp 连接点(封装了切入点中某个正在执行的方法信息)
     * @return 为目标方法的执行结果
     */
    @Around("doLogPointCut()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("SysLogAspect.doAround");
        long start = System.currentTimeMillis();
        log.info("start {}", start);
        try {
            Object result = jp.proceed();// 调用逻辑:本类中其它通知-->其它切面-->还有目标方法
            long end = System.currentTimeMillis();
            log.info("end {}", end);
            // 记录用户的正常行为信息
            saveLog(jp, (end - start));// 基于此方法将用户行为信息写到数据库中
            return result;
        } catch (Throwable e) {
            log.error("error {}", e.getMessage());
            throw e;
        }
    }

    @Autowired
    private SysLogService sysLogService;
    // 获取用户行为信息(谁在什么时间,执行了什么操作,访问了什么方法,传递了什么参数,..)并进行记录

    private void saveLog(ProceedingJoinPoint jp, long time) throws Exception {
        // 1.获取用户行为信息
        // 1.1获取ip地址
        String ip = IPUtils.getIpAddr();
        // 1.2获取登陆用户名
        //SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        String username = ShiroUtils.getUsername();// 做完登陆以后获取登陆的用户名
        // 1.3获取目标方法上RequiredLog注解指定的操作名
        // 1.3.1获取目标方法对象
        // 1.3.1.1获取目标对象类型
        Class<?> targetClass = jp.getTarget().getClass();
        // 1.3.1.2获取目标类中目标方法
        MethodSignature ms = (MethodSignature) jp.getSignature();
        Method targetMethod = targetClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        //System.out.println(targetMethod);
        // 1.3.2获取目标方法对象上的RequiredLog注解
        RequiredLog requiredLog = targetMethod.getAnnotation(RequiredLog.class);
        //System.out.println(requiredLog);
        // 1.3.3获取注解中指定的操作名
        String operation = null;
        if (requiredLog != null) {// 当切入点为注解表达式，此语句可以不进行判定
            operation = requiredLog.operation();
        }
        //System.out.println(operation);
        // 1.4获取目标方法的类全名以及方法名
        String method = targetClass.getName() + "." + targetMethod.getName();
        // 1.5获取执行方法时传入的实际参数
        String params = new ObjectMapper().writeValueAsString(jp.getArgs());//转换为json格式
        // 2.对用户行为信息进行封装
        SysLog userLog = new SysLog();
        userLog.setIp(ip);
        userLog.setUsername(username);
        userLog.setOperation(operation);
        userLog.setMethod(method);
        userLog.setParams(params);
        userLog.setTime(time);
        userLog.setCreatedTime(new Date());
        // 3.将用户行为信息写入到数据库
        sysLogService.saveObject(userLog);
    }
}// XxxController-->XxxService(XxxServiceImpl$$EnhancerXXX)--->Aspect-->XxxServiceImpl
