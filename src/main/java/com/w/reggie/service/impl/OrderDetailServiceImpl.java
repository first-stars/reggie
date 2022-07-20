package com.w.reggie.service.impl;

import com.w.reggie.entity.OrderDetail;
import com.w.reggie.mapper.OrderDetailMapper;
import com.w.reggie.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
