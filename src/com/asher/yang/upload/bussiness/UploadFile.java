package com.asher.yang.upload.bussiness;

import com.asher.yang.upload.bean.FtpBean;
import com.asher.yang.upload.util.TextUtils;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AsherYang on 2016/12/5.
 * email: ouyangfan1991@gmail.com
 * <p>
 * upload file to server use of ftp
 */
public class UploadFile {

    private String baseDir = "/project/Android/I3Watch/I3_Android_Contact";
    private FtpBean ftpBean;

    public UploadFile(FtpBean ftpBean) {
        this.ftpBean = ftpBean;
    }

    public void execute() {
        if (null == ftpBean || TextUtils.isEmpty(ftpBean.getHost())
                || TextUtils.isEmpty(ftpBean.getUsername())
                || TextUtils.isEmpty(ftpBean.getPassword())) {
            throw new IllegalArgumentException("ftp bean must not null.");
        }
        FTPClient ftpClient = new FTPClient();
        ftpClient.setCharset("utf-8");
        ftpClient.setType(FTPClient.TYPE_BINARY);
        try {
            ftpClient.connect(ftpBean.getHost(), ftpBean.getPort());
            ftpClient.login(ftpBean.getUsername(), ftpBean.getPassword());
//            ftpClient.changeDirectory("/project/Watch/I3/App/All/release");
            ftpClient.changeDirectory(baseDir);
            FTPFile[] files = ftpClient.list();
            List<FTPFile> ftpFiles = new ArrayList<>();
            Collections.addAll(ftpFiles, files);
            FTPFile maxModify = Collections.max(ftpFiles, new FileCompare());
            System.out.println("max file == " + maxModify);
            // 0:FILE , 1:DIRECTORY , 2:LINK
            if (maxModify.getType() == 1) {
                baseDir = baseDir + "/" + maxModify.getName();
                execute();
            } else if (maxModify.getType() == 0){
                // download
                File localFile = new File("D:\\" + maxModify.getName());
                ftpClient.download(maxModify.getName(), localFile);
                File newFile = new File("D:\\XTCContact.apk");
                localFile.renameTo(newFile);
                ftpClient.upload(newFile);
                newFile.delete();
            } else {
                return;
            }
            ftpClient.disconnect(false);
        } catch (Exception e) {
            System.out.println("exception message : " + e.getMessage());
        }
    }

}
