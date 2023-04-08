package com.aiops_web.controller;

import com.aiops_web.entity.sql.Employee;
import com.aiops_web.dao.sql.EmployeeMapper;
import com.aiops_web.service.sql.ManageEmployeeService;
import com.aiops_web.std.ResponseStd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "employeeHandler")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private ManageEmployeeService manageEmployeeService;

//    @PostMapping("/save")
//    public ResponseStd<Integer> save(@RequestBody Employee employee) {
//        return new ResponseStd(employeeMapper.save(employee));
//    }
//
//    @DeleteMapping("/deleteByID")
//    public ResponseStd<Integer> deleteByID(@RequestBody Integer id){
//        return new ResponseStd(employeeMapper.deleteByID(id));
//    }
//
//    @PutMapping("/update")
//    public ResponseStd<Integer> update(@RequestBody Employee employee){
//        return new ResponseStd(employeeMapper.update(employee));
//    }
//
//    @GetMapping("/findByID/{id}")
//    public ResponseStd<Employee> findByID(@PathVariable("id") Integer id) {
//        return new ResponseStd(employeeMapper.findByID(id));
//    }

    @GetMapping("/findAll")
    public ResponseStd<List<Employee>> findAll() {
        return new ResponseStd(manageEmployeeService.findAllEmployee());
    }
}
