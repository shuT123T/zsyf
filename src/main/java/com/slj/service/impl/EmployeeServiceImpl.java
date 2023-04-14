package com.slj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slj.domain.Employee;
import com.slj.service.EmployeeService;
import com.slj.mapper.EmployeeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Mr.shu
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-11-17 14:20:44
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    @Override
    public Page<Employee> getEmployeePage(Integer page, Integer pageSize, String name) {
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        // 添加条件过滤
        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        lqw.orderByDesc(Employee::getUpdateTime);
        // 执行查寻
        this.page(pageInfo, lqw);
        return pageInfo;
    }
}




