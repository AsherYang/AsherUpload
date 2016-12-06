package com.asher.yang.upload.action;

import com.asher.yang.upload.UploadProjectComponent;
import com.asher.yang.upload.util.ActionUtils;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class OpenPluginSettingsAction extends AnAction implements DumbAware {

    private static final Icon SETTINGS_ICON = GuiUtil.isUnderDarcula() ? GuiUtil.loadIcon("settings_dark.png") : GuiUtil.loadIcon("settings.png");

    public OpenPluginSettingsAction() {
        super("Asher Settings", "Edit the Asher settings for the current project", SETTINGS_ICON);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        showSettingsFor(ActionUtils.getProject(anActionEvent));
    }

    private static void showSettingsFor(Project project) {
        ShowSettingsUtil.getInstance().showSettingsDialog(project, UploadProjectComponent.class);
    }
}
