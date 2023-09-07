package com.redislimit.springbootredis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @ExceptionHandler中定义具体异常，如定义了具体的异常，就不会走Exception这个父类异常了。
     */
/*    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        // 构建一个包含异常信息的 JSON 对象
        String errorMessage = "这是一个全局处理异常: " + e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        // 返回 JSON 对象和适当的 HTTP 状态码
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayOutOfRangeException(IndexOutOfBoundsException e) {
        // 构建一个包含异常信息的 JSON 对象
        String errorMessage = "数组越界: " + e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        // 返回 JSON 对象和适当的 HTTP 状态码
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }*/
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> serviceException(RuntimeException e) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 500);
        map.put("message", e.getMessage());
        return map;
    }
}
