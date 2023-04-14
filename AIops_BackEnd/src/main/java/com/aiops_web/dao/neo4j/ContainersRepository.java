package com.aiops_web.dao.neo4j;
import com.aiops_web.entity.neo4j.Container;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainersRepository extends Neo4jRepository<Container, String> {
    
}
