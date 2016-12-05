package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.bean.SshBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * support ssh execute method
 */
public class SshExec {

    private SshBean ssh;
    private String osName = System.getProperty("os.name");

    public SshExec(SshBean ssh) {
        this.ssh = ssh;
    }

    public String execute(String cmd) {
        if (null == cmd || "".equals(cmd)) {
            throw new IllegalArgumentException("cmd need not null.");
        }
        StringBuilder sb = new StringBuilder();
        try {
            JSch jSch = new JSch();
            if (osName.toUpperCase().contains("WINDOWS")) {
                jSch.setKnownHosts("c:\\known_hosts");
            } else {
                jSch.setKnownHosts("/root/.ssh/know_hosts");
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            Session session = jSch.getSession(ssh.getUsername(), ssh.getHost(), 22);
            session.setConfig(config);
            session.setPassword(ssh.getPassword());
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            InputStream in = channel.getInputStream();
            channel.connect();
            int nextChar;
            while (true) {
                while ((nextChar = in.read()) != -1) {
                    sb.append((char) nextChar);
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
