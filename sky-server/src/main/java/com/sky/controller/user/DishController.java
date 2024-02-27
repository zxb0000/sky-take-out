package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api("菜品浏览接口")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result getDishByCategoryID(Long categoryId){
        //先查缓存，看看有没有
        String key="dish_"+categoryId;
        List<DishVO> dishes = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishes!=null&&dishes.size()>1){
            return Result.success(dishes);
        }
        //没有缓存，
         dishes = dishService.getMealsByCategoryId(categoryId);
        redisTemplate.opsForValue().set(key,dishes);
        return Result.success(dishes);
    }

}
