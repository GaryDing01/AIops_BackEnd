// cql cypher

// 清空节点和关系
MATCH (n)
OPTIONAL MATCH (n)-[r]-()
DELETE n,r;

// 创建节点
create 
(:node {type:'System',name:'System1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Node',name:'Node1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Node',name:'Node2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Node',name:'Node3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Pod',name:'Pod1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Pod',name:'Pod2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Pod',name:'Pod3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Container',name:'Container1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Container',name:'Container2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Container',name:'Container3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Service',name:'Service1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Service',name:'Service2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Service',name:'Service3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Class',name:'Class1',content:"{param1:1,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Class',name:'Class2',content:"{param1:2,param2:'s2',param3:'s3'}",parentId:''}),
(:node {type:'Class',name:'Class3',content:"{param1:3,param2:'s2',param3:'s3'}",parentId:''});

// contain
MATCH (a:node),(b:node)
WHERE a.name = 'Pod1' AND b.name = 'Container1'
CREATE (a)-[:relationship {type:'contain'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Pod1' AND b.name = 'Container2'
CREATE (a)-[:relationship {type:'contain'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Pod2' AND b.name = 'Container3'
CREATE (a)-[:relationship {type:'contain'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service1' AND b.name = 'Class1'
CREATE (a)-[:relationship {type:'contain'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service1' AND b.name = 'Class2'
CREATE (a)-[:relationship {type:'contain'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service2' AND b.name = 'Class3'
CREATE (a)-[:relationship {type:'contain'}] -> (b);

// runIn
MATCH (a:node),(b:node)
WHERE a.name = 'Pod1' AND b.name = 'Node1'
CREATE (a)-[:relationship {type:'runIn'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Pod2' AND b.name = 'Node2'
CREATE (a)-[:relationship {type:'runIn'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Pod3' AND b.name = 'Node3'
CREATE (a)-[:relationship {type:'runIn'}] -> (b);

// logicalAbstract
MATCH (a:node),(b:node)
WHERE a.name = 'Service1' AND b.name = 'Pod1'
CREATE (a)-[:relationship {type:'logicalAbstract'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service1' AND b.name = 'Pod2'
CREATE (a)-[:relationship {type:'logicalAbstract'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service1' AND b.name = 'Pod3'
CREATE (a)-[:relationship {type:'logicalAbstract'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service2' AND b.name = 'Pod2'
CREATE (a)-[:relationship {type:'logicalAbstract'}] -> (b);
MATCH (a:node),(b:node)
WHERE a.name = 'Service3' AND b.name = 'Pod3'
CREATE (a)-[:relationship {type:'logicalAbstract'}] -> (b);