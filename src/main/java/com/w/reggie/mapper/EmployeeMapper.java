package com.w.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.w.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xin
 * @date 2022-07-10-15:33
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
