package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.ContainsMapper;
import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.entity.neo4j.Pod;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/knowledgeGraph")
public class KnowledgeGraphController {

    @Resource
    private PodMapper podMapper;

    @Resource
    private ContainsMapper containsMapper;

    @GetMapping("/findAll")
    public ResponseStd<List<Pod>> findAll() {
        return new ResponseStd(podMapper.findAll());
    }

    @GetMapping("/Relation/findAll")
    public ResponseStd<List<Pod>> findAllRelations() {
        return new ResponseStd(containsMapper.findAll());
    }
}
