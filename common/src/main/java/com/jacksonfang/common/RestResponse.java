package com.jacksonfang.common;

import lombok.Data;

/**
 * @author Jackson Fang
 * Date:   2018/11/15
 * Time:   8:48
 */
@Data
public class RestResponse<T> {
    private int code;
    private String msg;
    private T result;

    private RestResponse() {
        this(RestCode.OK.code, RestCode.OK.msg);
    }

    private RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static <T> RestResponse<T> success(){
        return new RestResponse<>();
    }

    public static <T> RestResponse<T> success(T result){
        RestResponse<T> response = new RestResponse<>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> error(RestCode code) {
        return new RestResponse<>(code.code, code.msg);
    }
}
