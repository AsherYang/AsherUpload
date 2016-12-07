package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.UploadSettings;
import com.asher.yang.upload.bean.FtpBean;
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
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onSuccess() {
                System.out.println("login success.");
                super.onSuccess();
            }

            @Override
            public void onError(@NotNull Exception error) {
                super.onError(error);
                System.out.println("login fail.");
            }

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                FtpBean ftpBean = new FtpBean();
                ftpBean.setHost(uploadSettings.getHost());
                ftpBean.setPort(21);
                ftpBean.setUsername(uploadSettings.getUsername());
                ftpBean.setPassword(uploadSettings.getPassword());
                FtpFileUtil ftpUtil = new FtpFileUtil();
                try {
                    ftpUtil.login(ftpBean);
                    if (null != mLoginCallBack) {
                        mLoginCallBack.onSuccess();
                    }
                } catch (Exception e) {
                    System.out.println("login fail . ex = " + e.getMessage());
                    if (null != mLoginCallBack) {
                        mLoginCallBack.onFail(e);
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
