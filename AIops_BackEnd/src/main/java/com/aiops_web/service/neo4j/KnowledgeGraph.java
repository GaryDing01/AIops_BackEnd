package com.aiops_web.service.neo4j;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aiops_web.dao.neo4j.Neo4jNodeDao;
import com.aiops_web.entity.neo4j.Node;

@Service
public class KnowledgeGraph {
    
    @Resource
    private Neo4jNodeDao neo4jNodeDao;

    public List<Node> getAllNodeList() {
        return neo4jNodeDao.findAll();
    }
}
