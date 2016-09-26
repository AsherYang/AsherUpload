package com.asher.yang.upload.form;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

        testConnectionButton.addActionListener(mConnectBtnActionListener);
    }

    private ActionListener mConnectBtnActionListener = e -> {
        try {
            Process process = Runtime.getRuntime().exec("python /Users/ouyangfan/Documents/ouyangfan/code_program/python_pro/hello.py");
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

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
