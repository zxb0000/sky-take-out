package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;
    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result pageQuery(DishPageQueryDTO dishPageQueryDTO){
       PageResult pageResult= dishService.pageQuery(dishPageQueryDTO);
       return Result.success(pageResult);
    }
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        DishVO dishVO = dishService.getByid(id);
        return Result.success(dishVO);
    }
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result getByCategoryId(Long categoryId){
       List<Dish> list=dishService.getByCategoryId( categoryId);
        return Result.success(list);
    }
    @ApiOperation("新增菜品")
    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        dishService.addDish(dishDTO);
        String key="dish_"+dishDTO.getCategoryId();
        clearCache(key);
        return Result.success();
    }
    @ApiOperation("删除菜品")
    @DeleteMapping
    public Result deleteDishes(@RequestParam List<Long> ids){
        dishService.deleteDishes(ids);
        clearCache("dish_*");
        return Result.success();
    }
    @ApiOperation("修改菜品")
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);

        clearCache("dish_*");

        return Result.success();
    }
    @ApiOperation("起售禁售状态改变")
    @PostMapping("/status/{status}")
    public Result statusChange(@PathVariable Integer status,Long id){
        log.info("id为：{}，状态：{}",id,status);
        dishService.statusChange(status,id);
        clearCache("dish_*");
        return Result.success();
    }
    private void clearCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
