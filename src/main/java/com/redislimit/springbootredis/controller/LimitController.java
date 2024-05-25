package com.redislimit.springbootredis.controller;

import com.redislimit.springbootredis.enums.LimitType;
import com.redislimit.springbootredis.service.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/limit")
public class LimitController {
    //采用默认限流策略
    @GetMapping("/default")
    @RateLimiter(time = 5,count = 3,limitType = LimitType.DEFAULT)
    public String defaultLimit() {
        return "defaultLimit >>> "+new Date();
    }


    //采用IP限流策略
    @GetMapping("/ip")
    @RateLimiter(time = 10,count = 4,limitType = LimitType.IP)
    public String IPLimit() {
        return "IPLimit >>> "+new Date();
    }

}
