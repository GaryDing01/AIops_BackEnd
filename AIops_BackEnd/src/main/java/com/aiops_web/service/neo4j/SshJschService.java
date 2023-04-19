package com.aiops_web.service.neo4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aiops_web.config.JschConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Service
public class SshJschService {

    @Resource
    private JschConfig jschConfig;

    private Session session;

    public void CreateSession() {
        String username = jschConfig.getUsername();
        String ip = jschConfig.getIp();
        Integer port = jschConfig.getPort();
        String password = jschConfig.getPassword();
        String StrictHostKeyChecking = jschConfig.getStrictHostKeyChecking();

        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, ip, port);
            session.setConfig("StrictHostKeyChecking", StrictHostKeyChecking);
            session.setPassword(password);
            session.connect();
            // session.setConfig("charset", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseSession() {
        if (session != null) {
            session.disconnect();
            System.out.println("session disconnect");
        }
    }

    public String commandExec(String command) {
        if (command == null || command.isEmpty()) {
            return null;
        }
        Channel channel = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 创建⼀个执⾏命令的通道 Channel
            channel = session.openChannel("exec");

            // 设置环境，解决中文乱码问题
            ((ChannelExec) channel).setEnv("LANG", "en_US.UTF-8");
            // 设置执⾏命令
            ((ChannelExec) channel).setCommand(command);
            // 命令执⾏的标准输⼊流，⼀般来说不需要输⼊，置为 null
            channel.setInputStream(null);
            // 将命令的错误信息定向到 Springboot 项⽬的标准错误流 System.err 中
            ((ChannelExec) channel).setErrStream(System.err);
            // 建⽴连接
            channel.connect();

            // 获取命令执⾏的标准输出流
            InputStream in = channel.getInputStream();
            // 通过标准输出流接受命令的执⾏信息，使用字节流读取，⽽不是字符流
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int length = in.read(tmp, 0, 1024);
                    if (length < 0) {
                        break;
                    }
                    // 同时配置字符集，解决中文乱码问题
                    stringBuilder.append(new String(tmp, 0, length, "UTF-8"));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
                // Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (channel != null) {
                channel.disconnect();
            }
        }
        return stringBuilder.toString();
    }

}
