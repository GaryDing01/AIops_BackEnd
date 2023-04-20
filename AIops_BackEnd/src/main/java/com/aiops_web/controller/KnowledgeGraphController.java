package com.aiops_web.controller;

import com.aiops_web.dao.neo4j.PodMapper;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.service.neo4j.SystemArchitectureService;
import com.aiops_web.std.ResponseStd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/KG")
public class KnowledgeGraphController {

    @Resource
    private PodMapper podMapper;

    @Resource
    private KnowledgeGraphService knowledgeGraphService;

    // Node
    @RequestMapping(value = "/nodes", method = RequestMethod.POST)
    public ResponseStd<Long> addNode(@RequestBody Node node) {
        Long newId = knowledgeGraphService.addNode(node);
        return new ResponseStd<>(newId, 200, "success", "返回节点Id!");
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public ResponseStd<List<Node>> getNodes(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Long parentId) {
        List<Node> nodes = knowledgeGraphService.getNodeList(id, type, name, content, parentId);
        return new ResponseStd<>(nodes, 200, "success", "返回节点!");
    }

    @RequestMapping(value = "/nodes/{nodeId}", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> deleteNodeById(@PathVariable Long nodeId) {
        Boolean res = knowledgeGraphService.deleteNodeById(nodeId);
        return new ResponseStd<>(res, 200, "success", "执行删除!");
    }

    @RequestMapping(value = "/nodes/multiple", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> deleteNodesByIds(@RequestParam(required = true) List<Long> ids) {
        Boolean res = knowledgeGraphService.deleteNodesByIds(ids);
        return new ResponseStd<>(res, 200, "success", "执行删除!");
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.PUT)
    public ResponseStd<Boolean> updateNodeByNode(@RequestBody(required = true) Node node) {
        Boolean res = knowledgeGraphService.updateNodeByNode(node);
        if (res == false)
            return new ResponseStd<>(res, 40000, "failure", "执行更新失败!");
        return new ResponseStd<>(res, 200, "success", "执行更新!");
    }

    // @RequestMapping(value = "/nodes/node", method = RequestMethod.GET)
    // public ResponseStd<List<Node>> getNodesByNode(@RequestBody Node node) {
    // List<Node> nodes = knowledgeGraphService.getNodeListByNode(node);
    // return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    // }

    // @RequestMapping(value = "/nodes/{nodeId}", method = RequestMethod.GET)
    // public ResponseStd<Node> getNodeById(@PathVariable Long nodeId) {
    // Node node = knowledgeGraphService.getNodeById(nodeId);
    // return new ResponseStd<>(node, 200, "success", "成功返回节点!");
    // }

    // @RequestMapping(value = "/nodes/type", method = RequestMethod.GET)
    // public ResponseStd<List<Node>> getNodesByType(@RequestParam(required = true)
    // String nodeType) {
    // List<Node> nodes = knowledgeGraphService.getNodeListByType(nodeType);
    // return new ResponseStd<>(nodes, 200, "success", "成功返回节点!");
    // }

    // Relationship
    @RequestMapping(value = "/relationships", method = RequestMethod.POST)
    public ResponseStd<Long> addRelationship(@RequestBody Neo4jRelationshipDto relationshipDto) {
        Long newRId = knowledgeGraphService.addRelationship(relationshipDto);
        return new ResponseStd<>(newRId, 200, "success", "返回关系Id!");
    }

    @RequestMapping(value = "/relationships", method = RequestMethod.GET)
    public ResponseStd<List<Neo4jRelationshipDto>> getRelationships() {
        List<Neo4jRelationshipDto> relationships = knowledgeGraphService.getRelationshipList();
        return new ResponseStd<>(relationships, 200, "success", "返回关系!");
    }

    @RequestMapping(value = "/relationships/{relationshipId}", method = RequestMethod.GET)
    public ResponseStd<Neo4jRelationshipDto> getRelationshipById(@PathVariable Long relationshipId) {
        Neo4jRelationshipDto relationship = knowledgeGraphService.getRelationshipById(relationshipId);
        return new ResponseStd<>(relationship, 200, "success", "返回关系!");
    }

    @RequestMapping(value = "/relationships/nodeId", method = RequestMethod.GET)
    public ResponseStd<List<Neo4jRelationshipDto>> getRelationshipByStartIdAndEndId(
            @RequestParam Long startId,
            @RequestParam Long endId) {
        List<Neo4jRelationshipDto> relationship = knowledgeGraphService.getRelationshipByStartIdAndEndId(startId,
                endId);
        return new ResponseStd<>(relationship, 200, "success", "返回关系!");
    }

    @RequestMapping(value = "/relationships/{relationshipId}", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> deleteRelationshipById(@PathVariable Long relationshipId) {
        Boolean res = knowledgeGraphService.deleteRelationshipById(relationshipId);
        return new ResponseStd<>(res, 200, "success", "执行删除!");
    }

    @RequestMapping(value = "/relationships/multiple", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> deleteRelationshipsByIds(@RequestParam(required = true) List<Long> ids) {
        Boolean res = knowledgeGraphService.deleteRelationshipsByIds(ids);
        return new ResponseStd<>(res, 200, "success", "执行删除!");
    }

    @RequestMapping(value = "/relationships", method = RequestMethod.PUT)
    public ResponseStd<Boolean> updateRelationship(
            @RequestBody(required = true) Neo4jRelationshipDto relationshipDto) {
        System.out.println("relationshipDto: " + relationshipDto);
        Boolean res = knowledgeGraphService.updateRelationship(relationshipDto);
        if (res == null || res == false)
            return new ResponseStd<>(res, 40000, "failure", "执行更新失败!");
        return new ResponseStd<>(res, 200, "success", "执行更新!");
    }

    // clear database
    @RequestMapping(value = "/clearDatabase", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> clearDatabase() {
        Boolean res = knowledgeGraphService.deleteAllData();
        return new ResponseStd<>(res, 200, "success", "清空neo4j数据库!");
    }

    @Resource
    private SystemArchitectureService systemArchitectureService;

    @RequestMapping(value = "/system/architecture", method = RequestMethod.POST)
    public ResponseStd<Boolean> SystemArchitecture() {
        Boolean res = systemArchitectureService.configSystemArchitecture();
        return new ResponseStd<>(res, 200, "success", "返回是否成功!");
    }
}
