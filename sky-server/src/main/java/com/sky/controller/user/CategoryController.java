package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "用户分类接口")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/list")
    @ApiOperation("条件查询")
    public Result query(Integer type)
    {
        log.info("参数：{}",type);
        List<Category> byType = categoryService.getByType(type);
        return Result.success(byType);
    }
}
