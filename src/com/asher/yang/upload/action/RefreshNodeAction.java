package com.asher.yang.upload.action;

import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 */
public class RefreshNodeAction extends AnAction implements DumbAware {

    private static final Icon REFRESH_ICON = GuiUtil.isUnderDarcula() ? GuiUtil.loadIcon("refresh_dark.png") : GuiUtil.loadIcon("refresh.png");
    private final BrowserPanel browserPanel;

    public RefreshNodeAction(BrowserPanel browserPanel) {
        super("Refresh", "Refresh current node", REFRESH_ICON);
        this.browserPanel = browserPanel;
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        browserPanel.refreshCurrentView();
    }
}
