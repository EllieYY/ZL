package com.sk.zl.aop;

import com.sk.zl.model.result.ResultBean;
import com.sk.zl.utils.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Description : 异常处理AOP
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Slf4j
@RestControllerAdvice
public class ResultBeanExceptionHandler {
    @ExceptionHandler(IOException.class)
    public ResultBean<?> handleIOException(IOException exception, HttpServletResponse response) {

        log.error("IO异常", exception);
        Throwable throwable = exception;
        if (exception instanceof FileNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 这两行代码，主要解决链式异常的消息提取问题，默认提取触发异常的最原始异常的消息
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
        }
        return ResultBeanUtil.makeResp(throwable);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResultBean<?> handleException(RuntimeException exception, HttpServletResponse response) {

        log.error("严重异常", exception);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Throwable throwable = exception;
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        return ResultBeanUtil.makeResp(exception);
    }
}
