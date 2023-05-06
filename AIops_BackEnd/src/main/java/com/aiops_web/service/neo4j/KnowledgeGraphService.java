package com.aiops_web.service.neo4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.aiops_web.dao.neo4j.Neo4jNodeDao;
import com.aiops_web.dao.neo4j.Neo4jRelationshipDao;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.alibaba.fastjson.JSONObject;

@Service
public class KnowledgeGraphService {

    @Resource
    private Neo4jNodeDao neo4jNodeDao;

    @Resource
    private Neo4jRelationshipDao neo4jRelationshipDao;

    // Node
    public Long addNode(Node node) {
        node.setId(null);
        if (node.getType().isEmpty() || node.getType() == null)
            throw new BadRequestException("type 不能为空!");
        if (node.getName().isEmpty() || node.getName() == null)
            throw new BadRequestException("name 不能为空!");
        neo4jNodeDao.save(node);
        return node.getId();
    }

    public List<Node> getNodeList(Long id, String type, String name, String content, Long parentId) {
        Node model = new Node();
        if (id != null)
            model.setId(id);
        if (type != null && !type.isEmpty())
            model.setType(type);
        if (name != null && !name.isEmpty())
            model.setName(name);
        if (content != null && !content.isEmpty())
            model.setContent(content);
        if (parentId != null)
            model.setParentId(parentId);

        List<Node> nodes = neo4jNodeDao.findAll(Example.of(model));
        return nodes;
    }

    public List<Node> getNodeListByNodeIds(List<Long> ids) {
        List<Node> nodes = neo4jNodeDao.findAllByIds(ids);
        return nodes;
    }

    public Boolean deleteNodeById(Long id) {
        Node node = neo4jNodeDao.findById(id).orElse(null);
        if (node == null)
            return false;
        neo4jNodeDao.deleteById(id);
        return true;
    }

    public Boolean deleteNodeAndChildrenNodeById(Long id) {
        Node node = neo4jNodeDao.findById(id).orElse(null);
        if (node == null)
            return false;
        List<Long> nodeList = new ArrayList<>();
        nodeList.add(id);
        List<Long> childrenids = getChildrenNodesIdsByNodesIds(nodeList);
        nodeList.addAll(childrenids);
        neo4jNodeDao.deleteByIds(nodeList);
        return true;
    }

    public Boolean deleteNodesByIds(List<Long> ids) {
        neo4jNodeDao.deleteByIds(ids);
        return true;
    }

    public Boolean updateNodeByNode(Node node) {
        if (node.getId() == null)
            throw new BadRequestException("id 不能为空!");
        if (node.getType().isEmpty() || node.getType() == null)
            throw new BadRequestException("type 不能为空!");
        if (node.getName().isEmpty() || node.getName() == null)
            throw new BadRequestException("name 不能为空!");
        Node oldN = neo4jNodeDao.findById(node.getId()).orElse(null);
        if (oldN == null)
            return false;

        neo4jNodeDao.save(node);
        return true;
    }

    public Boolean changeNodeRelationship(Long nodeId, Long oldParentId, Long newParentId) {
        Node node = neo4jNodeDao.findById(nodeId).orElse(null);
        Node oldParentNode = neo4jNodeDao.findById(oldParentId).orElse(null);
        Node newParentNode = neo4jNodeDao.findById(newParentId).orElse(null);
        if (node == null || oldParentNode == null || newParentNode == null)
            return false;
        // 删除旧的contains关系
        List<Neo4jRelationshipDto> oldRs = neo4jRelationshipDao.findRelationshipByStartIdAndEndIdAndType(oldParentId,
                nodeId, Neo4jRelationshipEnum.Contains);
        List<Long> oldRIds = new ArrayList<>();
        for (Neo4jRelationshipDto r : oldRs) {
            oldRIds.add(r.getRId());
        }
        neo4jRelationshipDao.deleteRelationshipsByIds(oldRIds);

        // 添加新的contains关系
        JSONObject content = new JSONObject();
        content.put("name", Neo4jRelationshipEnum.Contains);
        neo4jRelationshipDao.addRelationship(Neo4jRelationshipEnum.Contains, content.toJSONString(), newParentId,
                nodeId);

        node.setParentId(newParentId);
        neo4jNodeDao.save(node);
        return true;
    }

    // Relationship
    public Long addRelationship(Neo4jRelationshipDto relationshipDto) {
        relationshipDto.setRId(null);
        Long rId = neo4jRelationshipDao.addRelationship(relationshipDto.getType(), relationshipDto.getContent(),
                relationshipDto.getStartId(), relationshipDto.getEndId());
        return rId;
    }

    public List<Neo4jRelationshipDto> getRelationshipList() {
        List<Neo4jRelationshipDto> relationships = neo4jRelationshipDao.findAllRelationships();
        return relationships;
    }

    public Neo4jRelationshipDto getRelationshipById(Long id) {
        Neo4jRelationshipDto relationship = neo4jRelationshipDao.findRelationshipById(id);
        return relationship;
    }

    public List<Neo4jRelationshipDto> getAllRelationshipByIds(List<Long> ids) {
        List<Neo4jRelationshipDto> relationships = neo4jRelationshipDao.findAllRelationshipByIds(ids);
        return relationships;
    }

    public List<Neo4jRelationshipDto> getRelationshipByStartIdAndEndId(Long startId, Long EndId) {
        List<Neo4jRelationshipDto> relationship = neo4jRelationshipDao.findRelationshipByStartIdAndEndId(startId,
                EndId);
        return relationship;
    }

    public Boolean deleteRelationshipById(Long id) {
        Neo4jRelationshipDto relationship = neo4jRelationshipDao.findRelationshipById(id);
        if (relationship == null)
            return false;
        neo4jRelationshipDao.deleteRelationshipById(id);
        return true;
    }

    public Boolean deleteRelationshipsByIds(List<Long> ids) {
        neo4jRelationshipDao.deleteRelationshipsByIds(ids);
        return true;
    }

    public Boolean updateRelationship(Neo4jRelationshipDto relationshipDto) {
        if (relationshipDto.getRId() == null)
            throw new BadRequestException("rId 不能为空!");

        Neo4jRelationshipDto oldR = neo4jRelationshipDao.findRelationshipById(relationshipDto.getRId());
        if (oldR == null)
            throw new BadRequestException("图谱中无此关系!");

        neo4jRelationshipDao.updateRelationship(relationshipDto.getRId(), relationshipDto.getType(),
                relationshipDto.getContent());
        return true;
    }

    // public Relationship findRelatinoshipById(Long id) {
    // Relationship relationships = neo4jRelationshipDao.findById(id).orElse(null);
    // return relationships;
    // }

    // 清空neo4j数据库中的所有数据
    public Boolean deleteAllData() {
        neo4jNodeDao.deleteAllData();
        return true;
    }

    // 知识图谱补充功能

    // 根据Service节点的名字获取节点信息
    public Node getNodeByNameInService(String name) {
        Node nodes = neo4jNodeDao.findNodeByNameInService(name);
        return nodes;
    }

    // 根据节点ids递归获取节点列表中节点的所有子节点ids
    private List<Long> getChildrenNodesIdsByNodesIds(List<Long> ids) {
        List<Long> nodesIds = neo4jNodeDao.findChildrenNodesIdsByNodesIds(ids);
        return nodesIds;
    }

    // 根据节点ids递归获取节点列表中节点的所有父节点ids
    private List<Long> getParentNodesIdsByNodeIds(List<Long> ids) {
        List<Long> parentids = neo4jNodeDao.findParentNodesIdsByNodesIds(ids);
        return parentids;
    }

    // 根据Service节点ids获取节点列表中节点的所有相关Pod、Node节点ids
    private List<Long> getServiceIdsRelevantPodAndNode(List<Long> ids) {
        List<Long> NodesIds = neo4jNodeDao.findServiceIdsRelevantNode(ids);
        List<Long> PodsIds = neo4jNodeDao.findServiceIdsRelevantPod(ids);
        List<Long> allList = new ArrayList<>();
        allList.addAll(NodesIds);
        allList.addAll(PodsIds);
        return allList;
    }

    // 根据Service节点ids获取节点列表中节点的所有相关节点ids。返回值包含传入的ids，已去重
    public List<Long> getRelevantNodesIdsByNodeIds(List<Long> ids) {
        // 获取子节点ids
        List<Long> childrenNodesIds = getChildrenNodesIdsByNodesIds(ids);
        // 获取父节点ids
        List<Long> parentNodesIds = getParentNodesIdsByNodeIds(ids);
        // 获取Pod、Node节点ids
        List<Long> PodAndNodeNodesIds = getServiceIdsRelevantPodAndNode(ids);
        Set<Long> allIds = new HashSet<>();
        allIds.addAll(childrenNodesIds);
        allIds.addAll(parentNodesIds);
        allIds.addAll(PodAndNodeNodesIds);
        allIds.addAll(ids);
        return new ArrayList<>(allIds);
    }

    // 根据节点ids获取相关的relationshipdto
    public List<Neo4jRelationshipDto> getRelevantRelationshipsByNodeIds(List<Long> ids) {
        List<Neo4jRelationshipDto> relationships = neo4jRelationshipDao.findRelevantRelationshipsByNodeIds(ids);
        return relationships;
    }

    public List<String> saveRelationshipsByNodeIdsStringList(List<String> RCNodeIdsList) {
        List<String> RCRelationshipIdsList = new ArrayList<>();
        for (String ids : RCNodeIdsList) {
            // 一条根因路径的节点ids
            Long[] idsArray = Arrays.stream(ids.split("\\|")).map(s -> Long.parseLong(s.trim())).toArray(Long[]::new);
            System.out.println("idsArray:" + idsArray);
            // 一条根因路径的关系ids
            Long[] RIds = new Long[idsArray.length - 1];
            // 判断每两个id之间是否存在关系，若不存在，则生成一个新的relationship
            for (int i = 0; i < idsArray.length - 1; i++) {
                Long startId = idsArray[i];
                Long endId = idsArray[i + 1];
                List<Neo4jRelationshipDto> relationships = neo4jRelationshipDao
                        .findRelationshipByStartIdAndEndIdAndType(startId, endId, Neo4jRelationshipEnum.Invokes);
                if (relationships.size() == 0) {
                    Neo4jRelationshipDto neo4jRelationshipDto = new Neo4jRelationshipDto();
                    neo4jRelationshipDto.setStartId(startId);
                    neo4jRelationshipDto.setEndId(endId);
                    neo4jRelationshipDto.setType(Neo4jRelationshipEnum.Invokes);
                    JSONObject content = new JSONObject();
                    content.put("name", Neo4jRelationshipEnum.Invokes);
                    neo4jRelationshipDto.setContent(content.toJSONString());
                    Long newRId = addRelationship(neo4jRelationshipDto);
                    RIds[i] = newRId;
                } else {
                    RIds[i] = relationships.get(0).getRId();
                }
            }
            // 将一条根因路径的关系ids以'|'分隔形成字符串，存入RCRelationshipIdsList
            String RCRelationshipIds = StringUtils.join(RIds, "|");
            RCRelationshipIdsList.add(RCRelationshipIds);
        }
        return RCRelationshipIdsList;
    }
}
