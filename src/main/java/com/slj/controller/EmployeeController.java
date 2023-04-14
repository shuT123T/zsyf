package com.slj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slj.common.Result;
import com.slj.domain.Employee;
import com.slj.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*
 * @Author shu
 * @Create 2022-10-28
 */
@Slf4j
@RestController
@RequestMapping("/employee")
@Api(tags = "员工管理接口")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 将页面提交的密码 password 进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 根据页面提交的用户名 username 查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        // 如果没有查询到则返回登录失败结果
        if (emp == null)
            return Result.error("登陆失败！账号错误");

        // 密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password))
            return Result.error("登陆失败！密码错误");

        // 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0)
            return Result.error("账号已禁用");

        // 登录成功，将给员工 id 存入 Session 并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工{}", employee.toString());

        // 设置初始密码 123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 获取当前用户的 id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        boolean f = employeeService.save(employee);
        return f ? Result.success("新增员工成功") : Result.error("新增员工失败");
    }

    /**
     * 员工分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page<Employee>> page(Integer page, Integer pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        Page<Employee> pageInfo = employeeService.getEmployeePage(page, pageSize, name);
        return Result.success(pageInfo);
    }

//    private Page<Employee> getEmployeePage(Integer page, Integer pageSize, String name) {
//        //构造分页构造器
//        Page<Employee> pageInfo = new Page<>(page, pageSize);
//
//        // 构造条件构造器
//        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
//        // 添加条件过滤
//        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        // 添加排序条件
//        lqw.orderByDesc(Employee::getUpdateTime);
//        // 执行查寻
//        employeeService.page(pageInfo, lqw);
//        return pageInfo;
//    }

    /**
     * 根据id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping("")
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
//        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        long id = Thread.currentThread().getId();
        employeeService.updateById(employee);
        return Result.success("修改成功");
    }

    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null)
            return Result.success(employee);
        return Result.error("没有查询到对应的员工信息");
    }
}
