// cql cypher
// MATCH (Node:`Node`) RETURN Node{.content, .id, .name, .parentId, .type, __NodeLabels__: labels(Node), __internalNeo4jId__: id(Node)}
// 清空节点和关系
MATCH (n)
OPTIONAL MATCH (n)-[r]-()
DELETE n,r;

// 创建节点
create 
(:Node {type:'System',name:'System1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:-1}),
(:Node {type:'Node',name:'Node1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Node',name:'Node2',content:'{"READY":"2/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Node',name:'Node3',content:'{"READY":"3/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Pod',name:'Pod1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Pod',name:'Pod2',content:'{"READY":"2/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Pod',name:'Pod3',content:'{"READY":"3/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Container',name:'Container1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Container',name:'Container2',content:'{"READY":"2/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Container',name:'Container3',content:'{"READY":"3/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Service',name:'Service1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Service',name:'Service2',content:'{"READY":"2/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Service',name:'Service3',content:'{"READY":"3/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Class',name:'Class1',content:'{"READY":"1/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Class',name:'Class2',content:'{"READY":"2/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0}),
(:Node {type:'Class',name:'Class3',content:'{"READY":"3/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}',parentId:0});

// contain
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Container1'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"11/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Container2'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"12/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod2' AND b.name = 'Container3'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"23/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Class1'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"11/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Class2'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"12/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service2' AND b.name = 'Class3'
CREATE (a)-[:Relationship {type:'contain',content:'{"READY":"23/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);

// runIn
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod1' AND b.name = 'Node1'
CREATE (a)-[:Relationship {type:'runIn',content:'{"READY":"11/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod2' AND b.name = 'Node2'
CREATE (a)-[:Relationship {type:'runIn',content:'{"READY":"22/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Pod3' AND b.name = 'Node3'
CREATE (a)-[:Relationship {type:'runIn',content:'{"READY":"33/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);

// logicalAbstract
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod1'
CREATE (a)-[:Relationship {type:'logicalAbstract',content:'{"READY":"11/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod2'
CREATE (a)-[:Relationship {type:'logicalAbstract',content:'{"READY":"12/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service1' AND b.name = 'Pod3'
CREATE (a)-[:Relationship {type:'logicalAbstract',content:'{"READY":"13/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service2' AND b.name = 'Pod2'
CREATE (a)-[:Relationship {type:'logicalAbstract',content:'{"READY":"22/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
MATCH (a:Node),(b:Node)
WHERE a.name = 'Service3' AND b.name = 'Pod3'
CREATE (a)-[:Relationship {type:'logicalAbstract',content:'{"READY":"33/1","STATUS":"Evicted","READINESS GATES":"<none>","NOMINATED NODE":"<none>","NODE":"tongji409-np5570m5","IP":"<none>","RESTARTS":"0","NAME":"elasticsearch-79654d5847-vjlxg","AGE":"71d"}'}] -> (b);
