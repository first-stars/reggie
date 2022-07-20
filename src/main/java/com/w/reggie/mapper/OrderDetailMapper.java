package com.w.reggie.mapper;

import com.w.reggie.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单明细表 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
