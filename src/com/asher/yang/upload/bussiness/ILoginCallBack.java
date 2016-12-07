package com.asher.yang.upload.bussiness;

/**
 * Created by AsherYang on 2016/12/7.
 * email: ouyangfan1991@gmail.com
 * <p>
 * login call back listener.
 */
public interface ILoginCallBack {

    void onSuccess();

    void onFail(Exception ex);
}
