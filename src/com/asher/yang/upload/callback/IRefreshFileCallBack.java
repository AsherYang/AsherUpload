package com.asher.yang.upload.callback;

import it.sauronsoftware.ftp4j.FTPFile;

import java.util.List;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * refresh file call back listener
 */
public interface IRefreshFileCallBack {

    void onSuccess(List<FTPFile> ftpFiles);

    void onFail(Exception ex);
}
