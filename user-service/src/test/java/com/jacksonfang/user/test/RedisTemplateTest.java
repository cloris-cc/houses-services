package com.jacksonfang.user.test;

import com.jacksonfang.user.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

/**
 * @author Jackson Fang
 * Date:   2018/11/15
 * Time:   23:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> template;  // the defined bean is effective.

    @Test
    public void test() {
        User user = new User();
        user.setName("Jackson-Fang");
        user.setEmail("1015440757@qq.com");
        user.setId(51L);

        template.opsForValue().set("test-user", user);

        User res = (User) template.opsForValue().get("test-user-null");

        if(res == null) {
            System.out.println("res is null.");
        }

        if (StringUtils.isEmpty(res)) {
            System.out.println("res is empty.");
        }

        System.out.println("the result is: \n" + res);

    }
}
