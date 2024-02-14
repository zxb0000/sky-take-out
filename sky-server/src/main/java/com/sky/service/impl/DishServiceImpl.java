package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.service.SetMealService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    SetMealDishMapper setMealDishMapper;
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageSelect(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public DishVO getByid(Long id) {
        log.info("id的值：{}",id);
        Dish dish= dishMapper.getById(id);
        List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public List<Dish> getByCategoryId(String categoryId) {
        return dishMapper.getByCategoryId(Long.valueOf(categoryId));

    }

    @Override
    @Transactional//开启事务
    public void addDish(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        Long id = dish.getId();

        List<DishFlavor> dishFlavors=dishDTO.getFlavors();
        if (dishFlavors!=null && dishFlavors.size()>0) {
            dishFlavors.forEach(dishFlavor -> {//便利插入id
                dishFlavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(dishFlavors);
        }

    }

    @Override
    @Transactional
    public void deleteDishes( List<Long> ids) {
        //先判断是不是在售的菜品--在售的不能被删除
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断是不是在套餐中得菜品
        List<Long> setMealIds =setMealDishMapper.getSetmealIdsByDishIds(ids);
        if ((setMealIds != null) && (setMealIds.size() > 0)){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表中得数据
        dishMapper.delete(ids);
        //删除口味表中得数据
        dishFlavorMapper.deleteByDishIds(ids);


    }

    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        //修改菜品表中的信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //删除菜品口味表中得数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //重新插入口味表数据
        List<DishFlavor> dishFlavors = dishDTO.getFlavors();
        if (dishFlavors!=null && dishFlavors.size()>0) {
            dishFlavors.forEach(dishFlavor -> {//便遍历插入id
                dishFlavor.setDishId(dishDTO.getId());
            });
            dishFlavorMapper.insertBatch(dishFlavors);
        }

    }

    @Override
    public void statusChange(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
}
