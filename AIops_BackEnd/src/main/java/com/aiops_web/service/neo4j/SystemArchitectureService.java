package com.aiops_web.service.neo4j;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SystemArchitectureService {

    @Resource
    private SshJschService sshJschService;

    private String systemNamespace = "train-ticket";

    public Boolean configSystemArchitecture() {
        getSystemNodesBaseInfo();
        getSystemPodsBaseInfo();
        getSystemServicesBaseInfo();
        return true;
    }

    // 获取系统中node的基本信息
    public void getSystemNodesBaseInfo() {
        sshJschService.CreateSession();
        String command = "kubectl get nodes -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("NodeBaseInfo");
        System.out.println(result);
        sshJschService.releaseSession();
    }

    // 获取系统中pod的基本信息
    public void getSystemPodsBaseInfo() {
        sshJschService.CreateSession();
        String command = "kubectl get pods -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("PodBaseInfo");
        System.out.println(result);
        sshJschService.releaseSession();
    }

    // 获取系统中service的基本信息
    public void getSystemServicesBaseInfo() {
        sshJschService.CreateSession();
        String command = "kubectl get services -o wide -n " + systemNamespace;
        String result = sshJschService.commandExec(command);
        System.out.println("ServiceBaseInfo");
        System.out.println(result);
        sshJschService.releaseSession();
    }
}
