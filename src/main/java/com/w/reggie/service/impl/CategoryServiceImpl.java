package com.w.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.w.reggie.common.CustomException;
import com.w.reggie.entity.Category;
import com.w.reggie.entity.Dish;
import com.w.reggie.entity.Setmeal;
import com.w.reggie.mapper.CategoryMapper;
import com.w.reggie.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w.reggie.service.IDishService;
import com.w.reggie.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IDishService dishService;

    @Autowired
    private ISetmealService setmealService;


    /**
     * 根据id删除分类，删除之前进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已关联，抛出一个业务异常
        if (count1>0){
            //已关联菜品，抛出异常
            throw new CustomException("当前分类关连了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        //查询当前分类是否关联了套餐，如果已关联，抛出一个业务异常
        if (count2>0){
            //已关联套餐，抛出异常
            throw new CustomException("当前分类关连了套餐，不能删除");
        }
        //没有关联，正常删除
        super.removeById(id);
    }
}
