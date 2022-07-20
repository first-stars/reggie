package com.w.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.w.reggie.common.R;
import com.w.reggie.common.RedisClear;
import com.w.reggie.dto.DishDto;
import com.w.reggie.entity.Category;
import com.w.reggie.entity.Dish;
import com.w.reggie.entity.DishFlavor;
import com.w.reggie.service.ICategoryService;
import com.w.reggie.service.IDishFlavorService;
import com.w.reggie.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜品管理 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService dishService;

    @Autowired
    private IDishFlavorService dishFlavorService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisClear redisClear;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping()
    public R<String> save(@RequestBody DishDto dishDto){
      log.info(dishDto.toString());
      dishService.saveWithFlavor(dishDto);
        //清理所有菜品的缓存数据
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //清理某个分类下面的菜品的缓存数据
        String key="dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);
        return R.success("新增菜品成功");
    }


    /**
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();


        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByAsc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);


        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getCategoryId();//分类Id
            Category category = categoryService.getById(id);

            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping()
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);

        //清理所有菜品的缓存数据
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //清理某个分类下面的菜品的缓存数据
        String key="dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key);
        return R.success("修改菜品成功");
    }

    /**
     * 菜品停售，起售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status,Long ids){
        log.info("状态{}，id{}",status,ids);
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId,ids);
        Dish dish = dishService.getById(ids);
        dish.setStatus(status);
        dishService.updateById(dish);
        //清理所有菜品的缓存数据
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return R.success("修改成功");
    }

    /**
     * 删除菜品信息
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> delete(Long ids){
        dishService.deleteWithFlavor(ids);
        redisClear.clear();
        return R.success("删除成功");
    }

    /**
     * 查询全部菜品信息
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        List<DishDto> dtoList=null;

        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();//dish_12345678_1
        //先从redis中获取数据
        dtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dtoList!=null){
            //如果存在，直接返回
            return R.success(dtoList);
        }


        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.orderByDesc(Dish::getSort).orderByAsc(Dish::getUpdateTime).eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(queryWrapper);
        dtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> list1 = dishFlavorService.list(queryWrapper1);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());

        //如果不存在，需查询数据库，将查询到的数据缓存到redis
        redisTemplate.opsForValue().set(key,dtoList,60, TimeUnit.MINUTES);
        return R.success(dtoList);
    }
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        queryWrapper.orderByDesc(Dish::getSort).orderByAsc(Dish::getUpdateTime).eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(queryWrapper);
//        return R.success(list);
//    }
}
