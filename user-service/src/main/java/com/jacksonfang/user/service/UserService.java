package com.jacksonfang.user.service;

import com.google.common.collect.ImmutableMap;
import com.jacksonfang.user.constant.RedisPrefix;
import com.jacksonfang.user.exception.Type;
import com.jacksonfang.user.exception.UserException;
import com.jacksonfang.user.mapper.UserMapper;
import com.jacksonfang.user.model.User;
import com.jacksonfang.user.utils.BeanHelper;
import com.jacksonfang.user.utils.HashUtils;
import com.jacksonfang.user.utils.JWTHelper;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Jackson Fang
 * Date:   2018/11/15
 * Time:   20:24
 */
@Service
@Transactional
public class UserService {

    @Value("${file.prefix}")
    String imgPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;


    /**
     * 通过 id 查询用户。
     * <p>
     * 先在 Redis 缓存中查询，若空则在 MySQL 数据库查找，再将返回结果存入 Redis 中。
     *
     * @param id 用户 id
     * @return user 查询到的用户
     */
    public User getUserById(Long id) {
        String key = RedisPrefix.USER + id;
        User user = (User) redisTemplate.opsForValue().get(key);

        if (StringUtils.isEmpty(user)) {
            user = userMapper.selectById(id);
            if (StringUtils.isEmpty(user)) {
                return null;
            } else { // 存在该 user。
                user.setAvatar(imgPrefix + user.getAvatar()); // 构建用户头像。
                redisTemplate.opsForValue().set(key, user);
                redisTemplate.expire(key, 5L, TimeUnit.MINUTES); // 设置 5 分钟缓存过期时间。生产环境应增大该数值。
            }
        }

        return user;
    }

    /**
     * 多条件查询用户。
     * <p>
     * 因为没有特定的查询条件，可能会有较多的结果，所以不做缓存。
     *
     * @param user 查询的条件
     * @return users 查询到的用户列表
     */
    public List<User> getUserByQuery(User user) {
        List<User> users = userMapper.select(user);
        users.forEach(u -> {
            u.setAvatar(imgPrefix + u.getAvatar());
        });
        return users;
    }

    /**
     * 添加用户。
     *
     * @param user      用户
     * @param enableUrl 激活链接
     */
    public void addAccount(User user, String enableUrl) {
        user.setPasswd(HashUtils.encryptPassword(user.getPasswd()));
        BeanHelper.onInsert(user);
        userMapper.insert(user);
        registerNotify(user.getEmail(), enableUrl);
    }


    /**
     * 激活用户。
     *
     * @param key randomKey
     * @return true
     */
    public boolean enable(String key) {
        String email = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(email)) {
            throw new UserException(Type.USER_NOT_FOUND);
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.update(updateUser);
        return true;
    }

    private void registerNotify(String email, String enableUrl) {
        String randomKey = RedisPrefix.EMAIL + RandomStringUtils.randomAlphabetic(10);
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1L, TimeUnit.HOURS);

        String verifyURL = enableUrl + "?key=" + randomKey;
        mailService.sendMail("房产平台用户激活邮件", verifyURL, email);
    }

    /**
     * 校验用户名密码、生成 token 并返回用户对象。
     *
     * @param email  登录邮箱
     * @param passwd 密码
     * @return user 用户对象
     */
    public User auth(String email, String passwd) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(passwd)) {
            throw new UserException(Type.USER_AUTH_FAIL);
        }
        User query = new User();
        query.setEmail(email);
        query.setPasswd(HashUtils.encryptPassword(passwd));
        query.setEnable(1);
        List<User> list = this.getUserByQuery(query);
        if (!list.isEmpty()) {
            User user = list.get(0);
            this.onLogin(user);
            return user;
        } else {
            throw new UserException(Type.USER_AUTH_FAIL);
        }


    }

    /**
     * 生成该 user(as value) 的 token(as key)，并存到 Redis 中。
     *
     * @param user 已登录的用户
     */
    private void onLogin(User user) {
        String token = JWTHelper.generateToken(ImmutableMap.of("email", user.getEmail(), "name", user.getName(), "timestamp", Instant.now().getEpochSecond() + ""));
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisPrefix.EMAIL + user.getEmail(), token, 30, TimeUnit.MINUTES);
    }

    /**
     * 通过 token 在 jwt 中获取用户信息。
     *
     * @param token JWT 生成绑定用户的 token
     * @return
     */
    public User getUserByToken(String token) {
        Map<String, String> claims = JWTHelper.verifyToken(token);
        String email = claims.get("email");
        Long expire = redisTemplate.getExpire(RedisPrefix.EMAIL + email);
        if (expire == null) {
            expire = 0L;
        }
        if (expire > 0) {
            redisTemplate.expire(RedisPrefix.EMAIL + email, 30, TimeUnit.MINUTES);
            User user = getUserByQuery(new User().setEmail(email)).get(0);
            if (user != null) {
                user.setToken(token);
                return user;
            } else {
                throw new UserException(Type.USER_NOT_LOGIN);
            }
        } else {
            throw new UserException(Type.USER_NOT_LOGIN);
        }

    }

    public void invalidate(String token) {
        Map<String, String> claims = JWTHelper.verifyToken(token);
        redisTemplate.delete(RedisPrefix.EMAIL + claims.get("email"));
    }
}
