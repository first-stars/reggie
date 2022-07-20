package com.w.reggie.service.impl;

import com.w.reggie.entity.Category;
import com.w.reggie.mapper.CategoryMapper;
import com.w.reggie.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
