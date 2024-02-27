package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetMealMapper {
    Page<SetmealVO> pageSelect(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    void insertToSetMeal(Setmeal setmeal);
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    void deleteByIds(Long[] ids);

    @Select("select setmeal.category_id,category.name as category_name,setmeal.description," +
            "setmeal.id,setmeal.image,setmeal.name,setmeal.price,setmeal.status,setmeal.update_time" +
            " from setmeal join category on setmeal.category_id = category.id" +
            " where setmeal.id=#{id}")
    SetmealVO selectByid(Long id);


    @Select("select * from setmeal where category_id=#{categoryId}")
    List<Setmeal> selectByCategoryId(Long categoryId);
}
