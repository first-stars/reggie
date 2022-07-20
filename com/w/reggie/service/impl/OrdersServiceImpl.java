package com.w.reggie.service.impl;

import com.w.reggie.entity.Orders;
import com.w.reggie.mapper.OrdersMapper;
import com.w.reggie.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
