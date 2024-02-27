package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealMapper setMealMapper;
    @Autowired
    SetMealDishMapper setMealDishMapper;

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMealMapper.pageSelect(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    public void addSetMeal(SetmealDTO setmealDTO) {
        //将DTO转换实体
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //setmeal.setCreateTime(LocalDateTime.now());
        //setmeal.setCreateUser(BaseContext.getCurrentId());
        //setmeal.setUpdateTime(LocalDateTime.now());
       // setmeal.setUpdateUser(BaseContext.getCurrentId());
        setMealMapper.insertToSetMeal(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish dish : setmealDishes) {
            if (dish != null) {
                dish.setSetmealId(setmeal.getId());
                setMealDishMapper.insert(dish);
            }
        }

    }

    @Override
    public void changeStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                //.updateTime(LocalDateTime.now())
                //.updateUser(BaseContext.getCurrentId())
                .build();
        setMealMapper.update(setmeal);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        setMealMapper.deleteByIds(ids);
        setMealDishMapper.deleteBySetmealId(ids);
    }

    @Override
    public void updateSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        List<SetmealDish> setmealDishs = setmealDTO.getSetmealDishes();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //先更新套餐表
        //setmeal.setUpdateUser(BaseContext.getCurrentId());
        //setmeal.setUpdateTime(LocalDateTime.now());
        setMealMapper.update(setmeal);
        //循环更新套餐菜品关系
        for(SetmealDish setmealDish:setmealDishs){
            if(setmealDish==null) return;
            setmealDish.setSetmealId(setmeal.getId());
            setMealDishMapper.update(setmealDish);
        }

    }

    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        setmealVO= setMealMapper.selectByid(id);
        List<SetmealDish> setmealDishes= setMealDishMapper.selectBySetMealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public List<Setmeal> getByCategoryId(Long categoryId) {
        return setMealMapper.selectByCategoryId(categoryId);
    }
}
