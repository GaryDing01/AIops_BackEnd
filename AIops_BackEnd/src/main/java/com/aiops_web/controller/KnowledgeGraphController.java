package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.ContainsMapper;
import com.aiops_web.dao.neo4j.NodeMapper;
import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.std.ResponseStd;
import org.elasticsearch.node.NodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/knowledgeGraph")
public class KnowledgeGraphController {

    @Resource
    private PodMapper podMapper;

    @Resource
    private ContainsMapper containsMapper;

    @Resource
    private KnowledgeGraphService knowledgeGraphService;

//    @GetMapping("/findAll")
//    public ResponseStd<List<Pod>> findAll() {
//        return new ResponseStd(podMapper.findAll());
//    }
//
//    @GetMapping("/Relation/findAll")
//    public ResponseStd<List<Pod>> findAllRelations() {
//        return new ResponseStd(containsMapper.findAll());
//    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public ResponseStd<List<Node>> getNodes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Long parentId) {
        List<Node> nodes = knowledgeGraphService.getNodeList(id, type, name, content, parentId);
        return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    }

    @RequestMapping(value = "/relationships", method = RequestMethod.GET)
    public ResponseStd<List<Relationship>> getRelationships() {
        List<Relationship> relationships = knowledgeGraphService.getRelationshipList();
        return new ResponseStd<>(relationships, 200, "success", "成功返回关系!");
    }

    @RequestMapping(value = "/relationships/{relationshipId}", method = RequestMethod.GET)
    public ResponseStd<Relationship> getRelationships(
            @PathVariable Long relationshipId) {
        Relationship relationship = knowledgeGraphService.getRelationshipById(relationshipId);
        return new ResponseStd<>(relationship, 200, "success", "成功返回关系!");
    }
}
