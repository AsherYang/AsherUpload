package com.asher.yang.upload.action;

import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 */
public class UploadFileAction extends AnAction implements DumbAware {

    private static final Icon EXECUTE_ICON = GuiUtil.isUnderDarcula() ? GuiUtil.loadIcon("asher_logo.png") : GuiUtil.loadIcon("asher_logo.png");
    private final BrowserPanel browserPanel;

    public UploadFileAction(BrowserPanel browserPanel) {
        super("UploadFile", "upload file to ftp server." , EXECUTE_ICON);
        this.browserPanel = browserPanel;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        FTPFile selectFile = browserPanel.getSelectedFile();
        notifyOnGoingMessage(selectFile);
    }

    private void notifyOnGoingMessage(FTPFile ftpFile) {
        System.out.println("File = " + ftpFile.getName() + " , link = " + ftpFile.getLink());
//        browserPanel.notifyInfoToolWindow(HtmlUtil.createHtmlLinkMessage
//                (ftpFile.getName() + " upload is on going.", ftpFile.getLink()));
        browserPanel.notifySuccessMessage(ftpFile.getName());
    }

}
