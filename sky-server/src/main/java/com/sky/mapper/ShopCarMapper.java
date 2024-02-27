package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ShopCarMapper {
    List<ShoppingCart> select(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time,number)" +
            " VALUES (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime},#{number})")
    void insert(ShoppingCart first);

    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumber(ShoppingCart first);

    void delete(ShoppingCart shoppingCart);
}
