// cql cypher
// MATCH (Node:`Node`) RETURN Node{.content, .id, .name, .parentId, .type, __NodeLabels__: labels(Node), __internalNeo4jId__: id(Node)}
// 清空节点和关系
MATCH (n)
OPTIONAL MATCH (n)-[r]-()
DELETE n,r;

// 创建节点
create 
(:Node {id:1,type:'System',name:'System1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:2,type:'Node',name:'Node1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:3,type:'Node',name:'Node2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:4,type:'Node',name:'Node3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:5,type:'Pod',name:'Pod1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:6,type:'Pod',name:'Pod2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:7,type:'Pod',name:'Pod3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:8,type:'Container',name:'Container1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:9,type:'Container',name:'Container2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:10,type:'Container',name:'Container3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:11,type:'Service',name:'Service1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:12,type:'Service',name:'Service2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:13,type:'Service',name:'Service3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:14,type:'Class',name:'Class1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:15,type:'Class',name:'Class2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:0}),
(:Node {id:16,type:'Class',name:'Class3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:0});

// contain
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Container1'
CREATE (a)-[:Relationship {id:1,type:'contain'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Container2'
CREATE (a)-[:Relationship {id:2,type:'contain'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod2' AND b.name = 'Container3'
CREATE (a)-[:Relationship {id:3,type:'contain'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Class1'
CREATE (a)-[:Relationship {id:4,type:'contain'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Class2'
CREATE (a)-[:Relationship {id:5,type:'contain'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service2' AND b.name = 'Class3'
CREATE (a)-[:Relationship {id:6,type:'contain'}] -> (b);

// runIn
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Node1'
CREATE (a)-[:Relationship {id:7,type:'runIn'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod2' AND b.name = 'Node2'
CREATE (a)-[:Relationship {id:8,type:'runIn'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod3' AND b.name = 'Node3'
CREATE (a)-[:Relationship {id:9,type:'runIn'}] -> (b);

// logicalAbstract
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod1'
CREATE (a)-[:Relationship {id:10,type:'logicalAbstract'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod2'
CREATE (a)-[:Relationship {id:11,type:'logicalAbstract'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod3'
CREATE (a)-[:Relationship {id:12,type:'logicalAbstract'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service2' AND b.name = 'Pod2'
CREATE (a)-[:Relationship {id:13,type:'logicalAbstract'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service3' AND b.name = 'Pod3'
CREATE (a)-[:Relationship {id:14,type:'logicalAbstract'}] -> (b);