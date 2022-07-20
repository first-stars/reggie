package com.w.reggie.service.impl;

import com.w.reggie.entity.Dish;
import com.w.reggie.mapper.DishMapper;
import com.w.reggie.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品管理 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

}
