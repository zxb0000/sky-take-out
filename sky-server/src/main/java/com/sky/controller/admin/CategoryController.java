package com.sky.controller.admin;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "分类管理")
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService  categoryService;
    @ApiOperation("类别分页查询")
    @GetMapping("/page")
    public Result pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult=categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result getType(Integer type){
       List<Category> categories=categoryService.getByType(type);
       return Result.success(categories);
    }
}
