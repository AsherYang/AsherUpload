package com.asher.yang.upload.form;

import com.asher.yang.upload.bean.FtpBean;
import com.asher.yang.upload.bussiness.FtpExec;
import com.intellij.openapi.project.Project;
import com.intellij.util.ResourceUtil;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class ConfigurationPanel {
    private JTextField usernameField;
    private JTextField hostField;
    private JPasswordField passwordField;

    private JPanel rootPanel;
    private JButton testConnectionButton;
    private JLabel connectionStatusLabel;
    private JTextField downPathField;
    private JTextField uploadFileName;

    private Project project;

    public ConfigurationPanel(Project project) {
        this.project = project;

        testConnectionButton.addActionListener(mCopyFileActionListener);
    }

    private ActionListener mConnectBtnActionListener = e -> {
        try {
            URL url = ResourceUtil.getResource(ConfigurationPanel.class.getClassLoader(), "python", "connect.py");
            Process process = Runtime.getRuntime().exec("python " + url.getPath());
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("line = " + line);
            }

            br.close();
            isr.close();
            process.waitFor();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    };

    // use linux shell . because java call python (param) not suit here.
    private ActionListener mCopyFileActionListener = event -> {
//        SshBean ssh = new SshBean();
//        ssh.setHost(getInputHost());
//        ssh.setUsername(getInputUserName());
//        ssh.setPassword(getInputPassword());
//        String dirPath = getInputDownDir();
//        String cmd = "ls " + dirPath;
//        SshExec sshExec = new SshExec(ssh);
//        String result = sshExec.execute(cmd);
//        System.out.println("result = " + result);
        FtpBean ftpBean = new FtpBean();
        ftpBean.setHost(getInputHost());
        ftpBean.setPort(21);
        ftpBean.setUsername(getInputUserName());
        ftpBean.setPassword(getInputPassword());
        FtpExec ftpExec = new FtpExec(ftpBean);
        ftpExec.execute();
    };

    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * 输入的用户名
     */
    public String getInputUserName() {
        return usernameField.getText();
    }

    /**
     * 输入的远程 host
     */
    public String getInputHost() {
        return hostField.getText();
    }

    /**
     * 输入的密码
     */
    public String getInputPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    /**
     * 输入的下载地址
     */
    public String getInputDownDir() {
        return String.valueOf(downPathField.getText());
    }

    /**
     * 输入的上传地址
     */
    public String getInputUploadFileName() {
        return uploadFileName.getText();
    }
}
