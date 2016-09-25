package com.asher.yang.upload;

import com.asher.yang.upload.form.ConfigurationPanel;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by ouyangfan on 16/9/25.
 * email: ouyangfan1991@gmail.com
 */
public class UploadProjectComponent implements ProjectComponent , SearchableConfigurable{

    private static final String ASHER_CONTROL_PLUGIN_NAME = "Asher Plugin";
    private static final String PROJECT_COMPONENT_NAME = "UploadProjectComponent";

    private Project project;
    private ConfigurationPanel configurationPanel;

    public UploadProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return PROJECT_COMPONENT_NAME;
    }

    @Override
    public void projectOpened() {
        UploadWindowManager.getInstance(project);
    }

    @Override
    public void projectClosed() {
        UploadWindowManager.getInstance(project).unregisterMyself();
    }

    @NotNull
    @Override
    public String getId() {
        return null;
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return ASHER_CONTROL_PLUGIN_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == configurationPanel) {
            configurationPanel = new ConfigurationPanel(project);
        }
        return configurationPanel.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void reset() {

    }

    @Override
    public void disposeUIResources() {

    }
}
