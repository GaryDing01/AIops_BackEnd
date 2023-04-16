package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.std.ResponseStd;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/KG")
public class KnowledgeGraphController {

    @Resource
    private PodMapper podMapper;

    @Resource
    private KnowledgeGraphService knowledgeGraph;

    // @RequestMapping(value = "/nodes/all", method = RequestMethod.GET)
    // public ResponseStd<List<Node>> getAllNodes() {
    // List<Node> nodes = knowledgeGraph.getAllNodeList();
    // return new ResponseStd<>(nodes, 200, "success", "成功返回所有节点!");
    // }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public ResponseStd<List<Node>> getNodes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Long parentId) {
        List<Node> nodes = knowledgeGraph.getNodeList(id, type, name, content, parentId);
        return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    }

    // @RequestMapping(value = "/nodes/node", method = RequestMethod.GET)
    // public ResponseStd<List<Node>> getNodesByNode(@RequestBody Node node) {
    // List<Node> nodes = knowledgeGraph.getNodeListByNode(node);
    // return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    // }

    // @RequestMapping(value = "/nodes/{nodeId}", method = RequestMethod.GET)
    // public ResponseStd<Node> getNodeById(@PathVariable Long nodeId) {
    // Node node = knowledgeGraph.getNodeById(nodeId);
    // return new ResponseStd<>(node, 200, "success", "成功返回节点!");
    // }

    // @RequestMapping(value = "/nodes/type", method = RequestMethod.GET)
    // public ResponseStd<List<Node>> getNodesByType(@RequestParam(required = true)
    // String nodeType) {
    // List<Node> nodes = knowledgeGraph.getNodeListByType(nodeType);
    // return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    // }

    @RequestMapping(value = "/relationships", method = RequestMethod.GET)
    public ResponseStd<List<Relationship>> getRelationships() {
        List<Relationship> relationships = knowledgeGraph.getRelationshipList();
        return new ResponseStd<>(relationships, 200, "success", "成功返回关系!");
    }

    @RequestMapping(value = "/relationships/{relationshipId}", method = RequestMethod.GET)
    public ResponseStd<Neo4jRelationshipDto> getRelationships(
            @PathVariable Long relationshipId) {
        Neo4jRelationshipDto relationship = knowledgeGraph.getRelationshipById(relationshipId);
        return new ResponseStd<>(relationship, 200, "success", "成功返回关系!");
    }
}
