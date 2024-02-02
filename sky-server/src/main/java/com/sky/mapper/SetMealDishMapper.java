package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    void update(SetmealDish setmealDish);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> selectBySetMealId(Long id);

    @Insert("insert into setmeal_dish(id, setmeal_id, dish_id, name, price, copies) " +
            "values (#{id},#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void insert(SetmealDish dish);
}
