package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.service.neo4j.KnowledgeGraph;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/knowledgeGraph")
public class KnowledgeGraphController {

    @Resource
    private PodMapper podMapper;

    @Resource
    private KnowledgeGraph knowledgeGraph;

    @RequestMapping(value = "/kg/nodes", method = RequestMethod.GET)
    public ResponseStd<List<Node>> getAllNodes() {
        List<Node> nodes = knowledgeGraph.getAllNodeList();
        return new ResponseStd<>(nodes, 200, "success", "成功返回所有节点!");
    }
}
