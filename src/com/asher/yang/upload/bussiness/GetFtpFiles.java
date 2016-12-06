package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.bean.FtpBean;
import com.asher.yang.upload.util.TextUtils;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * support ftp execute method
 */
public class GetFtpFiles {

    private String baseDir = "/project/Android/I3Watch/I3_Android_Contact";
    private FtpBean ftpBean;
    private FTPClient ftpClient;
    private List<FTPFile> ftpFiles = new ArrayList<>();
    private List<FTPFile> filesTmp = new ArrayList<>();

    public GetFtpFiles(FtpBean ftpBean) {
        this.ftpBean = ftpBean;
        ftpClient = new FTPClient();
    }

    public void login() {
        ftpClient.setCharset("utf-8");
        ftpClient.setType(FTPClient.TYPE_BINARY);
        try {
            ftpClient.connect(ftpBean.getHost(), ftpBean.getPort());
            ftpClient.login(ftpBean.getUsername(), ftpBean.getPassword());
        } catch (Exception e) {
            System.out.println("exception message : " + e.getMessage());
        }
    }

    public void changePath() {
        try {
            ftpClient.changeDirectory(baseDir);
        } catch (Exception e) {
            System.out.println("exception message : " + e.getMessage());
        }
    }

    public List<FTPFile> getFiles() {
        if (null == ftpBean || TextUtils.isEmpty(ftpBean.getHost())
                || TextUtils.isEmpty(ftpBean.getUsername())
                || TextUtils.isEmpty(ftpBean.getPassword())) {
            throw new IllegalArgumentException("ftp bean must not null.");
        }

        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.list();
        } catch (Exception e) {
            System.out.println("exception message : " + e.getMessage());
        }
        Collections.addAll(filesTmp, files);
        FTPFile maxModifyFile = Collections.max(filesTmp, new FileCompare());
        // 0:FILE , 1:DIRECTORY , 2:LINK
        if (maxModifyFile.getType() == 1) {
            baseDir = baseDir + "/" + maxModifyFile.getName();
            changePath();
            getFiles();
        } else if (maxModifyFile.getType() == 0) {
            // download
            ftpFiles.add(maxModifyFile);
        } else {
            return ftpFiles;
        }

        disConnect();
        return ftpFiles;
    }

    public void disConnect() {
        try {
            ftpClient.disconnect(false);
        } catch (Exception e) {
            System.out.println("exception message : " + e.getMessage());
        }
    }

}
