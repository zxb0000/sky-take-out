package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    DishVO getByid(Long id);

    List<Dish> getByCategoryId(Long categoryId);

    void addDish(DishDTO dishDTO);

    void deleteDishes(List<Long> ids);

    void update(DishDTO dishDTO);

    void statusChange(Integer status, Long id);

    /**
     * 根据分类的ID获取菜品的信息
     * @param categoryId
     * @return
     */
    List<DishVO> getMealsByCategoryId(Long categoryId);
}
