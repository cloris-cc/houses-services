package com.jacksonfang.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jacky
 * Date:   2019/3/16
 * Time:   1:47
 */
@Repository
public class UserDao {
    @Autowired
    RestTemplate restTemplate;

    public String getUsernameById(Long id) {
        return restTemplate.getForObject("http://user-service/user/getUsernameById?id={1}", String.class, id);
    }
}
