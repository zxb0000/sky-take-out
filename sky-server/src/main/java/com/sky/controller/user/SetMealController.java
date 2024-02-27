package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetMealDishService;
import com.sky.service.SetMealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetMealController")
@RequestMapping("user/setmeal")
@Slf4j
@Api(tags = "套餐浏览接口")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private SetMealDishService setMealDishService;
    /**
     * 根据套餐分类Id查询套餐
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames = "setMealCache",key = "#categoryId")
    public Result getByCategoryId(Long categoryId){
        log.info("category ID：{}",categoryId);
        List<Setmeal> setmeal=setMealService.getByCategoryId(categoryId);
        return Result.success(setmeal);
    }
    @ApiOperation("根据套餐id查询包含的菜品")
    @GetMapping("/dish/{id}")
    public Result getSetBySetMealID(@PathVariable Long id){
         List<SetmealDish> d  = setMealDishService.getBySetmealId(id);
        return Result.success(d);
    }
}
