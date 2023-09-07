package com.redislimit.springbootredis.aspect;

import com.redislimit.springbootredis.entity.LimitType;
import com.redislimit.springbootredis.service.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    private RedisTemplate<Object, Object> redisTemplate;

    private DefaultRedisScript<Long> limitScript;

    public RateLimitAspect(RedisTemplate<Object, Object> redisTemplate, DefaultRedisScript<Long> limitScript) {
        this.redisTemplate = redisTemplate;
        this.limitScript = limitScript;
    }

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        //获取限流key
        String key = rateLimiter.key();
        //获取限流时间
        int time = rateLimiter.time();
        //获取限流次数
        int count = rateLimiter.count();

        //获取组合之后的key
        String combineKey = getCombineKey(rateLimiter, point);
        //返回仅包含指定对象的不可变列表。返回的列表是可序列化的。
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            /**
             * @param1: Redis 封装脚本的对象
             * @param2: 组合后的key 对应脚本中的KEYS
             * @param3:可变参数 count-限流次数，time-限流时间（单位：秒）
             * @param3:对应脚本中的 ARGV
             */
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            //如果当前访问次数大于限流次数则抛出异常，限制接口访问
            if (number == null || number.intValue() > count) {
                throw new RuntimeException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), key);
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        //从@RateLimiter注解中获取限流key rate_limit:
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        //判断@RateLimiter注解的 限流类型
        if (rateLimiter.limitType() == LimitType.IP) {
            //如果为IP则将请求ip端口进行拼接rate_limit:localhost:8080-
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            stringBuffer.append(request.getHeader("Host")).append("-");
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        //得到Method对象
        Method method = signature.getMethod();
        //得到Class
        Class<?> targetClass = method.getDeclaringClass();
        //将运行时类名和方法名进行拼接
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        //ip: rate_limit:localhost:8080-com.llp.llpjavamail.controller.HelloController-hello
        //default: rate_limit:com.llp.llpjavamail.controller.HelloController-hello
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }
}

