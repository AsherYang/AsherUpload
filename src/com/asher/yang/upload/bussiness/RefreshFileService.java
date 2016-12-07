package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.bean.ConfigBean;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import it.sauronsoftware.ftp4j.FTPFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * refresh file
 */
public class RefreshFileService {

    private Project project;
    private UploadSettings uploadSettings;
    private IRefreshFileCallBack mFileCallBack;

    public RefreshFileService(Project project) {
        this.project = project;
        uploadSettings = UploadSettings.getSafeInstance(project);
    }

    public void setRefreshCallBack(IRefreshFileCallBack refreshCallBack) {
        this.mFileCallBack = refreshCallBack;
    }

    public void refresh() {
        new Task.Backgroundable(project, "Refresh File", false, UploadTaskOption.INSTANCE) {

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                ConfigBean configBean = US2ConfigBeanUtil.toConfigBean(uploadSettings);
                if (null == configBean) {
                    if (null != mFileCallBack) {
                        mFileCallBack.onFail(new Exception("config bean is null ."));
                    }
                    return;
                }
                FtpFileUtil fileUtil = new FtpFileUtil(configBean);
                try {
                    fileUtil.changePath(configBean.getFromPath());
                    List<FTPFile> ftpFiles = fileUtil.getFiles();
                    if (null != mFileCallBack) {
                        mFileCallBack.onSuccess(ftpFiles);
                    }
                } catch (Exception e) {
                    System.out.println("refresh file fail . ex = " + e.getMessage());
                    if (null != mFileCallBack) {
                        mFileCallBack.onFail(e);
                    }
                } finally {
                    try {
                        fileUtil.disConnect();
                    } catch (Exception e) {
                        System.out.println("refresh file fail disConnect. ex = " + e.getMessage());
                    }
                }
            }
        }.queue();
    }

    public static RefreshFileService getInstance(Project project) {
        return ServiceManager.getService(project, RefreshFileService.class);
    }
}
