package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        //获取密码并进行MD5处理
        String password = employeeLoginDTO.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("MD5加密密码：{}",password);
        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 增加新员工
     * @param employeeDTO 员工信息的传输模型
     */
    @Override
    public void addEmp(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        //完善剩余的属性
        employee.setStatus(StatusConstant.ENABLE);//账号状态
        //employee.setCreateTime(LocalDateTime.now());
       // employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //上传者的信息
        //employee.setCreateUser(BaseContext.getCurrentId());
        //employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }

    /**
     * 员工分页查询
     * @param queryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(),queryDTO.getPageSize());

        Page<Employee> page= employeeMapper.pageSelect(queryDTO.getName());

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 改变员工账号的状态
     * @param status
     * @param id
     */
    @Override
    public void statusChange(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id (id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据ID获取员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getById(Integer id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("*****");
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        //将DTO转化为实体类
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        //补齐参数
        //employee.setUpdateUser(BaseContext.getCurrentId());
        //employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }

}
