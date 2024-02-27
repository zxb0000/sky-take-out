package com.sky.service;

import com.sky.entity.SetmealDish;

import java.util.List;

public interface SetMealDishService {
    List<SetmealDish> getBySetmealId(Long id);
}
