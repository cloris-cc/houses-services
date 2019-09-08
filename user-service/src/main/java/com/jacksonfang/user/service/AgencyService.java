package com.jacksonfang.user.service;

import com.jacksonfang.common.PageParams;
import com.jacksonfang.user.mapper.AgencyMapper;
import com.jacksonfang.user.model.User;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jacky
 * Date:   2018/12/12
 * Time:   13:22
 */
@Service
public class AgencyService {

    @Value("${file.prefix}")
    String imgPrefix;

    @Autowired
    AgencyMapper agencyMapper;

    public Pair<List<User>, Long> getAllAgent(PageParams pageParams) {
        List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
        setImg(agents);
        Long count = agencyMapper.selectAgentCount(new User());
        return ImmutablePair.of(agents, count);
    }

    /**
     * 设置用户头像。
     */
    private void setImg(List<User> list) {
        list.forEach(user -> user.setAvatar(imgPrefix + user.getAvatar()));
    }

    public User getAgentDetail(Long id) {
        User user = new User();
        user.setId(id);
        user.setType(2);
        List<User> list = agencyMapper.selectAgent(user, PageParams.build(1,1));
        setImg(list);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
