package com.jacksonfang.user.exception;

import com.jacksonfang.common.RestCode;
import lombok.Getter;

/**
 * @author Jackson Fang
 * Date:   2018/11/17
 * Time:   21:24
 */
@Getter
public class UserException extends RuntimeException {

    private Type type;

    public UserException(Type type) {
        this.type = type;
    }

}
