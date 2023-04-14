package com.slj.service;

import com.slj.domain.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Mr.shu
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2022-11-17 14:20:44
*/
public interface ShoppingCartService extends IService<ShoppingCart> {

    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart sub(ShoppingCart shoppingCart);

    List<ShoppingCart> getShoppingCarts();
}
