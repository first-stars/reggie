package com.w.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.w.reggie.common.BaseContext;
import com.w.reggie.common.R;
import com.w.reggie.entity.AddressBook;
import com.w.reggie.service.IAddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 地址管理 前端控制器
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private IAddressBookService addressBookService;

    /**
     * 添加地址
     * @param addressBook
     * @param session
     * @return
     */
    @PostMapping()
    public R<String> save(@RequestBody AddressBook addressBook, HttpSession session){
        log.info("{}",addressBook);
        Long userId = (Long) session.getAttribute("user");
        addressBook.setUserId(userId);
        addressBookService.save(addressBook);
        return R.success("地址添加成功");
    }

    /**
     * 查询全部地址
     * @param session
     * @return
     */
    @GetMapping("list")
    public R<List<AddressBook>> select(HttpSession session){
        Long userId = (Long) session.getAttribute("user");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,userId);
        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }

    @PutMapping("/default")
    public R<AddressBook> de(@RequestBody AddressBook addressBook){
        log.info("",addressBook);
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);
        // 设置默认地址为1
        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    /**
     * 获取地址(修改)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> update(@PathVariable Long id){
        AddressBook byId = addressBookService.getById(id);
        return R.success(byId);
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping()
    public R<String> put(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }

    /**
     * 获取默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> get(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return R.success(addressBook);
    }



}
