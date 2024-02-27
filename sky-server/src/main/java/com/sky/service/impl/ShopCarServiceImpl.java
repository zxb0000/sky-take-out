package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Category;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShopCarMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import com.sky.service.DishService;
import com.sky.service.ShopCarService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShopCarServiceImpl implements ShopCarService {

    @Autowired
    private ShopCarMapper shopCarMapper;
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private DishService dishService;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断是不是已经在购物车中

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shopCarMapper.select(shoppingCart);
        if (list != null && list.size() > 0) {
            //在购物车中数量加一
            ShoppingCart first = list.get(0);
            first.setNumber(first.getNumber() + 1);
            shopCarMapper.updateNumber(first);
            return;
        }
        //不在添加到购物车中
        //判断是菜品还是套餐
        if (shoppingCart.getSetmealId() != null) {
            SetmealVO setmealVO = setMealMapper.selectByid(shoppingCart.getSetmealId());
            shoppingCart.setAmount(setmealVO.getPrice());
            shoppingCart.setName(setmealVO.getName());
            shoppingCart.setImage(setmealVO.getImage());
        } else {
            DishVO dishVO = dishService.getByid(shoppingCart.getDishId());
            shoppingCart.setAmount(dishVO.getPrice());
            shoppingCart.setImage(dishVO.getImage());
            shoppingCart.setName(dishVO.getName());
        }
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setNumber(1);
        //添加到购物车
        shopCarMapper.insert(shoppingCart);
    }

    @Override
    public List<ShoppingCart> showShopingCar() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        return shopCarMapper.select(shoppingCart);
    }

    @Override
    public void cleanAll() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        shopCarMapper.delete(shoppingCart);
    }

    @Override
    public void updateShopingCar(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        //查询商品的信息
        List<ShoppingCart> list = shopCarMapper.select(shoppingCart);
        ShoppingCart first = list.get(0);
        //如果商品的数量大于一，修改数量减一
        if (first.getNumber()>1){
            first.setNumber(first.getNumber() - 1);
            shopCarMapper.updateNumber(first);
        }else {
            //小于1的直接删除商品
            shopCarMapper.delete(first);
        }
    }
}
