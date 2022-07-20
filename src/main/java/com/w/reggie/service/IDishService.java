package com.w.reggie.service;

import com.w.reggie.dto.DishDto;
import com.w.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜品管理 服务类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
public interface IDishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，操作两张表dish，dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getWithFlavor(Long id);

    //修改菜品信息
    public void updateWithFlavor(DishDto dishDto);

    //删除菜品信息
    public void deleteWithFlavor(Long id);

}
