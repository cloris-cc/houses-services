package com.jacksonfang.api.controller;

import com.jacksonfang.api.model.User;
import com.jacksonfang.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jacky
 * Date:   2019/3/17
 * Time:   19:10
 */
@RestController
public class UserController {
    private static final String URL = "http://user-service/user";

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/auth")
    public RestResponse auth(@RequestBody User user) {
        return restTemplate.postForObject(URL + "/auth", user, RestResponse.class);
    }
}
