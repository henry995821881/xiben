package com.xiben.sso.site.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component("loggerPrint")
public class LoggerPrint  {
	
	public static Logger logger = LoggerFactory.getLogger(LoggerPrint.class);		
	

	@Before("execution(* com.xiben.sso.site.service..*.*(..))")	
	public void before(JoinPoint jp){
		
		String jp_method = jp.getSignature().getName();
		StringBuffer jp_param= new StringBuffer("参数是：");
		for(Object p :jp.getArgs()){
			
			jp_param.append(p!=null? p.toString():"null").append(",");
		}
		
		
		Object target = jp.getTarget();
		
		logger.debug("servicelogger>>>>>>>>>>>>before>>>>>>>>>");
		logger.debug("对象: "+target);
		logger.debug("方法: "+jp_method);
		logger.debug("参数 : "+jp_param.toString());
		
		
	}

	
	@AfterThrowing("execution(* com.xiben.sso.site.service..*.*(..))")	
    public void afterThrowing(JoinPoint jp,Exception exp){
   
		logger.error("servicelogger>>>>>>>>exception>>>>>>>>>>>>>");
		logger.error(exp.getMessage(),exp);
		
    }
	
	
}
