package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.bean.ConfigBean;
import org.jetbrains.annotations.Nullable;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * Upload settings to config bean util class
 */
public class US2ConfigBeanUtil {

    @Nullable
    public static ConfigBean toConfigBean(UploadSettings uploadSettings) {
        if (null == uploadSettings) {
            return null;
        }

        ConfigBean configBean = new ConfigBean();
        configBean.setHost(uploadSettings.getHost());
        configBean.setPort(21);
        configBean.setUsername(uploadSettings.getUsername().equals("") ? "" : uploadSettings.getUsername().trim());
        configBean.setPassword(uploadSettings.getPassword().equals("") ? "" : uploadSettings.getPassword().trim());
        configBean.setFromPath(uploadSettings.getFromPath().equals("") ? "" : uploadSettings.getFromPath().trim());
        configBean.setToPath(uploadSettings.getToPath().equals("") ? "" : uploadSettings.getToPath().trim());
        configBean.setFileName(uploadSettings.getFileName().equals("") ? "" : uploadSettings.getFileName().trim());
        configBean.setOnlyRelease(uploadSettings.getIsOnlyShowRelease());
        return configBean;
    }
}
