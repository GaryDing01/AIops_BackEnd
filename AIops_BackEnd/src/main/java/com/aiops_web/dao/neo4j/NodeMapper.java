package com.aiops_web.dao.neo4j;

import com.aiops_web.entity.neo4j.Node;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS("neo4j")
@Repository
public interface NodeMapper {
    @Select("MATCH (n:Node) WHERE n.type = #{type} RETURN n")
    List<Node> findByType(String type);

    @Select("MATCH (n:Node) RETURN id(n) as id, n.type as type, " +
            "n.name as name, n.content as content, n.parentId as parentId")
    List<Node> findAll();
}
