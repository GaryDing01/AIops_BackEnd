package com.aiops_web.service.neo4j;

import com.aiops_web.dao.neo4j.NodeMapper;
import com.aiops_web.dao.neo4j.RelationShipMapper;
import com.aiops_web.entity.neo4j.Node;
import com.aiops_web.entity.neo4j.Relationship;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KnowledgeGraphService {
    @Resource
    private NodeMapper nodeMapper;

    @Resource
    private RelationShipMapper relationShipMapper;

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

        List<Node> nodes = nodeMapper.findAll();
        return nodes;
    }

    public List<Relationship> getRelationshipList() {
        List<Relationship> relationships = relationShipMapper.findAllRelationship();
        System.out.println("relationships: " + relationships);
        return relationships;
    }

    public Relationship getRelationshipById(Long id) {
        Relationship relationships = relationShipMapper.findRelationshipById(id);
        return relationships;
    }
}
