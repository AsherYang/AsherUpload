package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.bean.ConfigBean;
import com.asher.yang.upload.callback.IUploadCallBack;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import it.sauronsoftware.ftp4j.FTPFile;
import org.jetbrains.annotations.NotNull;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * upload file to server use of ftp
 */
public class UploadFileService {

    private Project project;
    private UploadSettings uploadSettings;
    private IUploadCallBack mUploadCallBack;

    public UploadFileService(Project project) {
        this.project = project;
        uploadSettings = UploadSettings.getSafeInstance(project);
    }

    public void setUploadCallBack(IUploadCallBack uploadCallBack) {
        this.mUploadCallBack = uploadCallBack;
    }

    public void upload(FTPFile uploadFile) {
        new Task.Backgroundable(project, "Uploading", false, UploadTaskOption.INSTANCE) {

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                ConfigBean configBean = US2ConfigBeanUtil.toConfigBean(uploadSettings);
                if (null == configBean) {
                    if (null != mUploadCallBack) {
                        mUploadCallBack.onFail("settings is illegality.");
                    }
                    return;
                }

                FtpFileUtil fileUtil = new FtpFileUtil(configBean);
                try {
                    fileUtil.upload2Server(uploadFile);
                    if (null != mUploadCallBack) {
                        mUploadCallBack.onSuccess(configBean.getFileName() + " upload success.");
                    }
                } catch (Exception e) {
                    if (null != mUploadCallBack) {
                        mUploadCallBack.onFail(configBean.getFileName() + " upload fail. \n " + e.getMessage());
                    }
                    System.out.println("upload file fail. ex = " + e.getMessage());
                } finally {
                    try {
                        fileUtil.disConnect();
                    } catch (Exception e) {
                        System.out.println("upload file fail disConnect. ex = " + e.getMessage());
                    }
                }
            }
        }.queue();
    }

    public static UploadFileService getInstance(Project project) {
        return ServiceManager.getService(project, UploadFileService.class);
    }
}
