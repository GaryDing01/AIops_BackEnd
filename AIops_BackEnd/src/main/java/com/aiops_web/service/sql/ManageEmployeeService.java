package com.aiops_web.service.sql;

import com.aiops_web.dao.sql.EmployeeMapper;
import com.aiops_web.entity.sql.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManageEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    public Employee findEmployeeByID(int id) {
        System.out.println("Find one employee by ID!");
        return employeeMapper.findByID(id);
    }

    public List<Employee> findAllEmployee() {
        System.out.println("Try to find all Employees!");
        return employeeMapper.findAll();
    }

}
