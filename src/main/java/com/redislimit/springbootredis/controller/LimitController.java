package com.redislimit.springbootredis.controller;

import com.redislimit.springbootredis.entity.LimitType;
import com.redislimit.springbootredis.service.RateLimiter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/limit")
public class LimitController {
    @GetMapping("/ip")
    @RateLimiter(time = 5,count = 3,limitType = LimitType.IP)
    public String limit() {
        return "limit >>> "+new Date();
    }

}
