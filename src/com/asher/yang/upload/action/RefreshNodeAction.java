package com.asher.yang.upload.action;

import com.asher.yang.upload.callback.IRefreshFileCallBack;
import com.asher.yang.upload.bussiness.RefreshFileService;
import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.swing.*;
import java.util.List;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 */
public class RefreshNodeAction extends AnAction implements DumbAware {

    private static final Icon REFRESH_ICON = GuiUtil.isUnderDarcula() ? GuiUtil.loadIcon("refresh_dark.png") : GuiUtil.loadIcon("refresh.png");
    private final BrowserPanel browserPanel;
    private final Project project;

    public RefreshNodeAction(BrowserPanel browserPanel, Project project) {
        super("Refresh", "Refresh current node", REFRESH_ICON);
        this.browserPanel = browserPanel;
        this.project = project;
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        RefreshFileService fileService = RefreshFileService.getInstance(project);
        fileService.setRefreshCallBack(mRefreshFileCallBack);
        fileService.refresh();
    }

    private IRefreshFileCallBack mRefreshFileCallBack = new IRefreshFileCallBack() {
        @Override
        public void onSuccess(List<FTPFile> ftpFiles) {
            browserPanel.refreshFileTree(ftpFiles);
        }

        @Override
        public void onFail(Exception ex) {
            browserPanel.notifyErrorToolWindow(ex.getMessage());
        }
    };
}
