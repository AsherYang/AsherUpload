package com.asher.yang.upload.form;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.bean.FtpBean;
import com.asher.yang.upload.bussiness.GetFtpFiles;
import com.intellij.openapi.project.Project;
import com.intellij.ui.IdeBorderFactory;

import javax.swing.*;
import java.awt.event.ActionListener;

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
    private JTextField fromPathField;
    private JTextField toPathField;
    private JTextField uploadFileNameField;
    private JCheckBox onlyReleaseCheckBox;
    private JPanel checkBoxPanel;

    private Project project;

    public ConfigurationPanel(Project project) {
        this.project = project;

        testConnectionButton.addActionListener(mConnectBtnActionListener);
        checkBoxPanel.setBorder(IdeBorderFactory.createTitledBorder("Show Settings", true));
    }

    private ActionListener mConnectBtnActionListener = e -> {
        FtpBean ftpBean = new FtpBean();
        ftpBean.setHost(getInputHost());
        ftpBean.setPort(21);
        ftpBean.setUsername(getInputUserName());
        ftpBean.setPassword(getInputPassword());
        GetFtpFiles ftpFiles = new GetFtpFiles(ftpBean);
        ftpFiles.login();
        UploadSettings uploadSettings = UploadSettings.getSafeInstance(project);
        applyConfigurationData(uploadSettings);
    };

    // use linux shell . because java call python (param) not suit here.
    private ActionListener mCopyFileActionListener = event -> {
//        SshBean ssh = new SshBean();
//        ssh.setHost(getInputHost());
//        ssh.setUsername(getInputUserName());
//        ssh.setPassword(getInputPassword());
//        String dirPath = getInputFromPath();
//        String cmd = "ls " + dirPath;
//        SshExec sshExec = new SshExec(ssh);
//        String result = sshExec.execute(cmd);
//        System.out.println("result = " + result);
        FtpBean ftpBean = new FtpBean();
        ftpBean.setHost(getInputHost());
        ftpBean.setPort(21);
        ftpBean.setUsername(getInputUserName());
        ftpBean.setPassword(getInputPassword());
        GetFtpFiles ftpExec = new GetFtpFiles(ftpBean);
        System.out.println("=== " + ftpExec.getFiles());
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
}
