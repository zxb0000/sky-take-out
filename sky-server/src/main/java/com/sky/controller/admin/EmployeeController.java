package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */

@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理相关的")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("退出登录接口")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }
    @ApiOperation("新增员工接口")
    @PostMapping
    public Result addEmp(@RequestBody EmployeeDTO employeeDTO){
            log.info("新增员工：{}",employeeDTO);
            employeeService.addEmp(employeeDTO);
            return Result.success();
    }
    @ApiOperation("员工分页查询")
    @GetMapping("/page")
    public Result pageQuery(EmployeePageQueryDTO queryDTO){
        PageResult pageResult=employeeService.pageQuery(queryDTO);
        return Result.success(pageResult);
    }
    @ApiOperation("员工账号状态改变")
    @PostMapping("status/{status}")
    public Result statusChange(@PathVariable Integer status,Long id){
        employeeService.statusChange(status,id);
        return  Result.success();
    }

    /**
     * 根据ID获取员工信息
     * @param id 根据ID获取员工信息
     * @return
     */
    @ApiOperation("根据ID获取员工信息")
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
       Employee employee= employeeService.getById(id);
       return Result.success(employee);
    }

    /**
     * 修改员工信息
     * @param employeeDTO
     * @return
     */
    @ApiOperation("修改员工信息")
    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
