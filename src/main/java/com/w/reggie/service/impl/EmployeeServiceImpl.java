package com.w.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.w.reggie.entity.Employee;
import com.w.reggie.mapper.EmployeeMapper;
import com.w.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author xin
 * @date 2022-07-10-16:33
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>  implements EmployeeService {
}
