package com.asher.yang.upload.callback;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * upload file call back listener
 */
public interface IUploadCallBack {

    void onSuccess(String tips);

    void onFail(String errorMsg);
}
