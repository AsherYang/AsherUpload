package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.bean.ConfigBean;
import com.asher.yang.upload.util.TextUtils;
import it.sauronsoftware.ftp4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * support ftp execute method
 */
public class FtpFileUtil {

    private String baseDir = "/project/Android/I3Watch/I3_Android_Contact";
    private FTPClient ftpClient;
    private List<FTPFile> ftpFiles = new ArrayList<>();
    private List<FTPFile> filesTmp = new ArrayList<>();
    private ConfigBean mConfigBean;

    public FtpFileUtil(ConfigBean configBean) {
        ftpClient = new FTPClient();
        this.mConfigBean = configBean;
        initBaseDir();
    }

    private void initBaseDir() {
        if (null != mConfigBean && !TextUtils.isEmpty(mConfigBean.getFromPath())) {
            baseDir = mConfigBean.getFromPath();
        }
    }

    /**
     * 登录
     */
    public void login() throws FTPException, IOException, FTPIllegalReplyException {
        throwIllegalArgumentException(mConfigBean);
        ftpClient.setCharset("utf-8");
        ftpClient.setType(FTPClient.TYPE_BINARY);
        ftpClient.connect(mConfigBean.getHost(), mConfigBean.getPort());
        ftpClient.login(mConfigBean.getUsername(), mConfigBean.getPassword());
    }

    /**
     * change path
     */
    public void changePath(String toPath) throws FTPException, IOException, FTPIllegalReplyException {
        if (TextUtils.isEmpty(toPath)) {
            return;
        }
        if (!ftpClient.isAuthenticated()) {
            login();
        }
        baseDir = toPath;
        ftpClient.changeDirectory(baseDir);
    }

    /**
     * get files from baseDir
     */
    public List<FTPFile> getFiles() throws FTPException, IOException, FTPDataTransferException,
            FTPListParseException, FTPIllegalReplyException, FTPAbortedException {

        throwIllegalArgumentException(mConfigBean);
        if (!ftpClient.isAuthenticated()) {
            login();
        }
        filesTmp.clear();
        ftpFiles.clear();
        changePath(baseDir);
        FTPFile[] files = ftpClient.list();
        Collections.addAll(filesTmp, files);
        FTPFile maxModifyFile = Collections.max(filesTmp, new FileCompare());
        // 0:FILE , 1:DIRECTORY , 2:LINK
        if (maxModifyFile.getType() == 1) {
            baseDir = baseDir + "/" + maxModifyFile.getName();
            getFiles();
        } else if (maxModifyFile.getType() == 0) {
            // add to list
            filterFtpFile();
        } else {
            return ftpFiles;
        }
        return ftpFiles;
    }

    private void filterFtpFile() {
        if (null == filesTmp || filesTmp.isEmpty()) {
            return;
        }
        if (mConfigBean.isOnlyRelease()) {
            for (FTPFile file : filesTmp) {
                if (file.getName().contains("debug") || file.getName().contains("unaligned")) {
                    continue;
                }
                ftpFiles.add(file);
            }
        } else {
            ftpFiles.addAll(filesTmp);
        }
    }

    /**
     * disConnect
     */
    public void disConnect() throws FTPException, IOException, FTPIllegalReplyException {
        ftpClient.disconnect(false);
    }

    /**
     * check argument
     */
    public void throwIllegalArgumentException(ConfigBean configBean) {
        if (null == configBean || TextUtils.isEmpty(configBean.getHost())
                || TextUtils.isEmpty(configBean.getUsername())
                || TextUtils.isEmpty(configBean.getPassword())) {
            throw new IllegalArgumentException("ftp bean and argument must not null.");
        }
    }
}