package com.aiops_web.service.neo4j;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.aiops_web.dao.neo4j.Neo4jNodeDao;
import com.aiops_web.dao.neo4j.Neo4jRelationshipDao;
import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;

@Service
public class KnowledgeGraphService {

    @Resource
    private Neo4jNodeDao neo4jNodeDao;

    @Resource
    private Neo4jRelationshipDao neo4jRelationshipDao;

    public Long addNode(Node node) {
        node.setId(null);
        if (node.getType() == "" || node.getType() == null)
            throw new BadRequestException("type 不能为空!");
        if (node.getName() == "" || node.getName() == null)
            throw new BadRequestException("name 不能为空!");
        neo4jNodeDao.save(node);
        return node.getId();
    }

    public List<Node> getNodeList(Long id, String type, String name, String content, Long parentId) {
        Node model = new Node();
        if (id != null)
            model.setId(id);
        if (type != null && type != "")
            model.setType(type);
        if (name != null && name != "")
            model.setName(name);
        if (content != null && content != "")
            model.setContent(content);
        if (parentId != null)
            model.setParentId(parentId);

        List<Node> nodes = neo4jNodeDao.findAll(Example.of(model));
        return nodes;
    }

    public Node getNodeById(Long id) {
        Node node = neo4jNodeDao.findById(id).orElse(null);
        return node;
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

    public List<Neo4jRelationshipDto> getRelationshipList() {
        List<Neo4jRelationshipDto> relationships = neo4jRelationshipDao.findAllRelationship();
        return relationships;
    }

    public Neo4jRelationshipDto getRelationshipById(Long id) {
        Neo4jRelationshipDto relationships = neo4jRelationshipDao.findRelationshipById(id);
        return relationships;
    }

    // public Relationship findRelatinoshipById(Long id) {
    // Relationship relationships = neo4jRelationshipDao.findById(id).orElse(null);
    // return relationships;
    // }

}
