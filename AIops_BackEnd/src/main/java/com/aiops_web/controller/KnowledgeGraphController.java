package com.aiops_web.controller;

import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;
import com.aiops_web.entity.sql.AnodetectResult;
import com.aiops_web.entity.sql.RelationshipEnum;
import com.aiops_web.service.RelationshipEnumService;
import com.aiops_web.service.neo4j.KnowledgeGraphService;
import com.aiops_web.service.neo4j.SystemArchitectureService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

@RestController(value = "knowledgeGraphController")
@RequestMapping("/KG")
public class KnowledgeGraphController {

    @Resource
    private KnowledgeGraphService knowledgeGraphService;

    @Resource
    RelationshipEnumService relationshipEnumService;

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

    @RequestMapping(value = "/nodes/multiple", method = RequestMethod.GET)
    public ResponseStd<List<Node>> getNodeListByNodeIds(
            @RequestParam(required = true) List<Long> ids) {
        List<Node> nodes = knowledgeGraphService.getNodeListByNodeIds(ids);
        return new ResponseStd<>(nodes, 200, "success", "返回节点!");
    }

    @RequestMapping(value = "/nodes/ServiceName", method = RequestMethod.GET)
    public ResponseStd<Node> getNodeByName(@RequestParam(required = true) String name) {
        Node nodes = knowledgeGraphService.getNodeByNameInService(name);
        return new ResponseStd<>(nodes, 200, "success", "返回节点!");
    }

    @RequestMapping(value = "/nodes/relevantNodesIds", method = RequestMethod.GET)
    public ResponseStd<List<Long>> getRelevantNodesIdsByNodeIds(@RequestParam(required = true) List<Long> ids) {
        List<Long> nodesIds = knowledgeGraphService.getRelevantNodesIdsByNodeIds(ids);
        return new ResponseStd<>(nodesIds, 200, "success", "返回节点id列表!");
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

    @RequestMapping(value = "/nodes/{nodeId}/children", method = RequestMethod.DELETE)
    public ResponseStd<Boolean> deleteNodeAndChildrenNodeById(@PathVariable Long nodeId) {
        Boolean res = knowledgeGraphService.deleteNodeAndChildrenNodeById(nodeId);
        return new ResponseStd<>(res, 200, "success", "执行删除节点和子节点!");
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

    @RequestMapping(value = "/relationships/multiple", method = RequestMethod.GET)
    public ResponseStd<List<Neo4jRelationshipDto>> getAllRelationshipByIds(
            @RequestParam(required = true) List<Long> ids) {
        List<Neo4jRelationshipDto> relationship = knowledgeGraphService.getAllRelationshipByIds(ids);
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

    @RequestMapping(value = "/relationships/nodeIds", method = RequestMethod.GET)
    public ResponseStd<List<Neo4jRelationshipDto>> getRelevantRelationshipsByNodeIds(
            @RequestParam List<Long> ids) {
        List<Neo4jRelationshipDto> relationship = knowledgeGraphService.getRelevantRelationshipsByNodeIds(ids);
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

    // 生成知识图谱系统信息
    @RequestMapping(value = "/system/architecture", method = RequestMethod.POST)
    public ResponseStd<Boolean> SystemArchitecture() {
        Boolean res = systemArchitectureService.configSystemArchitecture();
        return new ResponseStd<>(res, 200, "success", "返回是否成功!");
    }

    // 知识图谱拖拽节点，更新相应的节点关系以及节点的parentId
    @RequestMapping(value = "/nodes/{nodeId}/changeRelationship", method = RequestMethod.GET)
    public ResponseStd<Boolean> changeNodeRelationship(
            @PathVariable Long nodeId,
            @RequestParam Long oldParentId,
            @RequestParam Long newParentId) {
        Boolean relationship = knowledgeGraphService.changeNodeRelationship(nodeId,
                oldParentId, newParentId);
        return new ResponseStd<>(relationship, 200, "success", "返回节点关系更改是否成功!");
    }

    // 传入List<String>形式的多条根因路径，由节点ids的string形式组成；生成相应的边，同时返回多条根因路径，由关系ids的string形式组成
    @RequestMapping(value = "/relationships/RCNodeIdsList", method = RequestMethod.POST)
    public ResponseStd<List<String>> saveRelationshipsByNodeIdsStringList(
            @RequestParam List<String> RCNodeIdsList) {
        List<String> res = knowledgeGraphService.saveRelationshipsByNodeIdsStringList(RCNodeIdsList);
        return new ResponseStd<>(res, 200, "success", "返回多条根因路径,由关系ids的string形式组成!");
    }

    // 其他表基本增删改查

    // relationship_enum表
    // 增加一个关系类型
    @PostMapping("/relaTypes")
    public ResponseStd<Integer> createRelaType(@RequestBody RelationshipEnum relationshipEnum) {
        boolean saveResult = relationshipEnumService.save(relationshipEnum);
        if (!saveResult) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<Integer>(relationshipEnum.getRelationId());
    }

    // 根据id删除一个关系类型
    @DeleteMapping("/relaTypes/{relationId}")
    public ResponseStd<Boolean> deleteRelaType(@PathVariable Integer relationId) {
        return new ResponseStd<Boolean>(relationshipEnumService.removeById(relationId));
    }

    // 修改一个关系类型
    @PutMapping("/relaTypes")
    public ResponseStd<Boolean> updateRelaType(@RequestBody RelationshipEnum relationshipEnum) {
        return new ResponseStd<Boolean>(relationshipEnumService.updateById(relationshipEnum));
    }

    // 查找全部关系类型
    @GetMapping("/relaTypes")
    public ResponseStd<List<RelationshipEnum>> selectAllRelaTypes() {
        List<RelationshipEnum> relationshipEnumList = relationshipEnumService.list();
        if (relationshipEnumList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<RelationshipEnum>>(relationshipEnumList);
    }

    // 根据id查找某一个关系类型
    @GetMapping("/relaTypes/{relationId}")
    public ResponseStd<RelationshipEnum> selectRelaTypesById(@PathVariable Integer relationId) {
        return new ResponseStd<RelationshipEnum>(relationshipEnumService.getById(relationId));
    }
}
