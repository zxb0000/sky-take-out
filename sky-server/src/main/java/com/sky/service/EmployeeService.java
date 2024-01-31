package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void addEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param queryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO queryDTO);

    /**
     * 改变员工账号状态
     * @param status
     * @param id
     */
    void statusChange(Integer status, Long id);
}
