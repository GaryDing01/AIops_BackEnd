package com.aiops_web.service.neo4j;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aiops_web.entity.neo4j.Node;
import com.alibaba.fastjson.JSONObject;

@Service
public class SystemArchitectureService {

    @Resource
    private SshJschService sshJschService;

    private String systemNamespace = "train-ticket";

    private List<Node> allNodeList = new ArrayList<>();

    public Boolean configSystemArchitecture() {
        sshJschService.CreateSession();
        getSystemNodesBaseInfo();
        // getSystemPodsBaseInfo();
        // getSystemServicesBaseInfo();
        sshJschService.releaseSession();
        return true;
    }

    // 获取系统中node的基本信息
    private void getSystemNodesBaseInfo() {
        String command = "kubectl get nodes -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("NodeBaseInfo");
        System.out.println(result);
        parseTableStringToNodeList(result, "Node");
    }

    // 获取系统中pod的基本信息
    private void getSystemPodsBaseInfo() {
        String command = "kubectl get pods -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("PodBaseInfo");
        System.out.println(result);
        parseTableStringToNodeList(result, "Pod");
    }

    // 获取系统中service的基本信息
    private void getSystemServicesBaseInfo() {
        String command = "kubectl get services -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("ServiceBaseInfo");
        System.out.println(result);
        parseTableStringToNodeList(result, "Service");
    }

    // 使用com.alibaba.fastjson.JSONObject解析用String表示的表格
    private void parseTableStringToNodeList(String tableString, String parseType) {

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
            // TODO: 设置parentId为系统Id
            dataList.add(node);
        }

        for (Node data : dataList) {
            System.out.println("data:");
            System.out.println(data);
        }

        allNodeList.addAll(dataList);
    }
}
