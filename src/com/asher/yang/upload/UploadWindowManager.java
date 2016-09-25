package com.asher.yang.upload;

import com.asher.yang.upload.form.BrowserPanel;
import com.asher.yang.upload.util.GuiUtil;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAwareRunnable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;

import javax.swing.*;

/**
 * Created by ouyangfan on 16/9/25.
 * email: ouyangfan1991@gmail.com
 */
public class UploadWindowManager {

    public static final String ASHER_BROWSER = "AsherUpload";
    private static final Icon ASHER_ICON = GuiUtil.loadIcon("asher_logo.png");
    private final Project project;

    public static UploadWindowManager getInstance(Project project) {
        return ServiceManager.getService(project, UploadWindowManager.class);
    }

    public UploadWindowManager(Project project) {
        this.project = project;

        final BrowserPanel browserPanel = BrowserPanel.getInstance(project);
        Content content = ContentFactory.SERVICE.getInstance().createContent(browserPanel, null, false);
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow(ASHER_BROWSER, false, ToolWindowAnchor.RIGHT);
        toolWindow.setIcon(ASHER_ICON);
        ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContent(content);

        StartupManager.getInstance(project).registerPostStartupActivity(new DumbAwareRunnable() {
            @Override
            public void run() {
                browserPanel.init();
            }
        });
    }

    public void unregisterMyself() {

    }
}

