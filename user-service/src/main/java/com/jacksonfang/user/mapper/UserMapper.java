package com.jacksonfang.user.mapper;

import com.jacksonfang.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jackson Fang
 *         Date: 2018/11/15
 *         Time: 20:32
 */
@Mapper
public interface UserMapper {

    User selectById(Long id);

    List<User> select(User user);

    int update(User user);

    int insert(User account);

    int delete(String email);
}
