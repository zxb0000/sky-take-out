package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {



    Page<DishVO> pageSelect(DishPageQueryDTO dishPageQueryDTO);

    @Select("select *from dish where id=#{id}")
    Dish getById(Long id);

    @Select("select *from dish where category_id=#{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);

    @AutoFill(value=OperationType.INSERT)
    void insert(Dish dish);


    void delete(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
}
