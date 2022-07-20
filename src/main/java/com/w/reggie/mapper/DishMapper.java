package com.w.reggie.mapper;

import com.w.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜品管理 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
