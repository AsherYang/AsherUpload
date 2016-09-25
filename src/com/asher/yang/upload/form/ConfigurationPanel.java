package com.asher.yang.upload.form;

import com.intellij.openapi.project.Project;

import javax.swing.*;

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
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
