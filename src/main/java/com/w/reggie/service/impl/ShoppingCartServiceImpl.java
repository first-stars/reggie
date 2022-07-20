package com.w.reggie.service.impl;

import com.w.reggie.entity.ShoppingCart;
import com.w.reggie.mapper.ShoppingCartMapper;
import com.w.reggie.service.IShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

}
