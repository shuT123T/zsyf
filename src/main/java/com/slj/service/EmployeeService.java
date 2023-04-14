package com.slj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.domain.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mr.shu
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2022-11-17 14:20:44
*/
public interface EmployeeService extends IService<Employee> {

    Page<Employee> getEmployeePage(Integer page, Integer pageSize, String name);
}
