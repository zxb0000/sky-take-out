package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SetMealService {
    /**
     * 页面查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 新增套餐
     * @param setmealDTO
     *
     */
    void addSetMeal(SetmealDTO setmealDTO);

    void changeStatus(Integer status, Long id);

    void deleteByIds(Long[] ids);

    void updateSetMeal(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);


    List<Setmeal> getByCategoryId(Long categoryId);
}
