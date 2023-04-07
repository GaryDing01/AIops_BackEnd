package com.aiops_web.controller;

import co.elastic.clients.elasticsearch.xpack.usage.Base;
import com.aiops_web.entity.Employee;
import com.aiops_web.dao.EmployeeMapper;
import com.aiops_web.std.BaseResponse;
import com.aiops_web.std.ResponseStd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "employeeHandler")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeMapper employeeMapper;

    @PostMapping("/save")
    public BaseResponse<Integer> save(@RequestBody Employee employee) {
        return ResponseStd.success(employeeMapper.save(employee));
    }

    @DeleteMapping("/deleteByID")
    public BaseResponse<Integer> deleteByID(@RequestBody Integer id){
        return ResponseStd.success(employeeMapper.deleteByID(id));
    }

    @PutMapping("/update")
    public BaseResponse<Integer> update(@RequestBody Employee employee){
        return ResponseStd.success(employeeMapper.update(employee));
    }

    @GetMapping("/findByID/{id}")
    public BaseResponse<Employee> findByID(@PathVariable("id") Integer id) {
        return ResponseStd.success(employeeMapper.findByID(id));
    }

    @GetMapping("/findAll")
    public BaseResponse<List<Employee>> findAll() {
        return ResponseStd.success(employeeMapper.findAll());
    }
}
