package com.jacksonfang.user.mapper;

import com.jacksonfang.common.PageParams;
import com.jacksonfang.user.model.Agency;
import com.jacksonfang.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Jacky
 * Date:   2018/12/12
 * Time:   13:34
 */
@Mapper
public interface AgencyMapper {

    List<Agency> select(Agency agency);

    int insert(Agency agency);

    List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);

}
