package com.asher.yang.upload.form;

import com.intellij.openapi.project.Project;
import com.intellij.util.ResourceUtil;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
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
    private JTextField dirPathField;

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
        String host = getInputHost();
        String userName = getInputUserName();
        String password = getInputPassword();
        URL url  = ResourceUtil.getResource(ConfigurationPanel.class.getClassLoader(), "shell", "copyfile.sh");
        try {
            String cmd1 = "chmod +x " + url.getPath();
            String cmd2 = "/bin/bash " + url.getPath() + " " + userName;
            Process process;
            process = Runtime.getRuntime().exec(cmd1);
            process.waitFor();
            process = Runtime.getRuntime().exec(cmd2);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println("line = " + line);
            }
            br.close();
            isr.close();
            is.close();
            process.waitFor();
            System.out.println("host = " + getInputHost() + " , userName = " + getInputUserName() + " , password = " + getInputPassword());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    };

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getInputUserName() {
        return usernameField.getText();
    }

    public String getInputHost() {
        return hostField.getText();
    }

    public String getInputPassword() {
        return String.valueOf(passwordField.getPassword());
    }
}
