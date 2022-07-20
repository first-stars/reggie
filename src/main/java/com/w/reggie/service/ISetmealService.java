package com.w.reggie.service;

import com.w.reggie.dto.SetmealDto;
import com.w.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
public interface ISetmealService extends IService<Setmeal> {
    /**
     * 新增菜品套餐
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除菜品套餐
     * @param ids
     */
    public void deleteWithDish(List<Long> ids);

    /**
     * 修改菜品套餐信息(获取)
     * @param id
     * @return
     */
    public SetmealDto updateWithDish(Long id);

    /**
     * 修改菜品套餐信息(保存)
     * @param setmealDto
     */
    public void putWithDish(SetmealDto setmealDto);
}
