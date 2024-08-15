//package com.example.rest.aspect;
//
//import java.util.Arrays;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.CodeSignature;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class LoggingAspect {
//
//
//    // Before, After, Around, AfterReturning, AfterThrowing
//
////    @Before(value = "execution(* com.example.rest.service.*.*(..))", argNames = "joinPoint")
//    @Before(value = "@annotation(org.springframework.transaction.annotation.Transactional)", argNames = "joinPoint")
//    public void logBefore(JoinPoint joinPoint) {
//        System.out.println("Before Method name: " + joinPoint.getSignature().getName());
//
//        CodeSignature methodSignature = (CodeSignature) joinPoint.getSignature();
//        String[] sigParamNames = methodSignature.getParameterNames();
//        Object[] args = joinPoint.getArgs();
//
//        for(int i = 0; i < sigParamNames.length; i++) {
//            System.out.println("Parameter Name: " + sigParamNames[i]);
//            System.out.println("Parameter Type: " + args[i].getClass());
//            System.out.println("Parameter Value: " + args[i]);
//        }
//    }
//
//    @After(value = "execution(* com.example.rest.service.*.*(..))", argNames = "joinPoint")
//    public void logAfter(JoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//
//        System.out.println("After Method response type: " + methodSignature.getReturnType());
//    }
//
//    @AfterReturning(value = "execution(* com.example.rest.service.*.*(..))",
//            argNames = "joinPoint, object", returning = "object")
//    public void logAfterReturning(JoinPoint joinPoint, Object object) {
//
//        Class<?> aClass = object.getClass();
//
//        System.out.println("AfterReturning Method response type: " + aClass);
//        System.out.println("AfterReturning Method response value: " + object);
//    }
//
//    @AfterThrowing(value = "execution(* com.example.rest.service.*.*(..))",
//            argNames = "joinPoint, exception",  throwing = "exception")
//    public void logAfterReturning(JoinPoint joinPoint, Exception exception) {
//        System.out.println("AfterThrowing Method exception type: " + exception.getClass());
//        System.out.println("AfterThrowing Method exception message: " + exception.getMessage());
//    }
//
////    @Around(value = "execution(* com.example.rest.service.*.*(..))",
////            argNames = "joinPoint")
////    public void logAround(JoinPoint joinPoint) {
////        System.err.println("Around Method name: " + joinPoint.getSignature().getName());
////
////        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
////
////        String[] sigParamNames = methodSignature.getParameterNames();
////        Object[] args = joinPoint.getArgs();
////
////        for(int i = 0; i < sigParamNames.length; i++) {
////            System.err.println("Parameter Name: " + sigParamNames[i]);
////            System.err.println("Parameter Type: " + args[i].getClass());
////            System.err.println("Parameter Value: " + args[i]);
////        }
////
////        System.err.println("Around Method response type: " + methodSignature.getReturnType());
////    }
//
//
//}
