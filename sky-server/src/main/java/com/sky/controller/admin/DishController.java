package com.sky.controller.admin;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @ApiOperation("分页查询菜品")
    @GetMapping("/page")
    public Result pageQuery(DishPageQueryDTO dishPageQueryDTO){
       PageResult pageResult= dishService.pageQuery(dishPageQueryDTO);
       return Result.success(pageResult);
    }
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        DishVO dishVO = dishService.getByid(id);
        return Result.success(dishVO);
    }
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result getByCategoryId(String categoryId){
       List<Dish> list=dishService.getByCategoryId( categoryId);
        return Result.success(list);
    }
}
