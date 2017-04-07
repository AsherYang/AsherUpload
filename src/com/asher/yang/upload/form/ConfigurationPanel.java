package com.asher.yang.upload.form;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.callback.ILoginCallBack;
import com.asher.yang.upload.bussiness.LoginService;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class ConfigurationPanel {
    private static final Color CONNECTION_TEST_SUCCESSFUL_COLOR = JBColor.GREEN;
    private static final Color CONNECTION_TEST_FAILED_COLOR = JBColor.RED;
    private JTextField usernameField;
    private JTextField hostField;
    private JPasswordField passwordField;

    private JPanel rootPanel;
    private JButton testConnectionButton;
    private JLabel connectionStatusLabel;
    private JTextField fromPathField;
    private JTextField toPathField;
    private JTextField uploadFileNameField;
    private JCheckBox onlyReleaseCheckBox;
    private JPanel checkBoxPanel;
    private JTextPane attentionTextPane;

    private Project project;

    public ConfigurationPanel(Project project) {
        this.project = project;

        testConnectionButton.addActionListener(mConnectBtnActionListener);
        checkBoxPanel.setBorder(IdeBorderFactory.createTitledBorder("Show Settings", true));
        attentionTextPane.setBorder(IdeBorderFactory.createTitledBorder("Attention:", true));
        initAttention();
    }

    private ActionListener mConnectBtnActionListener = e -> {
        login();
    };

    private void login() {
        LoginService loginService = LoginService.getInstance(project);
        loginService.setLoginListener(mLoginCallBack);
        loginService.login();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Attention
     */
    private void initAttention() {
        attentionTextPane.setText(" 1. toPath 选择Debug或Release目录的上层目录，否则找不到路径上传！ \n" +
                " 2. 您需要先使用Jenkins先编译版本，生成新版本后，确认版本再进行上传。");
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
    public String getInputFromPath() {
        return String.valueOf(fromPathField.getText());
    }

    /**
     * 输入的上传地址
     */
    public String getInputToPath() {
        return toPathField.getText();
    }

    /**
     * 输入的上传地址
     */
    public String getInputUploadFileName() {
        return uploadFileNameField.getText();
    }

    /**
     * 是否选中releaseCheckBox
     */
    public boolean getInputReleaseCbState() {
        return onlyReleaseCheckBox.isSelected();
    }

    /**
     * is modified
     *
     * @param uploadSettings uploadSettings
     * @return is modified
     */
    public boolean isModified(UploadSettings uploadSettings) {
        boolean credentialModified = !(uploadSettings.getUsername().equals(getInputUserName()))
                || !uploadSettings.getPassword().equals(getInputPassword());

        boolean selectModified = uploadSettings.getIsOnlyShowRelease() != getInputReleaseCbState();

        boolean uploadArgsModified = !uploadSettings.getHost().equals(getInputHost())
                || !uploadSettings.getFromPath().equals(getInputFromPath())
                || !uploadSettings.getToPath().equals(getInputToPath())
                || !uploadSettings.getFileName().equals(getInputUploadFileName());

        return credentialModified || selectModified || uploadArgsModified;
    }

    /**
     * apply configuration data
     *
     * @param uploadSettings settings
     */
    public void applyConfigurationData(UploadSettings uploadSettings) {
        if (null == uploadSettings) {
            return;
        }

        uploadSettings.setHost(getInputHost());
        uploadSettings.setUsername(getInputUserName());
        uploadSettings.setPassword(getInputPassword());
        uploadSettings.setFromPath(getInputFromPath());
        uploadSettings.setToPath(getInputToPath());
        uploadSettings.setFileName(getInputUploadFileName());
        uploadSettings.setOnlyShowRelease(getInputReleaseCbState());
    }

    /**
     * load configuration data
     *
     * @param uploadSettings settings
     */
    public void loadConfigurationData(UploadSettings uploadSettings) {
        if (null == uploadSettings) {
            return;
        }
        hostField.setText(uploadSettings.getHost());
        usernameField.setText(uploadSettings.getUsername());
        passwordField.setText(uploadSettings.getPassword());
        fromPathField.setText(uploadSettings.getFromPath());
        toPathField.setText(uploadSettings.getToPath());
        uploadFileNameField.setText(uploadSettings.getFileName());
        onlyReleaseCheckBox.setSelected(uploadSettings.getIsOnlyShowRelease());
    }

    /**
     * login call back listener
     */
    private ILoginCallBack mLoginCallBack = new ILoginCallBack() {
        @Override
        public void onSuccess() {
            setConnectionFeedbackLabel(CONNECTION_TEST_SUCCESSFUL_COLOR, "Successful");
        }

        @Override
        public void onFail(Exception ex) {
            setConnectionFeedbackLabel(CONNECTION_TEST_FAILED_COLOR, "[Fail] " + ex.getMessage());
        }
    };

    private void setConnectionFeedbackLabel(final Color labelColor, final String labelText) {
        GuiUtil.runInSwingThread(new Runnable() {
            public void run() {
                connectionStatusLabel.setForeground(labelColor);
                connectionStatusLabel.setText(labelText);
            }
        });
    }
}
