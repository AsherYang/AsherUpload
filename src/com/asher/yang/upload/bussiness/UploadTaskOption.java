package com.asher.yang.upload.bussiness;

import com.intellij.openapi.progress.PerformInBackgroundOption;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * background task
 */
public class UploadTaskOption implements PerformInBackgroundOption {

    public static UploadTaskOption INSTANCE = new UploadTaskOption();

    @Override
    public boolean shouldStartInBackground() {
        return true;
    }

    @Override
    public void processSentToBackground() {

    }
}
