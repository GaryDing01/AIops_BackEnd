package com.aiops_web.service.neo4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aiops_web.dto.Neo4jRelationshipDto;
import com.aiops_web.entity.neo4j.Node;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class SystemArchitectureService {

    @Resource
    private SshJschService sshJschService;

    @Resource
    private KnowledgeGraphService knowledgeGraphService;

    private String systemNamespace = "train-ticket";

    private List<Node> allNodeList = new ArrayList<>();

    public Boolean configSystemArchitecture() {
        knowledgeGraphService.deleteAllData(); // 清空neo4j数据库
        Node systemNode = new Node();
        systemNode.setType("System");
        systemNode.setName(systemNamespace);
        systemNode.setContent("{}");
        systemNode.setParentId(Long.valueOf(-1));
        Long systemId = knowledgeGraphService.addNode(systemNode); // 添加System节点

        sshJschService.CreateSession();
        // 获取Node的基本信息，形成Node，并将其加入数据库
        List<Node> nodeList = getSystemNodesBaseInfo(systemId);
        // 获取Pod的基本信息，形成Node，并将其加入数据库
        List<Node> podList = getSystemPodsBaseInfo(systemId);
        // 获取Container的基本信息，形成Node，并将其加入数据库
        List<Node> containerList = getSystemContainersBaseInfo(podList);
        // 获取Service的基本信息，形成Node，并将其加入数据库
        List<Node> serviceList = getSystemServicesBaseInfo(systemId);
        saveAllRelationship(nodeList, podList, containerList, serviceList); // 保存所有关系
        sshJschService.releaseSession();
        return true;
    }

    // 获取系统中node的基本信息
    private List<Node> getSystemNodesBaseInfo(Long systemId) {
        String command = "kubectl get nodes -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        List<Node> nodeList = parseTableStringToNodeList(result, "Node", systemId);

        List<Node> newNodeList = saveNodeList(nodeList);
        allNodeList.addAll(newNodeList);
        return newNodeList;
    }

    // 获取系统中pod的基本信息
    private List<Node> getSystemPodsBaseInfo(Long systemId) {
        String command = "kubectl get pods -o wide -n " + systemNamespace;
        // kubectl get node k8s-pm-4 -o json -n train-ticket 获取一些更详细信息
        String result = sshJschService.commandExec(command);
        List<Node> podList = parseTableStringToNodeList(result, "Pod", systemId);

        List<Node> newNodeList = saveNodeList(podList);
        allNodeList.addAll(newNodeList);
        return newNodeList;
    }

    // 获取系统中service的基本信息
    private List<Node> getSystemServicesBaseInfo(Long systemId) {
        String command = "kubectl get services -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        List<Node> serviceList = parseTableStringToNodeList(result, "Service", systemId);

        List<Node> newNodeList = saveNodeList(serviceList);
        allNodeList.addAll(newNodeList);
        return newNodeList;
    }

    // 获取系统中Container的基本信息
    private List<Node> getSystemContainersBaseInfo(List<Node> podList) {
        List<Node> containerList = new ArrayList<>();
        for (Node pod : podList) {
            String podName = pod.getName();
            Long podId = pod.getId();
            JSONObject podInfo = JSONObject.parseObject(pod.getContent());
            String podStatus = podInfo.getString("STATUS");
            if (podStatus.equals("Evicted"))
                continue;// Pod状态为Evicted时，无法获取Container信息

            String command = "kubectl get pods " + podName + " -o json -n " + systemNamespace;
            String podInf = sshJschService.commandExec(command);
            List<Node> podContainerList = parseContainersInfo(podInf, podId);
            containerList.addAll(podContainerList);
        }

        List<Node> newNodeList = saveNodeList(containerList);
        allNodeList.addAll(newNodeList);
        return newNodeList;
    }

    // 使用com.alibaba.fastjson.JSONObject解析用String表示的信息(该信息是表格格式，用于解析Node、Pod、Service)
    private List<Node> parseTableStringToNodeList(String tableString, String parseType, Long parentId) {

        List<Node> dataList = new ArrayList<>();
        String[] lines = tableString.split("\\r?\\n");
        // 按2个及以上的空白字符分割每一行
        String[] headers = lines[0].split("\\s\\s+");

        for (int i = 1; i < lines.length; i++) {

            // 每一行数据中的字符串列表
            String[] fields = lines[i].split("\\s\\s+");
            JSONObject dataObj = new JSONObject();
            for (int j = 0; j < headers.length; j++) {
                dataObj.put(headers[j], fields[j]);
            }
            Node node = new Node();
            node.setName(dataObj.getString("NAME"));
            node.setType(parseType);
            node.setContent(dataObj.toJSONString());
            node.setParentId(parentId);
            dataList.add(node);
        }

        return dataList;
    }

    // 使用com.alibaba.fastjson.JSONObject解析用String表示的Pod信息(该信息是Json格式，用于解析Container)
    private List<Node> parseContainersInfo(String podInf, Long podId) {
        List<Node> containerList = new ArrayList<>();
        JSONObject podInfo = JSONObject.parseObject(podInf);
        JSONObject podStatus = podInfo.getJSONObject("status");
        JSONArray podContainerStatuses = podStatus.getJSONArray("containerStatuses");
        JSONArray podInitContainerStatuses = podStatus.getJSONArray("initContainerStatuses");

        if (podContainerStatuses != null) {
            for (JSONObject podContainerStatus : podContainerStatuses.toJavaList(JSONObject.class)) {
                Node container = new Node();
                String containerName = podContainerStatus.getString("name");
                container.setName(containerName);
                container.setType("Container");
                container.setContent(podContainerStatus.toJSONString());
                container.setParentId(podId);
                containerList.add(container);
            }
        }
        if (podInitContainerStatuses != null) {
            for (JSONObject podInitContainerStatus : podInitContainerStatuses.toJavaList(JSONObject.class)) {
                Node container = new Node();
                String containerName = podInitContainerStatus.getString("name");
                container.setName(containerName);
                container.setType("Container");
                container.setContent(podInitContainerStatus.toJSONString());
                container.setParentId(podId);
                containerList.add(container);
            }
        }

        return containerList;
    }

    // 使用com.alibaba.fastjson.JSONObject解析String表示的Service和Pod之间的关系
    private List<String> parseServiceAndPodRelationship(Node node) {
        Set<String> serviceRelevantIps = new HashSet<>();
        String serviceName = node.getName();
        String command = "kubectl describe service " + serviceName + " -n " + systemNamespace;
        // String command = "kubectl describe service nacos -n train-ticket";
        String result = sshJschService.commandExec(command);
        String[] lines = result.split("\\r?\\n");
        for (String line : lines) {
            String[] fields = line.split("\\s\\s+");
            if (fields[0].contains("Endpoints")) {
                if (fields.length == 1)
                    continue;
                String[] ipList = fields[1].split(",");

                for (String ip : ipList) {
                    String[] ipAndPort = ip.split(":");
                    serviceRelevantIps.add(ipAndPort[0]);
                }
            }
        }

        return new ArrayList<>(serviceRelevantIps);
    }

    // 保存所有节点
    private List<Node> saveNodeList(List<Node> needSave) {
        List<Node> newAllNodeList = new ArrayList<>();
        for (Node node : needSave) {
            Long nodeId = knowledgeGraphService.addNode(node);
            node.setId(nodeId);
            newAllNodeList.add(node);
        }
        return newAllNodeList;
    }

    // 保存所有节点之间的关系
    private Boolean saveAllRelationship(List<Node> nodeList, List<Node> podList, List<Node> containerList,
            List<Node> serviceList) {

        // 保存Node与Node之间的contains关系
        for (Node node : allNodeList) {
            Neo4jRelationshipDto neo4jRelationshipDto = new Neo4jRelationshipDto();
            neo4jRelationshipDto.setStartId(node.getParentId());
            neo4jRelationshipDto.setEndId(node.getId());
            neo4jRelationshipDto.setType("contains");
            JSONObject content = new JSONObject();
            content.put("name", "contains");
            neo4jRelationshipDto.setContent(content.toJSONString());
            knowledgeGraphService.addRelationship(neo4jRelationshipDto);
        }

        // 保存Node与Node之间的runs_in关系(pod与node之间的关系)
        for (Node pod : podList) {
            JSONObject podInfo = JSONObject.parseObject(pod.getContent());
            String nodeName = podInfo.getString("NODE");
            for (Node node : nodeList) {
                if (node.getName().equals(nodeName)) {
                    Neo4jRelationshipDto neo4jRelationshipDto = new Neo4jRelationshipDto();
                    neo4jRelationshipDto.setStartId(pod.getId());
                    neo4jRelationshipDto.setEndId(node.getId());
                    neo4jRelationshipDto.setType("runs_in");
                    JSONObject content = new JSONObject();
                    content.put("name", "runs_in");
                    neo4jRelationshipDto.setContent(content.toJSONString());
                    knowledgeGraphService.addRelationship(neo4jRelationshipDto);
                }
            }
        }

        // 保存Node与Node之间的logical_abstracts关系(service与pod之间的关系)
        for (Node service : serviceList) {
            List<String> ips = parseServiceAndPodRelationship(service);
            for (Node pod : podList) {
                JSONObject podInfo = JSONObject.parseObject(pod.getContent());
                String podIp = podInfo.getString("IP");
                if (ips.contains(podIp)) {
                    Neo4jRelationshipDto neo4jRelationshipDto = new Neo4jRelationshipDto();
                    neo4jRelationshipDto.setStartId(service.getId());
                    neo4jRelationshipDto.setEndId(pod.getId());
                    neo4jRelationshipDto.setType("logical_abstracts");
                    JSONObject content = new JSONObject();
                    content.put("name", "logical_abstracts");
                    neo4jRelationshipDto.setContent(content.toJSONString());
                    knowledgeGraphService.addRelationship(neo4jRelationshipDto);
                }
            }
        }
        return true;
    }
}