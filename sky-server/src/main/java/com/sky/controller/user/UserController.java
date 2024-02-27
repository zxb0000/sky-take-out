package com.sky.controller.user;


import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@Slf4j
@Api(tags = "用户信息相关接口")
@RequestMapping("user/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtProperties jwtProperties;
    @ApiOperation("用户微信登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO dto) throws IOException {
        User user = userService.userLogin(dto);
        //装载信息体Claims
        DefaultClaims claims= new DefaultClaims();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());

        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        //构造VO
        UserLoginVO userLoginVO=UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(jwt)
                .build();
        return Result.success(userLoginVO);
    }
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(){
    return null;
    }
}
