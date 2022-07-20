package com.w.reggie.mapper;

import com.w.reggie.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
