package com.w.reggie.service.impl;

import com.w.reggie.entity.Employee;
import com.w.reggie.mapper.EmployeeMapper;
import com.w.reggie.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工信息 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-07-10
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
