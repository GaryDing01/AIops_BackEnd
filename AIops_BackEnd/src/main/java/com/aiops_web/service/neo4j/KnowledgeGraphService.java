package com.aiops_web.service.neo4j;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.aiops_web.dao.neo4j.Neo4jNodeDao;
import com.aiops_web.dao.neo4j.Neo4jRelationshipDao;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;

@Service
public class KnowledgeGraphService {

    @Resource
    private Neo4jNodeDao neo4jNodeDao;

    @Resource
    private Neo4jRelationshipDao neo4jRelationshipDao;

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

    public List<Relationship> getRelationshipList() {
        List<Relationship> relationships = neo4jRelationshipDao.findAllRelationship();
        System.out.println("relationships: " + relationships);
        return relationships;
    }

    public Relationship getRelationshipById(Long id) {
        Relationship relationships = neo4jRelationshipDao.findRelationshipById(id);
        return relationships;
    }

}
