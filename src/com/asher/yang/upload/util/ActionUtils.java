package com.asher.yang.upload.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

/**
 * Created by AsherYang on 2016/9/24.
 * email: ouyangfan1991@gmail.com
 */
public class ActionUtils {

    private ActionUtils() {

    }

    public static Project getProject(AnActionEvent event) {
        DataContext dataContext = event.getDataContext();
        return PlatformDataKeys.PROJECT.getData(dataContext);
    }
}
