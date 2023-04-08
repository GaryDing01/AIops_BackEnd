package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.entity.neo4j.Pod;
import com.aiops_web.entity.sql.Employee;
import com.aiops_web.std.ResponseStd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/knowledgeGraph")
public class KnowledgeGraphController {

    @Autowired
    private PodMapper podMapper;

    @GetMapping("/findAll")
    public ResponseStd<List<Pod>> findAll() {
        return new ResponseStd(podMapper.findAll());
    }
}
