package com.asher.yang.upload;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Created by AsherYang on 2016/12/6.
 * email: ouyangfan1991@gmail.com
 */
@State(
        name = "AsherUpload.Settings",
        storages = {
                @Storage(id = "AsherUploadSettings", file = "$PROJECT_FILE$"),
                @Storage(file = "$PROJECT_CONFIG_DIR$/uploadSettings.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class UploadSettings implements PersistentStateComponent<UploadSettings.State> {

    public static final String DEFAULT_HOST = "172.28.11.49";
    private State myState = new State();

    private UploadSettings() {

    }

    public static UploadSettings getSafeInstance(Project project) {
        UploadSettings settings = ServiceManager.getService(project, UploadSettings.class);
        return settings != null ? settings : new UploadSettings();
    }

    @Nullable
    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(State state) {
        XmlSerializerUtil.copyBean(state, myState);
    }

    public String getHost() {
        return myState.host;
    }

    public void setHost(String host) {
        myState.host = host;
    }

    public String getUsername() {
        return myState.username;
    }

    public void setUsername(String username) {
        myState.username = username;
    }

    public String getPassword() {
        return myState.password;
    }

    public void setPassword(String password) {
        myState.password = password;
    }

    public String getFromPath() {
        return myState.fromPath;
    }

    public void setFromPath(String fromPath) {
        myState.fromPath = fromPath;
    }

    public String getToPath() {
        return myState.toPath;
    }

    public void setToPath(String toPath) {
        myState.toPath = toPath;
    }

    public String getFileName() {
        return myState.fileName;
    }

    public void setFileName(String fileName) {
        myState.fileName = fileName;
    }

    public boolean getIsOnlyShowRelease() {
        return myState.rssSettings.isOnlyShowRelease;
    }

    public void setOnlyShowRelease(boolean onlyShowRelease) {
        myState.rssSettings.isOnlyShowRelease = onlyShowRelease;
    }

    // persistent state
    public static class State {
        public static final String RESET_STR_VALUE = "";
        public String host = DEFAULT_HOST;
        public String username = RESET_STR_VALUE;
        public String password = RESET_STR_VALUE;
        public String fromPath = RESET_STR_VALUE;
        public String toPath = RESET_STR_VALUE;
        public String fileName = RESET_STR_VALUE;
        public RssSettings rssSettings = new RssSettings();
    }

    public static class RssSettings {
        public boolean isOnlyShowRelease = false;
    }
}
