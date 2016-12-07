package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.callback.ILoginCallBack;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * login
 */
public class LoginService {

    private final Project project;
    private UploadSettings uploadSettings;
    private ILoginCallBack mLoginCallBack;

    public LoginService(Project project) {
        this.project = project;
        uploadSettings = UploadSettings.getSafeInstance(project);
    }

    public void setLoginListener(ILoginCallBack loginListener) {
        mLoginCallBack = loginListener;
    }

    public void login() {
        new Task.Backgroundable(project, "Authenticating Upload", false, UploadTaskOption.INSTANCE) {

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                FtpFileUtil ftpUtil = new FtpFileUtil(US2ConfigBeanUtil.toConfigBean(uploadSettings));
                try {
                    ftpUtil.login();
                    if (null != mLoginCallBack) {
                        mLoginCallBack.onSuccess();
                    }
                } catch (Exception e) {
                    System.out.println("login fail . ex = " + e.getMessage());
                    if (null != mLoginCallBack) {
                        mLoginCallBack.onFail(e);
                    }
                } finally {
                    try {
                        ftpUtil.disConnect();
                    } catch (Exception e) {
                        System.out.println("login fail disConnect. ex = " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        }.queue();
    }

    public static LoginService getInstance(Project project) {
        return ServiceManager.getService(project, LoginService.class);
    }
}
