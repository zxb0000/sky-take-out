package com.sky.service.impl;

import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.service.SetMealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetMealDishServiceImpl implements SetMealDishService {
    @Autowired
    SetMealDishMapper setMealDishMapper;
    @Override
    public List<SetmealDish> getBySetmealId(Long id) {
        return setMealDishMapper.selectBySetMealId(id);
    }
}
