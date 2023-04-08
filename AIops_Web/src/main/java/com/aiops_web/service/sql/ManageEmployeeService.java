package com.aiops_web.service.sql;

import com.aiops_web.dao.sql.EmployeeMapper;
import com.aiops_web.entity.sql.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> findAllEmployee() {
        System.out.println("Try to find all Employees!");
        return employeeMapper.findAll();
    }

}
