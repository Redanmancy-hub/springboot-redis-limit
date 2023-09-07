package com.redislimit.springbootredis.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/error")
public class TestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/exception")
    public void exceptionController(){
        try{
            int a = 1/0;
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw e;    // 抛出异常以触发全局异常处理器
        }
    }

    @GetMapping("/arrayOutOfRange")
    public void arrayOutOfRangeExceptionController(){
        try {
            List<String> list = Lists.newArrayList("a", "b", "c");
            System.out.println(list);
            System.out.println(list.get(4));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e; // 抛出异常以触发全局异常处理器
        }
    }

}
