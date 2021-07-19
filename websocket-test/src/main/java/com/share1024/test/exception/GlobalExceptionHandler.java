package com.share1024.test.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * \* @Author: yesheng
 * \* Date: 2021/7/19 19:27
 * \* Description:
 * \
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return ResponseEntity.internalServerError().body("request error :\n"+e.getMessage());
    }
}