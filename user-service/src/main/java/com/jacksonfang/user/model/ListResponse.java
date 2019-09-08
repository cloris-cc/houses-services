package com.jacksonfang.user.model;

import lombok.Data;

import java.util.List;

/**
 * @author Jackson Fang
 * Date:   2018/11/18
 * Time:   19:13
 */
@Data
public class ListResponse<T> {
    private List<T> list;
    private Long count;

    public static <T> ListResponse<T> build(List<T> list, Long count) {
        ListResponse<T> response = new ListResponse<>();
        response.setList(list);
        response.setCount(count);
        return response;
    }
}
