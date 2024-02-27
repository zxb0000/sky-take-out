package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShopCarService {
    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShopingCar();

    /**
     * 清空购物车
     */
    void cleanAll();

    void updateShopingCar(ShoppingCartDTO shoppingCartDTO);
}
