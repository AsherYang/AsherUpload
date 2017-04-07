package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.bean.ConfigBean;
import com.asher.yang.upload.util.TextUtils;
import it.sauronsoftware.ftp4j.*;

import java.io.File;
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
    private List<FTPFile> filesDirTmp = new ArrayList<>();
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
        FTPFile maxModifyFile = Collections.max(filesTmp, new FileComparator());
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
     * 上传文件至服务器指定地址 toPath
     * <p>
     * 先从fromPath 下载到本地，在上传到 toPath
     */
    public void upload2Server(FTPFile uploadFile) throws FTPException, IOException, FTPIllegalReplyException,
            FTPDataTransferException, FTPAbortedException, FTPListParseException {
        throwIllegalArgumentException(mConfigBean);
        if (null == uploadFile) {
            throw new IllegalArgumentException("FTPFile need not null.");
        }

        // 这一句主要是要定位(changeDirectory)到当前显示的目录下
        List<FTPFile> files = getFiles();
        if (null == files) {
            return;
        }
        File localFile = new File(localFilePath());
        // 使用"/" 形式，因为使用File.separator无效
        String remoteFileName = baseDir + "/" + uploadFile.getName();
        System.out.println("remoteFileName = " + remoteFileName);
        ftpClient.download(remoteFileName, localFile);
        changeToUploadPath(uploadFile, mConfigBean.getToPath());
        ftpClient.upload(localFile);
        localFile.delete();
    }

    /**
     * 获取需要上传的目录
     * 分为Debug和Release
     */
    private void changeToUploadPath(FTPFile uploadFile, String baseUploadPath) throws FTPException, IOException, FTPIllegalReplyException,
            FTPAbortedException, FTPDataTransferException, FTPListParseException {
        ftpClient.changeDirectory(baseUploadPath);
        boolean isDebugFile = uploadFile.getName().contains("debug");
        FTPFile[] files = ftpClient.list();
        // 为null 说明没有子目录及文件，直接上传到该目录
        if (null == files || files.length == 0) {
            return;
        }
        // 只获取文件夹
        filesDirTmp.clear();
        for (FTPFile ftpFile : files) {
            if (ftpFile.getType() == 1) {
                filesDirTmp.add(ftpFile);
            }
        }

        // 只有文件，没有文件夹，那就是使用当前文件夹上传
        if (filesDirTmp.isEmpty()) {
            return;
        }

        // 如果只有1层子目录，直接cd进去
        if (filesDirTmp.size() == 1) {
            // 使用"/" 形式，因为使用File.separator无效
            baseUploadPath = baseUploadPath + "/" + filesDirTmp.get(0).getName();
            changeToUploadPath(uploadFile, baseUploadPath);
            return;
        }

        // 多层子目录，存在Debug|Release目录
        boolean hasChange = false;
        for (FTPFile file : filesDirTmp) {
            // debug 目录
            if (isDebugFile && file.getName().toLowerCase().contains("debug")) {
                baseUploadPath = baseUploadPath + "/" + file.getName();
                hasChange = true;
                break;
            }
            // release 目录
            if (!isDebugFile && file.getName().toLowerCase().contains("release")) {
                baseUploadPath = baseUploadPath + "/" + file.getName();
                hasChange = true;
                break;
            }
        }

        if (hasChange) {
            changeToUploadPath(uploadFile, baseUploadPath);
        } else {
            // 如果没有改变，则说明，多级目录不存在Debug|Release目录，提示用户无法定位目录，重新设置
            throwIllegalArgumentException(mConfigBean);
        }
    }

    /**
     * 不同的os返回不同的路径
     *
     * @return localFilePath
     */
    private String localFilePath() {
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().contains("WINDOWS")) {
            return "D:\\" + mConfigBean.getFileName() + ".apk";
        } else {
            return "/root/" + mConfigBean.getFileName() + ".apk";
        }
    }

    /**
     * check argument
     */
    public void throwIllegalArgumentException(ConfigBean configBean) {
        if (null == configBean || TextUtils.isEmpty(configBean.getHost())
                || TextUtils.isEmpty(configBean.getUsername())
                || TextUtils.isEmpty(configBean.getPassword())) {
            throw new IllegalArgumentException("you should check the setting arguments.");
        }
    }
}
