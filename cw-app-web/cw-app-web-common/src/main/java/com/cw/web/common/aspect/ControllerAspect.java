package com.cw.web.common.aspect;

import com.cw.biz.CPContext;
import com.cw.biz.log.app.dto.SystemLogDto;
import com.cw.biz.log.app.service.SystemLogAppService;
import com.cw.biz.log.domain.entity.SystemLog;
import com.cw.core.common.annotation.ActionControllerLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 记录系统操作日志
 * Created by Administrator on 2017/8/1.
 */
@Aspect
@Component
public class ControllerAspect {

    public static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    private SystemLogAppService systemLogAppService;

    // 对以下com.cw.web.Controller类中的所有方法进行切入
    @Pointcut("@annotation(com.cw.core.common.annotation.ActionControllerLog)")
    private void weblog(){
        logger.info("=============记录操作日志=============");
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ActionControllerLog controllerLog = giveController(joinPoint);

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        SystemLogDto systemLogDto = new SystemLogDto();
        if(CPContext.getContext().getSeUserInfo()!=null) {
            systemLogDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        }
        systemLogDto.setAction(controllerLog.action());
        systemLogDto.setLogDate(new Date());
        systemLogDto.setUrl(request.getRequestURL().toString());
        systemLogDto.setName(controllerLog.title());
        systemLogDto.setRemoteAddress(request.getRemoteAddr());
        systemLogDto.setOperateArg(Arrays.toString(joinPoint.getArgs()));
        systemLogAppService.create(systemLogDto);
        logger.info("=============记录操作结束=============");

    }

    /**
     * 是否存在注解，如果存在就记录日志
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private static ActionControllerLog giveController(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if(method != null)
        {
            return method.getAnnotation(ActionControllerLog.class);
        }
        return null;
    }

}

