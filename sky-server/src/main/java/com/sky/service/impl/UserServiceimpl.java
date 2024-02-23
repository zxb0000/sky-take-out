package com.sky.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceimpl implements UserService {
    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;
    @Override
    public User userLogin(UserLoginDTO userLoginDTO) throws IOException {

        //获取openid
        String openid = getOpenid(userLoginDTO.getCode());
        //判断是不是为空，有没有登录成功
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断是不是新用户，用户表里有没有此信息
        User user = userMapper.getByOpenid(openid);
        //新用户存储信息
        if(user==null){
            user=User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 获取微信用户的openid
     * @param code js_code
     * @return 微信用户的openid
     *
     */
    private String getOpenid(String code){
        Map<String, String> map =new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String respond = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", map);
        JSONObject jsonObject = JSON.parseObject(respond);
        //获取openid
        return jsonObject.getString("openid");
    }
}
