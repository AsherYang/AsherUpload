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
        configBean.setUsername(uploadSettings.getUsername());
        configBean.setPassword(uploadSettings.getPassword());
        configBean.setFromPath(uploadSettings.getFromPath());
        configBean.setToPath(uploadSettings.getToPath());
        configBean.setFileName(uploadSettings.getFileName());
        configBean.setOnlyRelease(uploadSettings.getIsOnlyShowRelease());
        return configBean;
    }
}
