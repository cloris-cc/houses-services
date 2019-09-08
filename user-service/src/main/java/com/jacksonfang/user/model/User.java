package com.jacksonfang.user.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String email;
    private String phone;
    private String name;
    private String passwd;
    private String confirmPasswd;
    private Integer type; // 普通用户 1，经纪人 2。
    private Date createTime;
    private Integer enable;
    private String avatar;
    private MultipartFile avatarFile;
    private String newPassword;
    private String key;
    private Long agencyId;
    private String aboutme;
//    private String agencyName;
    private String enableUrl;
    private String token;

}
