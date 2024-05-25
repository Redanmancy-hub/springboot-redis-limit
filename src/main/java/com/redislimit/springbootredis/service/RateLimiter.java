package com.redislimit.springbootredis.service;

import com.redislimit.springbootredis.enums.LimitType;

import java.lang.annotation.*;

/**
 * 自定义限流注解
 * 1.使用@interface关键字
 * 2.定义注解包含的类型元素
 * 3.@Target：用来限定自定义注解能够被应用在哪些Java元素上面的（METHOD：方法上）
 * 4.@Retention：用来修饰自定义注解的生命力
 * 5.@Documented：是被用来指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default "rate_limit:";

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;

}
