package com.asher.yang.upload.form;

import com.asher.yang.upload.action.OpenPluginSettingsAction;
import com.asher.yang.upload.action.SelectViewAction;
import com.asher.yang.upload.bean.View;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class BrowserPanel extends SimpleToolWindowPanel {

    private JPanel rootPanel;

    private View currentSelectedView;

    private BrowserPanel(Project project) {
        super(true);
        setContent(rootPanel);
    }

    public static BrowserPanel getInstance(Project project) {
        return ServiceManager.getService(project, BrowserPanel.class);
    }

    public void init() {
        initGui();
    }

    private void initGui() {
        installActionsInToolbar();
    }

    private void installActionsInToolbar() {
        DefaultActionGroup actionGroup = new DefaultActionGroup("AsherToolbarGroup", false);
        actionGroup.add(new SelectViewAction(this));
        actionGroup.addSeparator();
        actionGroup.add(new OpenPluginSettingsAction());
        GuiUtil.installActionGroupInToolBar(actionGroup, this, ActionManager.getInstance(), "AsherBrowserActions");
    }

    public View getCurrentSelectedView() {
        currentSelectedView = new View("你好", "http://www.baidu.com");
        return currentSelectedView;
    }

}
