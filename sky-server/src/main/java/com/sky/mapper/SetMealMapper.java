package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetMealMapper {
    Page<SetmealVO> pageSelect(SetmealPageQueryDTO setmealPageQueryDTO);


    void insertToSetMeal(Setmeal setmeal);
    void update(Setmeal setmeal);

    void deleteByIds(Long[] ids);

    @Select("select setmeal.category_id,category.name as category_name,setmeal.description," +
            "setmeal.id,setmeal.image,setmeal.name,setmeal.price,setmeal.status,setmeal.update_time" +
            " from setmeal join category on setmeal.category_id = category.id" +
            " where setmeal.id=#{id}")
    SetmealVO selectByid(Long id);
}
