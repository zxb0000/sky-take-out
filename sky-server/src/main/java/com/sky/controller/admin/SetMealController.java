package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetMealController {
    @Autowired
    SetMealService setMealService;
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
       PageResult pageResult= setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("新增套餐")
    @PostMapping
    public Result addSetMeal(@RequestBody SetmealDTO setmealDTO)
    {
        setMealService.addSetMeal(setmealDTO);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("套餐状态改变")
    public Result statusChange(@PathVariable Integer status, Long id){
        setMealService.changeStatus(status,id);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteByids( Long[] ids){
        setMealService.deleteByIds(ids);
        return Result.success();
    }

    @ApiOperation("修改套餐信息")
    @PutMapping
    public Result updateSetMeal(@RequestBody SetmealDTO setmealDTO){
        setMealService.updateSetMeal(setmealDTO);
        return Result.success();
    }
    @ApiOperation("根据id获取套餐信息")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id){
        SetmealVO setmealVO=setMealService.getById(id);
        return Result.success(setmealVO);
    }
}
