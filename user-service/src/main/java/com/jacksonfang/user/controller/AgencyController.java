package com.jacksonfang.user.controller;


import com.jacksonfang.common.PageParams;
import com.jacksonfang.common.RestResponse;
import com.jacksonfang.user.model.ListResponse;
import com.jacksonfang.user.model.User;
import com.jacksonfang.user.service.AgencyService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jackson Fang
 * Date:   2018/11/18
 * Time:   19:09
 */
@RestController
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    // ------------------------ 查询 ------------------------
    @GetMapping("/getList")
    public RestResponse<ListResponse<User>> agentList(Integer limit, Integer offset) {
        PageParams pageParams = new PageParams();
        pageParams.setLimit(limit);
        pageParams.setOffset(offset);
        Pair<List<User>, Long> pair = agencyService.getAllAgent(pageParams);
        ListResponse<User> response = ListResponse.build(pair.getKey(), pair.getValue());
        return RestResponse.success(response);
    }

    @GetMapping("/agentDetail")
    public RestResponse<User> agentDetail(Long id) {
        User user = agencyService.getAgentDetail(id);
        return RestResponse.success(user);

    }

}
