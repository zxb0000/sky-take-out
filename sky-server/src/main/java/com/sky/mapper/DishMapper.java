package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {



    Page<Dish> pageSelect(Integer id, String name, Integer status, Integer categoryId);

    @Select("select *from dish where id=#{id}")
    Dish getById(Integer id);

    @Select("select *from dish where category_id=#{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);
}
