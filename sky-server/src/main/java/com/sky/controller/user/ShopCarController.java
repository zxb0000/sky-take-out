package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "用户购物车接口")
public class ShopCarController {
    @Autowired
    private ShopCarService shopCarService;
    @PostMapping("/add")
    @ApiOperation("购物车添加商品")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shopCarService.add(shoppingCartDTO);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查看购物车的信息")
    public Result<List<ShoppingCart>> list(){
       List<ShoppingCart> shoppingCarts= shopCarService.showShopingCar();
       return Result.success(shoppingCarts);
    }
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clearAll(){
        shopCarService.cleanAll();
        return Result.success();
    }
    @ApiOperation("在购物车中修改商品")
    @PostMapping("/sub")
    public Result updateInfo(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shopCarService.updateShopingCar(shoppingCartDTO);
        return Result.success();
    }
}
