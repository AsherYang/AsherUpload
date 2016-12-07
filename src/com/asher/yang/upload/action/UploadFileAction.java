package com.asher.yang.upload.action;

import com.asher.yang.upload.bussiness.UploadFileService;
import com.asher.yang.upload.callback.IUploadCallBack;
import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 */
public class UploadFileAction extends AnAction implements DumbAware {

    private static final Icon EXECUTE_ICON = GuiUtil.isUnderDarcula() ? GuiUtil.loadIcon("asher_logo.png") : GuiUtil.loadIcon("asher_logo.png");
    private final BrowserPanel browserPanel;
    private final Project project;

    public UploadFileAction(BrowserPanel browserPanel, Project project) {
        super("UploadFileService", "upload file to ftp server.", EXECUTE_ICON);
        this.browserPanel = browserPanel;
        this.project = project;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        FTPFile selectFile = browserPanel.getSelectedFile();
        System.out.println("select File = " + selectFile);
        UploadFileService uploadFileService = UploadFileService.getInstance(project);
        uploadFileService.setUploadCallBack(mUploadCallBack);
        uploadFileService.upload(selectFile);
    }

    private IUploadCallBack mUploadCallBack = new IUploadCallBack() {
        @Override
        public void onSuccess(String tips) {
            browserPanel.notifySuccessMessage(tips);
        }

        @Override
        public void onFail(String errorMsg) {
            browserPanel.notifyFailMessage(errorMsg);
        }
    };

}
