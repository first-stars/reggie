package com.w.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.w.reggie.common.R;
import com.w.reggie.dto.SetmealDto;
import com.w.reggie.entity.Category;
import com.w.reggie.entity.Setmeal;
import com.w.reggie.service.ICategoryService;
import com.w.reggie.service.ISetmealDishService;
import com.w.reggie.service.ISetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 套餐 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private ISetmealService setmealService;

    @Autowired
    private ISetmealDishService setmealDishService;

    @Autowired
    private ICategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping()
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 分页功能
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list= records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categortName = category.getName();
                setmealDto.setCategoryName(categortName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);

    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.deleteWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     * 修改菜单信息(获取)
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<SetmealDto> update(@PathVariable long id){
        log.info("获取的数据id{}",id);
        SetmealDto setmealDto = setmealService.updateWithDish(id);
        return R.success(setmealDto);
    }

    /**
     * 修改菜单信息(保存)
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> put(@RequestBody SetmealDto setmealDto){
//        log.info("获取的数据id{}",setmealDto);
        setmealService.putWithDish(setmealDto);
        return R.success("修改成功");
    }

    /**
     * 修改停售或起售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status, @RequestParam Long[] ids){
//        log.info("停售或起售的数据{},装态{}",ids,status);
        for (int i = 0; i < ids.length; i++) {
            Setmeal setmeal = setmealService.getById(ids[i]);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#categoryId+'_'+#status")
    public R<List<Setmeal>> list(@RequestParam Long categoryId,@RequestParam int status){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(categoryId!=null,Setmeal::getCategoryId,categoryId).eq(Setmeal::getStatus,status);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
