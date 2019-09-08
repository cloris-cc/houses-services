package com.jacksonfang.user.exception;


import com.jacksonfang.common.RestCode;
import com.jacksonfang.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jackson Fang
 * Date:   2018/11/15
 * Time:   15:56
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public RestResponse<Object> handler(HttpServletRequest req, Exception e) {
        logger.error(e.getMessage(), e);

        if(e instanceof UserException) {
            if(((UserException) e).getType().equals(Type.USER_NOT_FOUND) ) {
                return RestResponse.error(RestCode.INVALID_KEY);
            }
            if(((UserException) e).getType().equals(Type.USER_AUTH_FAIL)) {
                return RestResponse.error(RestCode.WRONG_EMAIL_OR_PASSWORD);
            }if(((UserException) e).getType().equals(Type.USER_NOT_LOGIN)) {
                return RestResponse.error(RestCode.USER_NOT_LOGIN);
            }
        }

        return RestResponse.error(RestCode.UNKNOWN_ERROR);
    }
}
