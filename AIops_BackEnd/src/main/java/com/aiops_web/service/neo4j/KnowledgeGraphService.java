package com.aiops_web.service.neo4j;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.aiops_web.dao.neo4j.Neo4jNodeDao;
import com.aiops_web.dao.neo4j.Neo4jRelationshipDao;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;

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

    public Boolean deleteNodeById(Long id) {
        Node node = neo4jNodeDao.findById(id).orElse(null);
        if (node == null)
            return false;
        neo4jNodeDao.deleteById(id);
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

}
