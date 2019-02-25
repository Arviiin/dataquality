package com.arviiin.dataquality.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * 面向切面编程
 * web请求日志切面类---专门针对控制层，如谁被请求了，花了多少时间，请求发送的参数，返回得值等
 */

@Aspect     // 表示一个切面bean
@Component  // bean容器的组件注解。虽然放在contrller包里，但它不是控制器。如果注入service,但我们又没有放在service包里
//@Order(3)   // 有多个日志时，ORDER可以定义切面的执行顺序（数字越大，前置越后执行，后置越前执行）
public class LogAspect {

    private static final Logger logger= LoggerFactory.getLogger(LogAspect.class);
    private static ThreadLocal<Long> startTime = new ThreadLocal<>();  //线程副本类去记录各个线程的开始时间
    //定义切入点
	/*1、execution 表达式主体
	  2、第1个* 表示返回值类型  *表示所有类型
	  3、包名  com.*.*.controller下
	  4、第4个* 类名，com.*.*.controller包下所有类
	  5、第5个* 方法名，com.*.*.controller包下所有类所有方法
	  6、(..) 表示方法参数，..表示任何参数
	  */
    @Pointcut("execution(* com.arviiin.dataquality.controller.DimensionController.*(..))")
    public void webLog() {
    }

    //@Before("execution(* com.arviiin.dataquality.controller.*Controller.*(..))")
    //可以直接写不加@Pointcut，只不过稍微麻烦些，每个都要如上加入切入点
    @Before("webLog()")
    public void beforeMethod(JoinPoint joinPoint){//方法里面注入连接点
        logger.info("before method:");        //info ,debug ,warn ,erro四种级别，这里我们注入info级别
        //获得系统的时间，单位为毫秒
        startTime.set(System.currentTimeMillis());

        /*StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()){
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method: " + sb.toString());*/
        //获取servlet请求对象---因为这不是控制器，这里不能注入HttpServletRequest，但springMVC本身提供ServletRequestAttributes可以拿到
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL:" + request.getRequestURL().toString());         // 想那个url发的请求
        logger.info("METHOD:" + request.getMethod());
        logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());                     // 请求的是哪个类，哪种方法
        logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));     // 方法本传了哪些参数
    }

    /**
     * 后置通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    //@After("execution(* com.arviiin.dataquality.controller.*Controller.*(..))")
    @After(value = "webLog()")
    public void afterMethodAdvice(JoinPoint joinPoint){
        logger.info("after method" + new Date());
    }

    //方法的返回值注入给ret
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterMethod(Object ret) {
        logger.info("after method return data：");
        logger.info("RESPONSE:" + ret);       // 响应的内容---方法的返回值responseEntity   1000F是float的意思
        logger.info("SPEND:" + ( (System.currentTimeMillis()-startTime.get())  / 1000F) + " seconds.");
    }


}


