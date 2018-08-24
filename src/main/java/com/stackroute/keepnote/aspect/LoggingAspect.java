package com.stackroute.keepnote.aspect;

import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
    
	//Logger logger = LoggerFactory.getLogger(WorkOrderController.class);
	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	@After("execution(com.stackroute.keepnote.controller.*)")
	public void logAfter(){
		System.out.println("@After:"+new Date());
	}
	
	@AfterReturning(pointcut = "execution(com.stackroute.keepnote.controller.*)",
			returning="val")
	public void logAfterReturning(Object val){
		System.out.println("Method return value:"+ val);
		System.out.println("@AfterReturning:"+new Date());
	}
	
	@AfterThrowing(pointcut = "execution(com.stackroute.keepnote.controller.*)",
			throwing="exception")
	public void logAfterThrowing(Exception exception){
		System.out.println("@AfterReturning:"+new Date());
		System.out.println("Exception caught:"+ exception.getMessage());
	}
	
	@Before("execution(com.stackroute.keepnote.controller.*)")
	public void logBefore(){
		System.out.println("@Before:"+new Date());
	}
}
